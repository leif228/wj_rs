package com.wujie.tc.netty.client.receive;


import com.alibaba.fastjson.JSONObject;
import com.wujie.fclient.service.AppUserService;
import com.wujie.tc.netty.pojo.Rec_task_i;
import io.netty.channel.ChannelHandlerContext;

/**
 * 服务→终端 心跳pong
 */
public class Rec_0000_0000 implements Rec_task_i {
    @Override
    public void doTask(ChannelHandlerContext ctx, String tx, JSONObject objParam) {
//        sendIdle(ctx);
    }

    @Override
    public void setService(AppUserService appUserService) {

    }


}
