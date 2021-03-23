package com.wujie.pcclient.netty.client;

import com.alibaba.fastjson.JSONObject;
import com.wujie.pcclient.app.business.enums.WebViewWebSocketFuctionEnum;
import com.wujie.pcclient.app.business.util.WechatConstant;
import com.wujie.pcclient.app.framework.netty.NioWebSocketHandler;
import com.wujie.pcclient.netty.client.decoder.TaskHandler;
import com.wujie.pcclient.netty.client.decoder.WjDecoderHandler;
import com.wujie.pcclient.netty.client.encoder.WjEncoderHandler;
import com.wujie.pcclient.netty.client.send.Sen_0000_0100;
import com.wujie.pcclient.netty.client.send.Sen_1000_0000;
import com.wujie.pcclient.netty.client.send.Sen_factory;
import com.wujie.pcclient.netty.pojo.*;
import com.wujie.pcclient.netty.protocol.AtProtocol;
import com.wujie.pcclient.netty.protocol.WjProtocol;
import com.wujie.pcclient.netty.utils.FileUtils;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;
import java.util.Properties;
import java.util.Queue;
import java.util.concurrent.TimeUnit;

@Slf4j
public class TcpClient {

    private static String ip;
    private static Integer port;
    private static NioEventLoopGroup worker ;

    private static Channel channel;

    private static Bootstrap bootstrap;
    private static WechatConstant wechatConstant;
    private static Queue<WjProtocol> queue;
    private static NioWebSocketHandler nioWebSocketHandler;

    public static int init() {
        try {
            log.info("启动tcp客户端:去连接:" + ip + ":" + port);
            queue = new LinkedList<WjProtocol>();
            bootstrap = new Bootstrap();
            worker = new NioEventLoopGroup();
            bootstrap.group(worker);
            bootstrap.channel(NioSocketChannel.class);
            bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
            bootstrap.handler(new ChannelInitializer() {
                @Override
                protected void initChannel(Channel ch) throws Exception {
//                ch.pipeline().addLast("logging",new LoggingHandler("DEBUG"));
                    //五秒没有收到消息 将IdleStateHandler 添加到 ChannelPipeline 中
                    ch.pipeline().addLast(new IdleStateHandler(0, 30, 0));
                    ch.pipeline().addLast(new WjEncoderHandler());
//                    ch.pipeline().addLast(new WjEchoHandler());
                    ch.pipeline().addLast(new WjDecoderHandler(new TaskHandler()));//解码器，接收消息时候用
//                    ch.pipeline().addLast(new InBusinessHandler());//业务处理类，最终的消息会在这个handler中进行业务处理
                }
            });
//            bootstrap.remoteAddress(ip, port);
//            ChannelFuture future = bootstrap.connect().sync();//使用了Future来启动线程
//
//            future.channel().closeFuture().sync();//以异步的方式关闭端口
            doConnect();

            return 0;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return 2;
        }
    }

    /**
     * 连接服务端 and 重连
     */
    public static void doConnect() throws InterruptedException {

        if (channel != null && channel.isActive()) {
            return;
        }
        bootstrap.remoteAddress(ip, port);
        ChannelFuture connect = bootstrap.connect();//使用了Future来启动线程
        //实现监听通道连接的方法
        connect.addListener(new ChannelFutureListener() {

            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {

                if (channelFuture.isSuccess()) {
                    channel = channelFuture.channel();
                    sendLoginData();
                    doTask();
                    log.info("连接成功:" + ip + ":" + port);
                } else {

                    channelFuture.channel().eventLoop().schedule(new Runnable() {

                        @SneakyThrows
                        @Override
                        public void run() {
                            log.info("每隔5s重连....");
                            doConnect();
                        }
                    }, 5, TimeUnit.SECONDS);
                }
            }
        });
//        connect.channel().closeFuture().sync();//以异步的方式关闭端口
    }

