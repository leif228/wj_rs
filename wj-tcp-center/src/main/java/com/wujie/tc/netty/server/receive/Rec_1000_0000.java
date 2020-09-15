package com.wujie.tc.netty.server.receive;


import com.alibaba.fastjson.JSONObject;
import com.wujie.common.base.ApiResult;
import com.wujie.fclient.service.AppUserService;
import com.wujie.tc.netty.pojo.Rec_task_i;
import com.wujie.tc.netty.protocol.WjProtocol;
import com.wujie.tc.netty.server.send.Sen_0000_0000;
import com.wujie.tc.netty.server.send.Sen_factory;
import io.netty.channel.ChannelHandlerContext;

/**
 * 终端←→服务 业务
 */
public class Rec_1000_0000 implements Rec_task_i {
    AppUserService appUserService;
    @Override
    public void doTask(ChannelHandlerContext ctx, String tx, JSONObject objParam) {
        ApiResult apiResult = appUserService.doAtTask(tx);
    }

    @Override
    public void setService(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

}
