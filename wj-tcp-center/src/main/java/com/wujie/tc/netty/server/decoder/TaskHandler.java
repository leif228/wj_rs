package com.wujie.tc.netty.server.decoder;


import com.alibaba.fastjson.JSONObject;
import com.wujie.tc.netty.pojo.Rec_task_i;
import com.wujie.tc.netty.protocol.WjProtocol;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
@Slf4j
public class TaskHandler {

    public void doProtocol(ChannelHandlerContext ctx, WjProtocol wjProtocol) {
        JSONObject objParam = null;
        String tx = null;

        if (WjProtocol.FORMAT_TX.equals(wjProtocol.getFormat())) {
            if (wjProtocol.getUserdata() != null) {

                String dataStr = new String(wjProtocol.getUserdata());
                tx = dataStr;
            }
        } else if (WjProtocol.FORMAT_JS.equals(wjProtocol.getFormat())) {
            if (wjProtocol.getUserdata() != null) {

                String jsonStr = new String(wjProtocol.getUserdata());
                objParam = JSONObject.parseObject(jsonStr);
            }
        } else if (WjProtocol.FORMAT_AT.equals(wjProtocol.getFormat())) {
            if (wjProtocol.getUserdata() != null) {

                tx = new String(wjProtocol.getUserdata());
            }
        }

        //======业务处理======
//        if (Arrays.toString(new Byte[]{0x00, 0x00}).equals(Arrays.toString(wjProtocol.getMaincmd()))
//                && Arrays.toString(new Byte[]{0x01, 0x00}).equals(Arrays.toString(wjProtocol.getSubcmd()))) {//终端→服务 登录
//            this.nettyLogin(ctx, objParam);
//        }
//        if (Arrays.toString(new Byte[]{0x00, 0x00}).equals(Arrays.toString(wjProtocol.getMaincmd()))
//                && Arrays.toString(new Byte[]{0x00, 0x00}).equals(Arrays.toString(wjProtocol.getSubcmd()))) {//终端→服务 心跳ping
//            this.nettyIdle(ctx);
//        }

        //通用处理
        try {
            String classPath = "com.wujie.tc.netty.server.receive";
            String className = "Rec_" + wjProtocol.IntToHexStringLimit2(wjProtocol.getMaincmd()[0]) + wjProtocol.IntToHexStringLimit2(wjProtocol.getMaincmd()[1]) + "_"
                    + wjProtocol.IntToHexStringLimit2(wjProtocol.getSubcmd()[0]) + wjProtocol.IntToHexStringLimit2(wjProtocol.getSubcmd()[1]);
            className = classPath + "." + className;
            log.debug( "className:" + className);
            Class genClass = Class.forName(className);
            Rec_task_i rec_task_i = (Rec_task_i) genClass.newInstance();
            rec_task_i.doTask(ctx, tx, objParam);
        } catch (Exception e) {
            log.debug( "TaskHandler.doProtocol_报错了:" + e.getMessage());
        }
    }

//    private void nettyLogin(ChannelHandlerContext ctx, JSONObject objParam) {
//        if (ObjectUtils.isEmpty(objParam)) {
//            ctx.channel().close();
//        }
////        JSONObject jsonObject = JSONObject.parseObject(data.toString());
//        LoginTask loginTask = JSONObject.toJavaObject(objParam, LoginTask.class);
////        Object userId1 = objParam.get("OID");
////        String oid = JSON.parseObject(userId1.toString(), String.class);
//        String oid = loginTask.getOID();
////        UserInfoVo userInfoVo = appUserService.getUserInfoById(userId);
//        Device device = new Device(oid, System.currentTimeMillis() + "");
//        if (ChannelManager.deviceChannels.containsKey(device.getUniqueNo())) {
//            Channel channel = ChannelManager.deviceChannels.get(device.getUniqueNo());
//            // TODO 需要定义返回的JSON格式，通知用户被挤下去了
//            channel.close();
//        }
//        ctx.channel().attr(ChannelManager.deviceInfoVoAttr).set(device);
//        ChannelManager.deviceChannels.put(device.getUniqueNo(), ctx.channel());
//
//        sendLoginBack(ctx);
//    }

//    private void sendLoginBack(ChannelHandlerContext ctx) {
//        WjProtocol wjProtocol = new WjProtocol();
//        wjProtocol.setPlat(new byte[]{0x20, 0x00});
//        wjProtocol.setMaincmd(new byte[]{0x00, 0x00});
//        wjProtocol.setSubcmd(new byte[]{0x01, 0x00});
//        wjProtocol.setFormat("JS");
//        wjProtocol.setBack(new byte[]{0x01, 0x00});
//
//        int len = WjProtocol.MIN_DATA_LEN + 0;
//        wjProtocol.setLen(wjProtocol.short2byte((short) len));
//
//        ctx.write(wjProtocol);
//
//        ctx.flush();
//    }
//
//    private void nettyIdle(ChannelHandlerContext ctx) {
//        sendIdle(ctx);
//    }

//    private void sendIdle(ChannelHandlerContext ctx) {
//        WjProtocol wjProtocol = new WjProtocol();
//        wjProtocol.setPlat(new byte[]{0x20, 0x00});
//        wjProtocol.setMaincmd(new byte[]{0x00, 0x00});
//        wjProtocol.setSubcmd(new byte[]{0x00, 0x00});
//        wjProtocol.setFormat("TX");
//        wjProtocol.setBack(new byte[]{0x01, 0x00});
//
//        int len = WjProtocol.MIN_DATA_LEN + 0;
//        wjProtocol.setLen(wjProtocol.short2byte((short) len));
//
//        ctx.write(wjProtocol);
//
//        ctx.flush();
//    }
}
