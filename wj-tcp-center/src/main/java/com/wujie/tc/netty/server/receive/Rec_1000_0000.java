package com.wujie.tc.netty.server.receive;


import com.alibaba.fastjson.JSONObject;
import com.wujie.common.base.ApiResult;
import com.wujie.fclient.service.AppUserService;
import com.wujie.tc.netty.pojo.Rec_task_i;
import com.wujie.tc.netty.protocol.WjProtocol;
import com.wujie.tc.netty.server.send.Sen_0000_0000;
import com.wujie.tc.netty.server.send.Sen_factory;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * 终端←→服务 业务
 */
@Slf4j
public class Rec_1000_0000 implements Rec_task_i {
    AppUserService appUserService;
    @Override
    public void doTask(ChannelHandlerContext ctx, String tx, JSONObject objParam) {
        log.info( "===============Rec_1000_0000收到的tx==========" + tx);
        ApiResult apiResult = appUserService.doAtTask(tx);
        if(!ApiResult.SUCCESS.equals(apiResult.get(ApiResult.RETURNCODE)))
            log.info( "===============Rec_1000_0000收到的appUserService.doAtTask错误==========" + apiResult.get(ApiResult.MESSAGE));
    }

    @Override
    public void setService(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

}
