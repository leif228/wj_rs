package com.wujie.tc.netty.server.receive;


import com.alibaba.fastjson.JSONObject;
import com.wujie.fclient.service.AppUserService;
import com.wujie.tc.netty.pojo.Device;
import com.wujie.tc.netty.pojo.LoginTask;
import com.wujie.tc.netty.pojo.Rec_task_i;
import com.wujie.tc.netty.server.send.Sen_0000_0100;
import com.wujie.tc.netty.server.send.Sen_factory;
import com.wujie.tc.netty.protocol.WjProtocol;
import com.wujie.tc.netty.server.ChannelManager;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;

/**
 * 终端→服务 登录
 */

@Slf4j
public class Rec_0000_0100 implements Rec_task_i {
    @Override
    public void doTask(ChannelHandlerContext ctx, String tx, JSONObject objParam) {
        log.debug( "===============Rec_0000_0100收到的objParam==========" + objParam.toJSONString());
        if (ObjectUtils.isEmpty(objParam)) {
            ctx.channel().close();
        }
//        JSONObject jsonObject = JSONObject.parseObject(data.toString());
        LoginTask loginTask = JSONObject.toJavaObject(objParam, LoginTask.class);
//        Object userId1 = objParam.get("OID");
//        String oid = JSON.parseObject(userId1.toString(), String.class);
        String oid = loginTask.getOID();
//        UserInfoVo userInfoVo = appUserService.getUserInfoById(userId);
        Device device = new Device(oid, System.currentTimeMillis() + "");
        log.debug( "===============Rec_0000_0100.ChannelManager.deviceChannels数量：==========" + ChannelManager.deviceChannels.size());
        log.debug( "===============Rec_0000_0100.收到的oid：==========" + oid);
        if (ChannelManager.deviceChannels.containsKey(device.getUniqueNo())) {

            log.debug( "===============Rec_0000_0100.ChannelManager.deviceChannels.containsKey==========" + device.getUniqueNo());
            Channel channel = ChannelManager.deviceChannels.get(device.getUniqueNo());
            // TODO 需要定义返回的JSON格式，通知用户被挤下去了
            channel.close();
        }
        ctx.channel().attr(ChannelManager.deviceInfoVoAttr).set(device);
        ChannelManager.deviceChannels.put(device.getUniqueNo(), ctx.channel());

        sendLoginBack(ctx);
    }

    @Override
    public void setService(AppUserService appUserService) {

    }

    private void sendLoginBack(ChannelHandlerContext ctx) {
//        WjProtocol wjProtocol = new WjProtocol();
//        wjProtocol.setPlat(new byte[]{0x20, 0x00});
//        wjProtocol.setMaincmd(new byte[]{0x00, 0x00});
//        wjProtocol.setSubcmd(new byte[]{0x01, 0x00});
//        wjProtocol.setFormat("JS");
//        wjProtocol.setBack(new byte[]{0x01, 0x00});
//
//        int len = WjProtocol.MIN_DATA_LEN + 0;
//        wjProtocol.setLen(wjProtocol.short2byte((short) len));

        WjProtocol wjProtocol = Sen_factory.getInstance(Sen_0000_0100.main, Sen_0000_0100.sub,null);
        if(wjProtocol == null)
            return;

        ctx.write(wjProtocol);

        ctx.flush();
    }
}
