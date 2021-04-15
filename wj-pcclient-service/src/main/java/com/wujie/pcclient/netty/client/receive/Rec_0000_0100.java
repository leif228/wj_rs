package com.wujie.pcclient.netty.client.receive;

import com.alibaba.fastjson.JSONObject;
import com.wujie.pcclient.app.business.enums.NetManageEnum;
import com.wujie.pcclient.netty.pojo.Rec_task_i;
import io.netty.channel.ChannelHandlerContext;

public class Rec_0000_0100 implements Rec_task_i {
    @Override
    public void doTask(ChannelHandlerContext ctx, String tx, JSONObject objParam, NetManageEnum type) {
//        Log.v(WjDecoderHandler.TAG, "tcp登录返回:" + tx);
    }
}
