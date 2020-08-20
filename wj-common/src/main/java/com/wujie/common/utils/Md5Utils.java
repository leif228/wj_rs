package com.wujie.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;

/**
 * Md5加密方法
 * 
 * @author wujie
 */
public class Md5Utils
{
    private static final Logger log = LoggerFactory.getLogger(Md5Utils.class);

    private static byte[] md5(String s)
    {
        MessageDigest algorithm;
        try
        {
            algorithm = MessageDigest.getInstance("MD5");
            algorithm.reset();
            algorithm.update(s.getBytes("UTF-8"));
            byte[] messageDigest = algorithm.digest();
            return messageDigest;
        }
        catch (Exception e)
        {
            log.error("MD5 Error...", e);
        }
        return null;
    }

    private static final String toHex(byte[] hash)
    {
        if (hash == null)
        {
            return null;
        }
        StringBuffer buf = new StringBuffer(hash.length * 2);
        int i;

        for (i = 0; i < hash.length; i++)
        {
            if ((hash[i] & 0xff) < 0x10)
            {
                buf.append("0");
            }
            buf.append(Long.toString(hash[i] & 0xff, 16));
        }
        return buf.toString();
    }

    public static String hash(String s)
    {
        try
        {
            return new String(toHex(md5(s)).getBytes("UTF-8"), "UTF-8");
        }
        catch (Exception e)
        {
            log.error("not supported charset...{}", e);
            return s;
        }
    }
    public static String encrypt(String sourceStr) {
        try {
            // 获得MD5摘要算法的 MessageDigest对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(sourceStr.getBytes());
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            StringBuffer buf = new StringBuffer();
            for (int i = 0; i < md.length; i++) {
                int tmp = md[i];
                if (tmp < 0)
                    tmp += 256;
                if (tmp < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(tmp));
            }
            // return buf.toString().substring(8, 24);// 16位加密
            return buf.toString();// 32位加密
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        System.out.println(encrypt("123456"));
        System.out.println(encrypt("123456"));

    }
}