    public static void closeConnect() {
        try {
            if(channel !=null && worker != null){
                log.info("关闭tcp客户端:" + ip + ":" + port);
                channel.closeFuture().sync();//以异步的方式关闭端口
                worker.shutdownGracefully().sync();
                bootstrap=null;
                queue = null;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void sendMsgToManageService(WebViewWebSocketFuctionEnum webViewWebSocketFuctionEnum, Object data, NioWebSocketHandler nioWebSocketHandler) {
        TcpClient.nioWebSocketHandler = nioWebSocketHandler;
        switch (webViewWebSocketFuctionEnum){
            case sendChatMsg:
                ManageChatMsgTask manageChatMsgTask = JSONObject.parseObject(data.toString(),ManageChatMsgTask.class);
                sendChatMsg(webViewWebSocketFuctionEnum,manageChatMsgTask);
                break;
            case toGenEvent:
                ManageChatMsgTask manageChatMsgTask2 = JSONObject.parseObject(data.toString(),ManageChatMsgTask.class);
                toGenEvent(webViewWebSocketFuctionEnum,manageChatMsgTask2);
                break;
            case toLightOn:
                ManageLightTask manageLightTask = JSONObject.parseObject(data.toString(),ManageLightTask.class);
                toLightOn(webViewWebSocketFuctionEnum,manageLightTask.getAt());
                break;
            case toLightOff:
                ManageLightTask manageLightTask2 = JSONObject.parseObject(data.toString(),ManageLightTask.class);
                toLightOff(webViewWebSocketFuctionEnum,manageLightTask2.getAt());
                break;
            case toAt:
                ManageLightTask manageLightTask3 =   JSONObject.parseObject(data.toString(),ManageLightTask.class);
                toAt(webViewWebSocketFuctionEnum, manageLightTask3.getAt());
                break;
            default:
        }
    }

    private static void toAt(WebViewWebSocketFuctionEnum type,String at) {
        log.info("at事件" );

        AtTask atTask = new AtTask();
        atTask.setAt(at);

        WjProtocol wjProtocol = Sen_factory.getInstance(Sen_1000_0000.main, Sen_1000_0000.sub, atTask);
        if (wjProtocol == null)
            return;

        if (channel != null && channel.isActive()) {
            channel.writeAndFlush(wjProtocol);
            TcpClient.nioWebSocketHandler.backMsg(type, "发送at事件成功");
        } else {
            queue.offer(wjProtocol);
        }
    }

    //发送聊天消息
    private static void sendChatMsg(WebViewWebSocketFuctionEnum type, ManageChatMsgTask manageChatMsgTask) {
        ManageChatMsgAtParam manageChatMsgAtParam = new ManageChatMsgAtParam();
        manageChatMsgAtParam.setEventNo(manageChatMsgTask.getEventNo());
        manageChatMsgAtParam.setMsg(manageChatMsgTask.getMsg());
        manageChatMsgAtParam.setMsgType(manageChatMsgTask.getMsgType());

        String json = JSONObject.toJSONString(manageChatMsgAtParam);
        log.debug("文本消息参数json：" + json);

        String at = genAt("N", manageChatMsgTask.getOid(), "0500", "E001", "0001", "0001", json);
        AtTask atTask = new AtTask();
        atTask.setAt(at);

        WjProtocol wjProtocol = Sen_factory.getInstance(Sen_1000_0000.main, Sen_1000_0000.sub, atTask);
        if (wjProtocol == null)
            return;

        if (channel != null && channel.isActive()) {
            channel.writeAndFlush(wjProtocol);
            TcpClient.nioWebSocketHandler.backMsg(type,  "发送聊天消息成功");
        } else {
            queue.offer(wjProtocol);
        }
    }

    private static void toLightOff(WebViewWebSocketFuctionEnum type, String at) {
        log.info("关灯事件" );

//        String at = "AT@Nchn0L0a30202010260001100000000012120101100150100001FFFF001C{\"way\":\"ctl\",\"val\":\"000000\"}#*";
        AtTask atTask = new AtTask();
        atTask.setAt(at);

        WjProtocol wjProtocol = Sen_factory.getInstance(Sen_1000_0000.main, Sen_1000_0000.sub, atTask);
        if (wjProtocol == null)
            return;

        if (channel != null && channel.isActive()) {
            channel.writeAndFlush(wjProtocol);
            TcpClient.nioWebSocketHandler.backMsg(type, "发送关灯事件成功");
        } else {
            queue.offer(wjProtocol);
        }
    }

    private static void toLightOn(WebViewWebSocketFuctionEnum type, String at) {
        log.info("开灯事件" );

//        String at = "AT@Nchn0L0a30202010260001100000000012120101100150100001FFFF001C{\"way\":\"ctl\",\"val\":\"FFFFFF\"}#*";
        AtTask atTask = new AtTask();
        atTask.setAt(at);

        WjProtocol wjProtocol = Sen_factory.getInstance(Sen_1000_0000.main, Sen_1000_0000.sub, atTask);
        if (wjProtocol == null)
            return;

        if (channel != null && channel.isActive()) {
            channel.writeAndFlush(wjProtocol);
            TcpClient.nioWebSocketHandler.backMsg(type,  "发送开灯事件成功");
        } else {
            queue.offer(wjProtocol);
        }
    }

    private static void toGenEvent(WebViewWebSocketFuctionEnum type, ManageChatMsgTask manageChatMsgTask) {
        log.info("事件产生json：" + manageChatMsgTask.getOid());

        String at = genAt("N", manageChatMsgTask.getOid(), "0500", "A001", "0001", "0001", manageChatMsgTask.getOid());
        AtTask atTask = new AtTask();
        atTask.setAt(at);

        WjProtocol wjProtocol = Sen_factory.getInstance(Sen_1000_0000.main, Sen_1000_0000.sub, atTask);
        if (wjProtocol == null)
            return;

        if (channel != null && channel.isActive()) {
            channel.writeAndFlush(wjProtocol);
            TcpClient.nioWebSocketHandler.backMsg(type, "发送事件产生消息成功");
        } else {
            queue.offer(wjProtocol);
        }
    }

    /**
     * 向服务端发送消息
     */
    private static void sendLoginData() {
        if (channel != null && channel.isActive()) {

            LoginTask loginTask = new LoginTask();
            Properties properties = FileUtils.readFile(wechatConstant.getTcpClientConfigPath());
            if (properties.getProperty("fzwno") != null)
                loginTask.setOID(properties.getProperty("fzwno"));
            else
                loginTask.setOID("0000000000");

            WjProtocol wjProtocol = Sen_factory.getInstance(Sen_0000_0100.main, Sen_0000_0100.sub,loginTask);
            if(wjProtocol == null)
                return;


            channel.writeAndFlush(wjProtocol);
        }
    }
    public static int sendAtTask(String at) {
        if (channel != null && channel.isActive()) {

            AtTask atTask = new AtTask();
            atTask.setAt(at);

            WjProtocol wjProtocol = Sen_factory.getInstance(Sen_1000_0000.main, Sen_1000_0000.sub,atTask);
            if(wjProtocol == null)
                return 2;

            channel.writeAndFlush(wjProtocol);

            return 0;
        }else {
            return 1;
        }
    }

    private static void doTask() {
        boolean flag = true;

        if (channel != null && channel.isActive()) {

            while (flag) {
                if (!queue.isEmpty()) {
                    channel.writeAndFlush(queue.poll());
                } else {
                    flag = false;
                }
            }
        }
    }

    private static String genAt(String flag, String oid, String pri, String buss, String port, String cmd, String param) {
        //AT@Nchn0L0a002020091100011000000000E00200010500A001000100010027chn0L0a00202009110001100000000011110102#*

        String paramLen = AtProtocol.IntToHexStringLimit4(param.length());
        String at = AtProtocol.HEARD + flag + oid + pri + buss + port + cmd + paramLen + param + AtProtocol.END;//取出帧头

        log.info("编码后的at为：" + at);

        return at;
    }

    public static void main(String[] args) throws InterruptedException {
        try {
            Properties properties = FileUtils.readFile("E:\\config.properties");

            ip = properties.getProperty("ip");
            port = Integer.valueOf(properties.getProperty("port"));
            if (ip != null && port != null)
                init();
            else{
                ip = "127.0.0.1";
                port = 8777;
                init();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int startTcpClient(WechatConstant wechatConstant) {
        TcpClient.wechatConstant = wechatConstant;
        Properties properties = FileUtils.readFile(wechatConstant.getTcpClientConfigPath());
        ip = properties.getProperty("ip");
        port = Integer.valueOf(properties.getProperty("port"));
        if (ip != null && port != null) {

            return init();
        } else {
            return 1;
        }
    }
}
