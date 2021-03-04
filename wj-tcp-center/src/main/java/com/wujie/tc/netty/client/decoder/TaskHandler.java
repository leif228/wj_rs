package com.wujie.tc.netty.client.decoder;


import com.alibaba.fastjson.JSONObject;
import com.wujie.tc.netty.client.send.Sen_0000_0000;
import com.wujie.tc.netty.client.send.Sen_factory;
import com.wujie.tc.netty.pojo.Rec_task_i;
import com.wujie.tc.netty.protocol.WjProtocol;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TaskHandler {

    public void doProtocol(ChannelHandlerContext ctx, WjProtocol wjProtocol) {
        JSONObject objParam = null;
        String tx = "";

        if (WjProtocol.FORMAT_TX.equals(wjProtocol.getFormat())) {
            if(wjProtocol.getUserdata() != null){

                String dataStr = new String(wjProtocol.getUserdata());
                tx = dataStr;
            }
        } else if (WjProtocol.FORMAT_JS.equals(wjProtocol.getFormat())) {
            if(wjProtocol.getUserdata() != null){

                String jsonStr = new String(wjProtocol.getUserdata());
                objParam = JSONObject.parseObject(jsonStr);
            }
        } else if (WjProtocol.FORMAT_AT.equals(wjProtocol.getFormat())) {
            if(wjProtocol.getUserdata() != null){

                tx = new String(wjProtocol.getUserdata());
            }
        }

        //======业务处理======
//        if (Arrays.toString(new Byte[]{0x00, 0x00}).equals(Arrays.toString(wjProtocol.getMaincmd()))
//                && Arrays.toString(new Byte[]{0x00, 0x00}).equals(Arrays.toString(wjProtocol.getSubcmd()))) {//服务→终端 心跳pong
//            this.nettyReq(ctx);
//        }

        //通用处理
        try {
            String classPath = "com.wujie.tc.netty.client.receive";
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
//    private void nettyReq(ChannelHandlerContext ctx) {
//    }
    public void sendReq(ChannelHandlerContext ctx) {
//        WjProtocol wjProtocol = new WjProtocol();
//        wjProtocol.setPlat(new byte[]{0x20,0x00});
//        wjProtocol.setMaincmd(new byte[]{0x00,0x00});
//        wjProtocol.setSubcmd(new byte[]{0x00,0x00});
//        wjProtocol.setFormat("TX");
//        wjProtocol.setBack(new byte[]{0x00,0x00});
//
//        int len = WjProtocol.MIN_DATA_LEN + 0;
//        wjProtocol.setLen(wjProtocol.short2byte((short) len));

        WjProtocol wjProtocol = Sen_factory.getInstance(Sen_0000_0000.main, Sen_0000_0000.sub,null);
        if(wjProtocol == null)
            return;

        ctx.write(wjProtocol);

        ctx.flush();
    }

}
