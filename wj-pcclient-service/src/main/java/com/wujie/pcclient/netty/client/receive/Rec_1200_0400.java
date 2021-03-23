package com.wujie.pcclient.netty.client.receive;

import com.alibaba.fastjson.JSONObject;
import com.wujie.pcclient.netty.pojo.Rec_task_i;
import io.netty.channel.ChannelHandlerContext;

public class Rec_1200_0400 implements Rec_task_i {
    @Override
    public void doTask(ChannelHandlerContext ctx, String tx, JSONObject objParam) {
//        Log.v(WjDecoderHandler.TAG, "手机配置网关:" + tx);
//        nettyTcpClient.nettyNetGetDevListOver(tx, objParam);
    }
}
