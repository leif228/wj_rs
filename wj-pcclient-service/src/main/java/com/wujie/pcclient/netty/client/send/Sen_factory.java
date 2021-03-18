package com.wujie.pcclient.netty.client.send;


import com.wujie.pcclient.netty.pojo.BaseTask;
import com.wujie.pcclient.netty.pojo.Sen_i;
import com.wujie.pcclient.netty.protocol.WjProtocol;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Sen_factory {
    public static WjProtocol getInstance(byte[] main, byte[] sub, BaseTask baseTask){
        //通用处理
        try {
            String classPath = "com.wujie.pcclient.netty.client.send";
            String className = "Sen_" + IntToHexStringLimit2(main[0]) + IntToHexStringLimit2(main[1]) + "_"
                    + IntToHexStringLimit2(sub[0]) + IntToHexStringLimit2(sub[1]);
            className = classPath + "." + className;
            log.debug("className:" + className);
            Class genClass = Class.forName(className);
            Sen_i sen_i = (Sen_i) genClass.newInstance();

            return sen_i.generateWj(baseTask);
        } catch (Exception e) {
            log.debug("Sen_factory.getInstance_报错了:" + e.getMessage());
        }
        return null;
    }

    private static String IntToHexStringLimit2(int num) {

        String hexString = Integer.toHexString(num);
        switch (hexString.length()) {
            case 1:
                hexString = "0" + hexString;
                break;
            case 2:
                hexString = hexString;
                break;
            default:
                hexString = null;
                break;
        }

        return hexString;
    }
}
