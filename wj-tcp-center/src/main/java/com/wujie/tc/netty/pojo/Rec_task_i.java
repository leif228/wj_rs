package com.wujie.tc.netty.pojo;

import com.alibaba.fastjson.JSONObject;
import io.netty.channel.ChannelHandlerContext;

public interface Rec_task_i {
    public void doTask(ChannelHandlerContext ctx, String tx, JSONObject objParam);
}
