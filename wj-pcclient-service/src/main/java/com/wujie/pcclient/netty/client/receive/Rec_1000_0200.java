package com.wujie.pcclient.netty.client.receive;


import com.alibaba.fastjson.JSONObject;
import com.wujie.pcclient.netty.client.GTcpClient;
import com.wujie.pcclient.netty.pojo.Rec_task_i;
import io.netty.channel.ChannelHandlerContext;

/**
 * at 业务
 * 手机←→网关 业务
 */
public class Rec_1000_0200 implements Rec_task_i {
    @Override
    public void doTask(ChannelHandlerContext ctx, String tx, JSONObject objParam) {
//        Log.v(WjDecoderHandler.TAG, "at业务:" + tx);
        GTcpClient.atTask(tx, objParam);
    }

}
