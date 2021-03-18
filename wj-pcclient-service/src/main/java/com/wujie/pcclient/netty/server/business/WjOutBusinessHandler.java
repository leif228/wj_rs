package com.wujie.pcclient.netty.server.business;

import com.wujie.pcclient.netty.protocol.WjProtocol;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WjOutBusinessHandler extends MessageToByteEncoder {

    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
        log.debug("OutBusinessHandler#encode 服务端 发送数据 ... :" + ctx.channel().remoteAddress().toString() + "==>数据：" + msg.toString());
        if (msg instanceof WjProtocol) {
            WjProtocol protocol = (WjProtocol) msg;
            out.writeBytes(protocol.getHeader().getBytes());

//            out.writeShort(protocol.getLen());
            out.writeBytes(protocol.getLen());

            out.writeByte(protocol.getVer());
            out.writeByte(protocol.getEncrypt());

//            out.writeShort(protocol.getPlat());
            out.writeBytes(protocol.getPlat());

//            out.writeShort(protocol.getMaincmd());
            out.writeBytes(protocol.getMaincmd());

//            out.writeShort(protocol.getSubcmd());
            out.writeBytes(protocol.getSubcmd());

            out.writeBytes(protocol.getFormat().getBytes());

//            out.writeShort(protocol.getBack());
            out.writeBytes(protocol.getBack());

            if (protocol.getUserdata() != null)
                out.writeBytes(protocol.getUserdata());

//            out.writeByte(protocol.getCheckSum());
            out.writeByte(protocol.getCheckSum(protocol));

            log.debug("数据编码成功：" + out.readableBytes());
        } else {
            log.debug("不支持的数据协议：" + msg.getClass() + "\t期待的数据协议类是：" + WjProtocol.class);
        }
    }


}
