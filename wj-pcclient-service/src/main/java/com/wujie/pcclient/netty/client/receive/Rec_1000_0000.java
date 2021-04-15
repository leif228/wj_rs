package com.wujie.pcclient.netty.client.receive;


import com.alibaba.fastjson.JSONObject;
import com.wujie.pcclient.app.business.enums.NetManageEnum;
import com.wujie.pcclient.netty.client.GTcpClient;
import com.wujie.pcclient.netty.client.TcpClient;
import com.wujie.pcclient.netty.pojo.Rec_task_i;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * at 业务
 */
@Slf4j
public class Rec_1000_0000 implements Rec_task_i {
    @Override
    public void doTask(ChannelHandlerContext ctx, String tx, JSONObject objParam, NetManageEnum type) {
        log.debug("收到"+type+"返回的at业务:" + tx);
        if (objParam != null)
            log.debug("收到"+type+"返回的at业务:" + tx + ":" + objParam.toJSONString());

        if(type == NetManageEnum.Net)
            GTcpClient.atTask(type,tx, objParam);
        else if(type == NetManageEnum.Manage)
            TcpClient.atTask(type,tx, objParam);
    }

}
