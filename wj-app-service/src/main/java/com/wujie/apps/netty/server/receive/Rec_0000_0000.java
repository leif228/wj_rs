package com.wujie.apps.netty.server.receive;


import com.alibaba.fastjson.JSONObject;
import com.wujie.apps.netty.server.send.Sen_0000_0000;
import com.wujie.apps.netty.server.send.Sen_factory;
import com.wujie.fclient.service.AppUserService;
import com.wujie.apps.netty.pojo.Rec_task_i;
import com.wujie.apps.netty.protocol.WjProtocol;
import io.netty.channel.ChannelHandlerContext;

/**
 * 终端→服务 心跳ping
 */
public class Rec_0000_0000 implements Rec_task_i {
    @Override
    public void doTask(ChannelHandlerContext ctx, String tx, JSONObject objParam) {
        sendIdle(ctx);
    }

    @Override
    public void setService(AppUserService appUserService) {

    }

    private void sendIdle(ChannelHandlerContext ctx) {
//        WjProtocol wjProtocol = new WjProtocol();
//        wjProtocol.setPlat(new byte[]{0x20, 0x00});
//        wjProtocol.setMaincmd(new byte[]{0x00, 0x00});
//        wjProtocol.setSubcmd(new byte[]{0x00, 0x00});
//        wjProtocol.setFormat("TX");
//        wjProtocol.setBack(new byte[]{0x01, 0x00});
//
//        int len = WjProtocol.MIN_DATA_LEN + 0;
//        wjProtocol.setLen(wjProtocol.short2byte((short) len));

        WjProtocol wjProtocol = Sen_factory.getInstance(Sen_0000_0000.main, Sen_0000_0000.sub,null);
        if(wjProtocol == null)
            return;

        ctx.write(wjProtocol);

        ctx.flush();
    }
}
