package com.wujie.apps.netty.client.send;


import com.alibaba.fastjson.JSONObject;
import com.wujie.apps.netty.pojo.BaseTask;
import com.wujie.apps.netty.pojo.LoginTask;
import com.wujie.apps.netty.protocol.WjProtocol;

import java.io.UnsupportedEncodingException;

//终端→服务 登录
public class Sen_0000_0100 implements Sen_i {
    public static final byte[] main = new byte[]{0x00, 0x00};
    public static final byte[] sub = new byte[]{0x01, 0x00};

    @Override
    public WjProtocol generateWj(BaseTask baseTask) throws UnsupportedEncodingException {
        WjProtocol wjProtocol = new WjProtocol();
        wjProtocol.setPlat(new byte[]{0x21, 0x00});
        wjProtocol.setMaincmd(main);
        wjProtocol.setSubcmd(sub);
        wjProtocol.setFormat("JS");
        wjProtocol.setBack(new byte[]{0x00, 0x00});

        int len = 0;
        byte[] objectBytes = null;
        if(baseTask != null){
            LoginTask loginTask = (LoginTask) baseTask;

            String jsonStr = JSONObject.toJSONString(loginTask);
            objectBytes = jsonStr.getBytes("UTF-8");

            len = objectBytes.length;
        }

        len = WjProtocol.MIN_DATA_LEN + len;
        wjProtocol.setLen(wjProtocol.short2byte((short) len));
        wjProtocol.setUserdata(objectBytes);

        return wjProtocol;
    }
}
