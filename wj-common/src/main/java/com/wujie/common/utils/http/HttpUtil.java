package com.wujie.common.utils.http;

import org.apache.http.*;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.*;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.nio.charset.CodingErrorAction;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class HttpUtil {
	
	private static final String CHARSET = "UTF-8";    
    private static PoolingHttpClientConnectionManager connManager = null;
    private static CloseableHttpClient httpclient = null;
    
    static {
        try {
        	HttpRequestRetryHandler handler = new HttpRequestRetryHandler() {
                @Override  
                public boolean retryRequest(IOException arg0, int retryTimes, HttpContext arg2) {  
                  if (retryTimes > 5) {  
                        return false;  
                    }  
                    if (arg0 instanceof UnknownHostException || arg0 instanceof ConnectTimeoutException  
                            || !(arg0 instanceof SSLException) || arg0 instanceof NoHttpResponseException) {  
                        return true;  
                    }  
                   
                    HttpClientContext clientContext = HttpClientContext.adapt(arg2);  
                    HttpRequest request = clientContext.getRequest();  
                    boolean idempotent = !(request instanceof HttpEntityEnclosingRequest);  
                    if (idempotent) {  
                        // 如果请求被认为是幂等的，那么就重试。即重复执行不影响程序其他效果的  
                        return true;  
                    }  
                    return false;  
                }  
            };
        	
            SSLContext sslContext = SSLContexts.custom().useTLS().build();
            sslContext.init(null,
                    new TrustManager[] { new X509TrustManager() {
                        public X509Certificate[] getAcceptedIssuers() { return null;}
                        public void checkClientTrusted( X509Certificate[] certs, String authType) {}
                        public void checkServerTrusted( X509Certificate[] certs, String authType) {}
                    }}, null);
            Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                    .register("http", PlainConnectionSocketFactory.INSTANCE)
                    .register("https", new SSLConnectionSocketFactory(sslContext))
                    .build();
             
            connManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
            httpclient = HttpClients.custom().setConnectionManager(connManager).setRetryHandler(handler).build();
            
            // Create socket configuration
            SocketConfig socketConfig = SocketConfig.custom().setTcpNoDelay(true).build();
            connManager.setDefaultSocketConfig(socketConfig);
            
            // Create message constraints
            MessageConstraints messageConstraints = MessageConstraints.custom()
                .setMaxHeaderCount(200)
                .setMaxLineLength(2000)
                .build();
            
            // Create connection configuration
            ConnectionConfig connectionConfig = ConnectionConfig.custom()
                .setMalformedInputAction(CodingErrorAction.IGNORE)
                .setUnmappableInputAction(CodingErrorAction.IGNORE)
                .setCharset(Consts.UTF_8)
                .setMessageConstraints(messageConstraints)
                .build();
            connManager.setDefaultConnectionConfig(connectionConfig);
            connManager.setMaxTotal(200);
            connManager.setDefaultMaxPerRoute(20);
        } catch (KeyManagementException e) {
        } catch (NoSuchAlgorithmException e) {
        }
    }
    
    /**
     * 发送POST请求
     * @param url 请求地址
     * @param params 参数
     * @return
     */
    public static String post(String url, Map<String, String> params) {
    	return post(url, params, false, null, 0);
    }
    
    /**
     * 发送POST请求
     * @param url 请求地址
     * @param params 参数
     * @param proxyHost 代理地址
     * @param proxyPort 代理端口号
     * @return
     */
    public static String post(String url, Map<String, String> params, String proxyHost, int proxyPort) {
    	return post(url, params, true, proxyHost, proxyPort);
    }
    
    /**
     * 发送POST请求
     * @param url 请求地址
     * @param params 参数
     * @param isProxy 是否添加代理
     * @param proxyHost 代理地址
     * @param proxyPort 代理端口号
     * @return
     */
    public static String post(String url, Map<String, String> params, boolean isProxy, String proxyHost, int proxyPort){
        HttpPost post = new HttpPost(url);
        post.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.93 Safari/537.36");  
        post.setHeader("Connection", "keep-alive");
        
        try {
            if (isProxy) {
            	HttpHost proxy = new HttpHost(proxyHost, proxyPort, "http"); //添加代理
                //设置httpclient超时时间，使用代理
                RequestConfig requestConfig = RequestConfig.custom()
                        .setConnectTimeout(5000).setConnectionRequestTimeout(3000)
                        .setSocketTimeout(5000).setProxy(proxy).build();
                post.setConfig(requestConfig);
            } else {
                //设置httpclient超时时间
                RequestConfig requestConfig = RequestConfig.custom()
                        .setConnectTimeout(3000).setConnectionRequestTimeout(1000)
                        .setSocketTimeout(3000).build();        
                post.setConfig(requestConfig);
            }
            
            //封装请求参数
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            if(params != null){
            	for(Entry<String, String> entry :params.entrySet()){
            		nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            	}
            }
            post.setEntity(new UrlEncodedFormEntity(nvps, CHARSET));
            CloseableHttpResponse response = httpclient.execute(post);
            try {
                HttpEntity entity = response.getEntity();
                try {
                    if(entity != null){
                        String str = EntityUtils.toString(entity, CHARSET);
                        return str;
                    }
                } finally {
                    if(entity != null){
                        entity.getContent().close();
                    }
                }
            } finally {
                if(response != null){
                    response.close();
                }
            }
        } catch (Exception e) {
        	throw new RuntimeException(e);
        } finally {
            post.releaseConnection();
        }
        return "";
    }
    
   /**
    * 发送GET请求
    * @param url 请求地址
    * @param params 参数
    * @return
    */
   public static String get(String url, Map<String, String> params) {
	   return get(url, params, false, null, 0);
   }
   
  /**
   * 发送GET请求
   * @param url 请求地址
   * @param params 参数
   * @param proxyHost 代理地址
   * @param proxyPort 代理端口号
   * @return
   */
  public static String get(String url, Map<String, String> params, String proxyHost, int proxyPort) {
	   return get(url, params, true, proxyHost, proxyPort);
  }
     
    /**
     * 发送GET请求
     * @param url 请求地址
     * @param params 参数
     * @param isProxy 是否添加代理
     * @param proxyHost 代理地址
     * @param proxyPort 代理端口号
     * @return
     */
    public static String get(String url, Map<String, String> params, boolean isProxy, String proxyHost, int proxyPort) {
         
        StringBuilder sb = new StringBuilder(url);
        if(!url.contains("?")){
        	sb.append("?");
        }else{
            sb.append("&");
        }
        if(params != null){
        	for (Entry<String, String> entry : params.entrySet()) {
        		sb.append(entry.getKey());
        		sb.append("=");
        		try {
        			sb.append(URLEncoder.encode(entry.getValue(), CHARSET));
        		} catch (UnsupportedEncodingException e) {throw new RuntimeException(e);}
        		sb.append("&");
        	}
        }
        
    	sb.deleteCharAt(sb.length()-1);
    	
    	String responseString = null;
        HttpGet get = new HttpGet(sb.toString());
        
        try {
            if (isProxy) {
            	HttpHost proxy = new HttpHost(proxyHost, proxyPort, "http"); //添加代理
                //设置httpclient超时时间，使用代理
                RequestConfig requestConfig = RequestConfig.custom()
                        .setConnectTimeout(3000).setConnectionRequestTimeout(1000)
                        .setSocketTimeout(3000).setProxy(proxy).build();
                get.setConfig(requestConfig);
            } else {
                //设置httpclient超时时间
                RequestConfig requestConfig = RequestConfig.custom()
                        .setConnectTimeout(3000).setConnectionRequestTimeout(1000)
                        .setSocketTimeout(3000).build();        
                get.setConfig(requestConfig);
            }
            
            CloseableHttpResponse response = httpclient.execute(get);
            try {
                HttpEntity entity = response.getEntity();
                try {
                    if(entity != null){
                        responseString = EntityUtils.toString(entity, CHARSET);
                    }
                } finally {
                    if(entity != null){
                        entity.getContent().close();
                    }
                }
            } catch (Exception e) {
                return responseString;
            } finally {
                if(response != null){
                    response.close();
                }
            }
        } catch (SocketTimeoutException e) {
            return responseString;
        } catch (Exception e) {
        	throw new RuntimeException(e);
        } finally {
            get.releaseConnection();
        }
        return responseString;
    }

    //测试方法
	public static void main(String[] args) throws InterruptedException {
		String proxyHost = "117.191.11.75";
		int proxyPort = 8080;
		String url = "http://www.myships.com/myships/10029";
		Map<String, String> params = new HashMap<String, String>();
		params.put("mmsi", "413997667,413900176");
		String rs = HttpUtil.post(url, params, proxyHost, proxyPort);
		System.out.println(rs);
	}

}
