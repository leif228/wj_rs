package com.wujie.pcclient.netty.server.receive;


import com.alibaba.fastjson.JSONObject;
import com.wujie.pcclient.app.business.enums.NetManageEnum;
import com.wujie.pcclient.netty.pojo.Rec_task_i;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * 终端←→服务 业务
 */
@Slf4j
public class Rec_1000_0000 implements Rec_task_i {
    @Override
    public void doTask(ChannelHandlerContext ctx, String tx, JSONObject objParam, NetManageEnum type) {
        log.debug( "===============Rec_1000_0000收到的tx==========" + tx);
//        ApiResult apiResult = appUserService.doAtTask(tx);
//        if(!ApiResult.SUCCESS.equals(apiResult.get(ApiResult.RETURNCODE)))
//            log.info( "===============Rec_1000_0000收到的appUserService.doAtTask错误==========" + apiResult.get(ApiResult.MESSAGE));
    }


}
