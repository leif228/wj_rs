package com.wujie.pcclient.netty.client.receive;

import com.alibaba.fastjson.JSONObject;
import com.wujie.pcclient.app.business.enums.NetManageEnum;
import com.wujie.pcclient.netty.client.GTcpClient;
import com.wujie.pcclient.netty.pojo.Rec_task_i;
import io.netty.channel.ChannelHandlerContext;

public class Rec_1200_0200 implements Rec_task_i {
    @Override
    public void doTask(ChannelHandlerContext ctx, String tx, JSONObject objParam, NetManageEnum type) {
//        Log.v(WjDecoderHandler.TAG, "获取设备列表完成，页面打开配置页:" + objParam.toJSONString());
        GTcpClient.nettyNetGetDevListOver(tx, objParam);
    }
}
