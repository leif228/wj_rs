package com.wujie.common.utils.http.socks;

import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.DnsResolver;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpContext;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.*;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class HttpClientUtil {
    // 依次是代理地址，代理端口号，用户密码
    private static String proxyHost="117.191.11.75";
    private static int proxyPort=8080;

//    private static String proxyHost="192.168.1.168";
//    private static int proxyPort=1080;
//    private static String proxyName="user";
//    private static String proxyPwd="123456";
    
    public static SSLContext buildCertificateIgnoringSslContext() {
        try {
            return new SSLContextBuilder()
                .loadTrustMaterial(null, (x509Certificates, s) -> true)
                .build();
        }
        catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException e) {
            throw new IllegalStateException("Unexpected exception while building the certificate-ignoring SSLContext.", e);
        }
    }


    public static String getWithProxy(String url, Map<String, String> params) {
        //用户名和密码验证
    	/*Authenticator.setDefault(new Authenticator(){
            protected  PasswordAuthentication  getPasswordAuthentication(){
                PasswordAuthentication p=new PasswordAuthentication(proxyName, proxyPwd.toCharArray());
                return p;
            }
        });*/
        Registry<ConnectionSocketFactory> reg = RegistryBuilder.<ConnectionSocketFactory> create()
                .register("http", new MyConnectionSocketFactory())
                .register("https", new MySSLConnectionSocketFactory(buildCertificateIgnoringSslContext())).build();
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager(reg, new FakeDnsResolver());
        CloseableHttpClient httpclient = HttpClients.custom().setConnectionManager(cm).build();
        try {
            InetSocketAddress socksaddr = new InetSocketAddress(proxyHost,proxyPort);
            HttpClientContext context = HttpClientContext.create();
            context.setAttribute("socks.address", socksaddr);

            
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
            			sb.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
            		} catch (UnsupportedEncodingException e) {throw new RuntimeException(e);}
            		sb.append("&");
            	}
            }
        	sb.deleteCharAt(sb.length()-1);
        	
            HttpGet httpget = new HttpGet(sb.toString());            
            CloseableHttpResponse response = httpclient.execute(httpget, context);
            try {
                return new String(EntityUtils.toByteArray(response.getEntity()), "UTF-8");
            } finally {
                response.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                httpclient.close();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return null;
    }


    public static String postWithProxy(String url, Map<String, String> params) {
        //用户名和密码验证
    	/*Authenticator.setDefault(new Authenticator(){
            protected  PasswordAuthentication  getPasswordAuthentication(){
                PasswordAuthentication p=new PasswordAuthentication(proxyName, proxyPwd.toCharArray());
                return p;
            }
        });*/
        Registry<ConnectionSocketFactory> reg = RegistryBuilder.<ConnectionSocketFactory> create()
                .register("http", new MyConnectionSocketFactory())
                .register("https", new MySSLConnectionSocketFactory(buildCertificateIgnoringSslContext())).build();
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager(reg, new FakeDnsResolver());
        CloseableHttpClient httpclient = HttpClients.custom().setConnectionManager(cm).build();
        try {
            InetSocketAddress socksaddr = new InetSocketAddress(proxyHost,proxyPort);
            HttpClientContext context = HttpClientContext.create();
            context.setAttribute("socks.address", socksaddr);
            HttpPost httppost = new HttpPost(url);            
            //封装请求参数
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            if(params != null){
            	for(Entry<String, String> entry :params.entrySet()){
            		nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            	}
            }
            httppost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
            
            CloseableHttpResponse response = httpclient.execute(httppost,context);
            try {
                return new String(EntityUtils.toByteArray(response.getEntity()), "UTF-8");
            } finally {
                response.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                httpclient.close();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return null;
    }
    
    static class FakeDnsResolver implements DnsResolver {
        @Override
        public InetAddress[] resolve(String host) throws UnknownHostException {
            // Return some fake DNS record for every request, we won't be using it
            return new InetAddress[] { InetAddress.getByAddress(new byte[] { 1, 1, 1, 1 }) };
        }
    }

    static class MyConnectionSocketFactory extends PlainConnectionSocketFactory {
        @Override
        public Socket createSocket(final HttpContext context) throws IOException {
            InetSocketAddress socksaddr = (InetSocketAddress) context.getAttribute("socks.address");
            Proxy proxy = new Proxy(Proxy.Type.SOCKS, socksaddr);
            return new Socket(proxy);
        }

        @Override
        public Socket connectSocket(int connectTimeout, Socket socket, HttpHost host, InetSocketAddress remoteAddress,
                                    InetSocketAddress localAddress, HttpContext context) throws IOException {
            // Convert address to unresolved
            InetSocketAddress unresolvedRemote = InetSocketAddress
                    .createUnresolved(host.getHostName(), remoteAddress.getPort());
            return super.connectSocket(connectTimeout, socket, host, unresolvedRemote, localAddress, context);
        }
    }

    static class MySSLConnectionSocketFactory extends SSLConnectionSocketFactory {

        public MySSLConnectionSocketFactory(final SSLContext sslContext) {
            // You may need this verifier if target site's certificate is not secure
            super(sslContext, ALLOW_ALL_HOSTNAME_VERIFIER);
        }

        @Override
        public Socket createSocket(final HttpContext context) throws IOException {
            InetSocketAddress socksaddr = (InetSocketAddress) context.getAttribute("socks.address");
            Proxy proxy = new Proxy(Proxy.Type.SOCKS, socksaddr);
            return new Socket(proxy);
        }

        @Override
        public Socket connectSocket(int connectTimeout, Socket socket, HttpHost host, InetSocketAddress remoteAddress,
                                    InetSocketAddress localAddress, HttpContext context) throws IOException {
            // Convert address to unresolved
            InetSocketAddress unresolvedRemote = InetSocketAddress
                    .createUnresolved(host.getHostName(), remoteAddress.getPort());
            return super.connectSocket(connectTimeout, socket, host, unresolvedRemote, localAddress, context);
        }
    }

}
