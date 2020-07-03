package com.wujie.common.utils;

public class CooordUtil {

	private static final double EARTH_RADIUS = 6378137;

	private static double rad(double d) {
		return d * Math.PI / 180.0;
	}

	/**
	 * 根据两点间经纬度坐标（double值），计算两点间距离，单位为米
	 * 
	 * @param lng1
	 * @param lat1
	 * @param lng2
	 * @param lat2
	 * @return
	 */
	public static double GetDistance(double lng1, double lat1, double lng2, double lat2) {
		double radLat1 = rad(lat1);
		double radLat2 = rad(lat2);
		double a = radLat1 - radLat2;
		double b = rad(lng1) - rad(lng2);
		double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1) * Math.cos(radLat2)
				* Math.pow(Math.sin(b / 2), 2)));
		s = s * EARTH_RADIUS;
		// s = Math.round(s * 1000.0) / 1000.0;
		return s;
	}
	//计算时速(节=海里/小时)
	public static Double getSpeed(double lng1, double lat1, double lng2, double lat2,  String startTime, String endTime){
		double distance = GetDistance(lng1, lat1, lng2, lat2);
		//Double distanceGM = Double.valueOf(distance);//换算成公里
		Double distanceGM = Double.valueOf(distance)*0.5399568/1000.0;//换算成海里
		//Double distanceGM = Double.valueOf(distance)/1000.0;//换算成公里 
		Double time = getTimeDistance(startTime,endTime);
		Double result = distanceGM/time;
		return result;
	}
	//计算时间差
	public static Double getTimeDistance( String startTime, String endTime){
		int second = CalendarUtil.secondDiff(CalendarUtil.parseYYYY_MM_DD_HH_MM_SS(endTime), CalendarUtil.parseYYYY_MM_DD_HH_MM_SS(startTime));
		Double hour = second*1.0D/3600.0;
		return hour;
	}
	/**
	 * 计算两个节点点方位角
	 * @param lat_a
	 * @param lng_a
	 * @param lat_b
	 * @param lng_b
	 * @return
	 */
	public static double gps2d(double lat_a, double lng_a, double lat_b, double lng_b) {
		double d = 0.0;
		double radLat1 = rad(lat_a);
		double radLat2 = rad(lat_b);
		double radLng1 = rad(lng_a);
		double radLng2= rad(lng_b);
		d = Math.sin(radLat1) * Math.sin(radLat2) + Math.cos(radLat1) * Math.cos(radLat2) * Math.cos(radLng2 - radLng1);
		d = Math.sqrt(1 - d * d);
		d = Math.cos(radLat2) * Math.sin(radLng2 - radLng1) / d;
		d = Math.asin(d) * 180 / Math.PI;
		// d = Math.round(d*10000);
		return 180-d;
	}
	
	/** 经度113°53'5.40\"E */
	public static double convertLon(String lon) {
		double longitude = 0.0;
		if (lon != null && !"".equals(lon)) {
			lon = lon.trim();
			double du = Double.parseDouble(lon.substring(0, lon.indexOf("°")));
			double fen = Double.parseDouble(lon.substring(lon.indexOf("°") + 1, lon.indexOf("'")));
			double miao = Double.parseDouble(lon.substring(lon.indexOf("'") + 1, lon.indexOf("\"")));
			longitude = Math.round((du + fen / 60 + miao / 60 / 60) * 1000000.0) / 1000000.0;
			if (!lon.substring(lon.length() - 1).equals("E"))
				longitude = -1 * longitude;
		}
		return longitude;
	}

	/** 纬度22°27'15.82\"N */
	public static double convertLat(String lat) {
		double latitude = 0.0;
		if (lat != null && !"".equals(lat)) {
			lat = lat.trim();
			double du = Double.parseDouble(lat.substring(0, lat.indexOf("°")));
			double fen = Double.parseDouble(lat.substring(lat.indexOf("°") + 1, lat.indexOf("'")));
			double miao = Double.parseDouble(lat.substring(lat.indexOf("'") + 1, lat.indexOf("\"")));
			latitude = Math.round((du + fen / 60 + miao / 60 / 60) * 1000000.0) / 1000000.0;
			if (!lat.substring(lat.length() - 1).equals("N"))
				latitude = -1 * latitude;
		}
		return latitude;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//double distance = GetDistance(118.06, 24.27, 121.29, 31.14);
		//System.out.println("厦门到上海的距离(km):" + distance);

	}

}
