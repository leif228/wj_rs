package com.wujie.pcclient.netty.pojo;

import com.alibaba.fastjson.JSONObject;
import com.wujie.pcclient.app.business.enums.NetManageEnum;
import io.netty.channel.ChannelHandlerContext;

public interface Rec_task_i {
    public void doTask(ChannelHandlerContext ctx, String tx, JSONObject objParam, NetManageEnum type);
}
