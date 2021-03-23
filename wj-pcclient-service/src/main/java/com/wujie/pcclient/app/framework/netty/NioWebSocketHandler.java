package com.wujie.pcclient.app.framework.netty;

import com.alibaba.fastjson.JSONObject;
import com.wujie.pcclient.app.business.enums.WebViewWebSocketFuctionEnum;
import com.wujie.pcclient.netty.client.GTcpClient;
import com.wujie.pcclient.netty.client.TcpClient;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NioWebSocketHandler extends SimpleChannelInboundHandler<Object> {


    private WebSocketServerHandshaker handshaker;
    public Channel channel;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.debug("收到消息：" + msg);
        if (msg instanceof FullHttpRequest) {
            //以http请求形式接入，但是走的是websocket
            handleHttpRequest(ctx, (FullHttpRequest) msg);
        } else if (msg instanceof WebSocketFrame) {
            //处理websocket客户端的消息
            handlerWebSocketFrame(ctx, (WebSocketFrame) msg);
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //添加连接
        log.debug("客户端加入连接：" + ctx.channel());
        channel = ctx.channel();
//        ChannelSupervise.addChannel(ctx.channel());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        //断开连接
        log.debug("客户端断开连接：" + ctx.channel());
//        ChannelSupervise.removeChannel(ctx.channel());
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        log.debug("channelReadComplete：" + ctx.channel());
        ctx.flush();
    }

    private void handlerWebSocketFrame(ChannelHandlerContext ctx, WebSocketFrame frame) {
        // 判断是否关闭链路的指令
        if (frame instanceof CloseWebSocketFrame) {
            handshaker.close(ctx.channel(), (CloseWebSocketFrame) frame.retain());
            if (channel != null && ctx.channel().id().asLongText().equals(channel.id().asLongText())) {

                channel = null;
            }
            return;
        }
        // 判断是否ping消息
        if (frame instanceof PingWebSocketFrame) {
            ctx.channel().write(
                    new PongWebSocketFrame(frame.content().retain()));
            return;
        }
        // 不支持二进制消息
        if (!(frame instanceof TextWebSocketFrame)) {
            log.debug("不支持二进制消息");
            throw new UnsupportedOperationException(String.format(
                    "%s frame types not supported", frame.getClass().getName()));
        }
        // 返回应答消息
        String request = ((TextWebSocketFrame) frame).text();
        log.debug("服务端收到：" + request);
//        TextWebSocketFrame tws = new TextWebSocketFrame(request);
        // 群发
//        ChannelSupervise.send2All(tws);
        // 返回【谁发的发给谁】
//         ctx.channel().writeAndFlush(tws);

        JSONObject param = null;
        try {
            param = JSONObject.parseObject(request);
        } catch (Exception e) {
            log.debug("不支持的消息数据格式！");
            return;
        }
        if (param == null) {
            log.debug("不支持的消息数据格式！不能为空！");
            return;
        }
        String type = param.get("type").toString();
        WebViewWebSocketFuctionEnum webViewWebSocketFuctionEnum = null;
        try {
            webViewWebSocketFuctionEnum = WebViewWebSocketFuctionEnum.valueOf(type);
        } catch (Exception e) {
            log.debug("不支持的消息数据格式！类别解析出错！");
            return;
        }
        Object data = param.get("data");
        switch (webViewWebSocketFuctionEnum) {
            case toHeart:
                backMsg(webViewWebSocketFuctionEnum, "心跳返回");//{"type":"toHeart","data":{}}
                break;

            case sendChatMsg:
                TcpClient.sendMsgToManageService(webViewWebSocketFuctionEnum, data, this);
                break;
            case toGenEvent:
                TcpClient.sendMsgToManageService(webViewWebSocketFuctionEnum, data, this);
                break;
            case toLightOn:
                TcpClient.sendMsgToManageService(webViewWebSocketFuctionEnum, data, this);
                break;
            case toLightOff:
                TcpClient.sendMsgToManageService(webViewWebSocketFuctionEnum, data, this);
                break;
            case toAt:
                TcpClient.sendMsgToManageService(webViewWebSocketFuctionEnum, data, this);
                break;

            case toNetInfo:
                GTcpClient.sendMsgToNetService(webViewWebSocketFuctionEnum, data, this);
                break;
            case deviceComp:
                GTcpClient.sendMsgToNetService(webViewWebSocketFuctionEnum, data, this);
                break;
            case saveDevice:
                GTcpClient.sendMsgToNetService(webViewWebSocketFuctionEnum, data, this);
                break;
            case authOver:
                GTcpClient.sendMsgToNetService(webViewWebSocketFuctionEnum, data, this);
                break;
            case toSearchNet:
                GTcpClient.sendMsgToNetService(webViewWebSocketFuctionEnum, data, this);
                break;
            case toNetTcp:
                GTcpClient.sendMsgToNetService(webViewWebSocketFuctionEnum, data, this);
                break;
            case toConfigNet:
                GTcpClient.sendMsgToNetService(webViewWebSocketFuctionEnum, data, this);
                break;
            case toAtNet:
                GTcpClient.sendMsgToNetService(webViewWebSocketFuctionEnum, data, this);
                break;
            default:
        }

    }

    public void backMsg(WebViewWebSocketFuctionEnum type, String msg) {
        JSONObject resultJson = new JSONObject();
        JSONObject resutlObj = new JSONObject();
        resutlObj.put("code", "0");
        resutlObj.put("msg", msg);

        resultJson.put("type", type.name());
        resultJson.put("data", resutlObj);
        TextWebSocketFrame homeownerResp = new TextWebSocketFrame(resultJson.toJSONString());
        if (channel != null && channel.isActive())
            channel.writeAndFlush(homeownerResp);
    }


    /**
     * 唯一的一次http请求，用于创建websocket
     */
    private void handleHttpRequest(ChannelHandlerContext ctx,
                                   FullHttpRequest req) {
        //要求Upgrade为websocket，过滤掉get/Post
        if (!req.decoderResult().isSuccess()
                || (!"websocket".equals(req.headers().get("Upgrade")))) {
            //若不是websocket方式，则创建BAD_REQUEST的req，返回给客户端
            sendHttpResponse(ctx, req, new DefaultFullHttpResponse(
                    HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST));
            return;
        }
        WebSocketServerHandshakerFactory wsFactory = new WebSocketServerHandshakerFactory(
                "ws://localhost:8081/websocket", null, false);
        handshaker = wsFactory.newHandshaker(req);
        if (handshaker == null) {
            WebSocketServerHandshakerFactory
                    .sendUnsupportedVersionResponse(ctx.channel());
        } else {
            handshaker.handshake(ctx.channel(), req);
        }
    }

    /**
     * 拒绝不合法的请求，并返回错误信息
     */
    private static void sendHttpResponse(ChannelHandlerContext ctx,
                                         FullHttpRequest req, DefaultFullHttpResponse res) {
        // 返回应答给客户端
        if (res.status().code() != 200) {
            ByteBuf buf = Unpooled.copiedBuffer(res.status().toString(),
                    CharsetUtil.UTF_8);
            res.content().writeBytes(buf);
            buf.release();
        }
        ChannelFuture f = ctx.channel().writeAndFlush(res);
        // 如果是非Keep-Alive，关闭连接
//        if (!isKeepAlive(req) || res.status().code() != 200) {
//            f.addListener(ChannelFutureListener.CLOSE);
//        }
    }
}
