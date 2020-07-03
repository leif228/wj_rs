package com.wujie.common.utils;

import com.wujie.common.config.Global;
import com.wujie.common.json.JSON;
import com.wujie.common.json.JSONObject;
import com.wujie.common.utils.http.HttpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 获取地址类
 * 
 * @author wujie
 */
public class AddressUtils
{
    private static final Logger log = LoggerFactory.getLogger(AddressUtils.class);

    public static final String IP_URL = "http://ip.taobao.com/service/getIpInfo.php";

    public static String getRealAddressByIP(String ip)
    {
    	if ("127.0.0.1".equals(ip)) {
    		return "内网IP";
    	}
        String address = "XX XX";
    	if(IpUtils.internalIp(ip)){
    	    return "内网IP";
        }
        if (Global.isAddressEnabled())
        {
            String rspStr = HttpUtils.sendPost(IP_URL, "ip=" + ip);
            if (StringUtils.isEmpty(rspStr))
            {
                log.error("获取地理位置异常 {}", ip);
                return address;
            }
            JSONObject obj;
            try
            {
                obj = JSON.unmarshal(rspStr, JSONObject.class);
                JSONObject data = obj.getObj("data");
                String region = data.getStr("region");
                String city = data.getStr("city");
                address = region + " " + city;
            }
            catch (Exception e)
            {
                log.error("获取地理位置异常 {}", ip);
            }
        }
        return address;
    }
}
