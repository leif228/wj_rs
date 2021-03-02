package com.wujie.apps.netty.client.business;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WjEchoHandler extends ChannelInboundHandlerAdapter {

    //连接成功后发送登录消息
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//        WjProtocol wjProtocol = new WjProtocol();
//        wjProtocol.setPlat(Short.parseShort("20"));
//        wjProtocol.setMaincmd(Short.parseShort("0"));
//        wjProtocol.setSubcmd(Short.parseShort("1"));
//        wjProtocol.setFormat("JS");
//        wjProtocol.setBack(Short.parseShort("0"));
//
//        LoginTask loginTask = new LoginTask();
//        Properties properties=FileUtils.readFile("E:\\config.properties");
//        if(properties != null)
//            loginTask.setOid(properties.getProperty("fzwno"));
//        else
//            loginTask.setOid("88888888");
//
////        byte [] objectBytes= ByteUtils.InstanceObjectMapper().writeValueAsBytes(loginTask);
//
//        String jsonStr = JSONObject.toJSONString(loginTask);
//        log.debug(jsonStr);
//        byte [] objectBytes= jsonStr.getBytes();
//
//        int len = 21+objectBytes.length;
//        wjProtocol.setLen((short) len);
//        wjProtocol.setUserdata(objectBytes);
//
//        ctx.write(wjProtocol);
//
//        ctx.flush();


    }
}
