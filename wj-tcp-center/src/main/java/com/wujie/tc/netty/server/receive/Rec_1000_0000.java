package com.wujie.tc.netty.server.receive;


import com.alibaba.fastjson.JSONObject;
import com.wujie.common.base.ApiResult;
import com.wujie.fclient.service.AppUserService;
import com.wujie.tc.netty.pojo.Rec_task_i;
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
        log.info("===============Rec_1000_0000收到的tx==========" + tx);
        Long startTime = System.currentTimeMillis();
        ApiResult apiResult = appUserService.doAtTask(tx);
        if (!ApiResult.SUCCESS.equals(apiResult.get(ApiResult.RETURNCODE))) {
            log.info("===============Rec_1000_0000处理-错误tx=" + tx + "==========" + apiResult.get(ApiResult.MESSAGE));
            Long endTime = (System.currentTimeMillis() - startTime);
            log.info("===============Rec_1000_0000处理-错误tx=" + tx + "==========耗时：" + endTime + "ms");
        } else {
            log.info("===============Rec_1000_0000处理-成功tx=" + tx + "==========" + apiResult.get(ApiResult.MESSAGE));
            Long endTime = (System.currentTimeMillis() - startTime);
            log.info("===============Rec_1000_0000处理-成功tx=" + tx + "==========耗时：" + endTime + "ms");

        }
    }

    @Override
    public void setService(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

}
