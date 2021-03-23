package com.wujie.pcclient.netty.client.receive;

import com.alibaba.fastjson.JSONObject;
import com.wujie.pcclient.netty.pojo.Rec_task_i;
import io.netty.channel.ChannelHandlerContext;

public class Rec_0000_0300 implements Rec_task_i {
    @Override
    public void doTask(ChannelHandlerContext ctx, String tx, JSONObject objParam) {
//        Log.v(WjDecoderHandler.TAG, "网关返回任务进度:" + objParam.toJSONString());
    }
}
