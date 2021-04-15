package com.wujie.pcclient.netty.client.receive;

import com.alibaba.fastjson.JSONObject;
import com.wujie.pcclient.app.business.enums.NetManageEnum;
import com.wujie.pcclient.netty.client.GTcpClient;
import com.wujie.pcclient.netty.pojo.Rec_task_i;
import io.netty.channel.ChannelHandlerContext;

public class Rec_1200_0100 implements Rec_task_i {
    @Override
    public void doTask(ChannelHandlerContext ctx, String tx, JSONObject objParam, NetManageEnum type) {
//        Log.v(WjDecoderHandler.TAG, "驱动下载完成,打开认证页面:" + objParam.toJSONString());
        GTcpClient.nettyNetFileDownOver(tx, objParam);
    }
}
