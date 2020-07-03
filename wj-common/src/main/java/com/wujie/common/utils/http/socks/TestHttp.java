package com.wujie.common.utils.http.socks;

import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class TestHttp {

	//检查服务是否可用
	@SuppressWarnings("resource")
	public static boolean isConnected() {
		Socket socket;
		try {
			socket = new Socket("192.168.1.168", 1080);
			socket.sendUrgentData(0xFF);
			return true;
		} catch (Exception e) {
			return false;
		}

	}
	
	public static void main(String[] args) {
		try {
//			String url="http://www.weather.com.cn/data/sk/101110101.html";			
//			String result= HttpClientUtil.getWithProxy(url, null);
			
			//String url = "http://r.qzone.qq.com/cgi-bin/user/cgi_personal_card";
	        //Map<String, String> params = new HashMap<String, String>();
			//params.put("uin", "695022146");
			//String result= HttpClientUtil.postWithProxy(url, params);
			
			String url = "http://www.myships.com/myships/10029";
	        Map<String, String> params = new HashMap<String, String>();
			params.put("mmsi", "413997667");
			String result= HttpClientUtil.postWithProxy(url, params);
			
			System.out.println(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
