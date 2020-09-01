package com.wujie.ac.app.business.entity.wjhttp;

import com.alibaba.fastjson.JSONObject;
import io.netty.channel.ChannelHandlerContext;

import javax.servlet.http.HttpServletResponse;

public interface Rec_task_i {
    public void doTask(HttpServletResponse response, String tx, JSONObject objParam);
    public BaseTask backUserData();
    public byte[] backSendData();
}
