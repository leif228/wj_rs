package com.wujie.pcclient.netty.client;

import com.alibaba.fastjson.JSONObject;
import com.wujie.pcclient.app.business.enums.NetManageEnum;
import com.wujie.pcclient.app.business.enums.WebViewWebSocketFuctionEnum;
import com.wujie.pcclient.app.business.util.WechatConstant;
import com.wujie.pcclient.app.framework.netty.NioWebSocketHandler;
import com.wujie.pcclient.netty.client.GUtil.BroadCastToCenter;
import com.wujie.pcclient.netty.client.decoder.GWjDecoderHandler;
import com.wujie.pcclient.netty.client.decoder.TaskHandler;
import com.wujie.pcclient.netty.client.encoder.WjEncoderHandler;
import com.wujie.pcclient.netty.client.send.*;
import com.wujie.pcclient.netty.pojo.*;
import com.wujie.pcclient.netty.protocol.WjProtocol;
import com.wujie.pcclient.netty.utils.FileUtils;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Slf4j
public class GTcpClient {

    private static String ip;
    private static Integer port = 8666;
    private static NioEventLoopGroup worker ;

    private static Channel channel;

    private static Bootstrap bootstrap;
    private static WechatConstant wechatConstant;
    private static NioWebSocketHandler nioWebSocketHandler;
    private static Queue<WjProtocol> queue;
    private static boolean isSearching = false;
    static Timer timer = new Timer();
    static List<NetSearchNetDto> netSearchNetDtos = new ArrayList<>();

    public static void setNioWebSocketHandler(NioWebSocketHandler nioWebSocketHandler){
        GTcpClient.nioWebSocketHandler = nioWebSocketHandler;
    }

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
                    ch.pipeline().addLast(new GWjDecoderHandler(new TaskHandler(NetManageEnum.Net)));//解码器，接收消息时候用
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
            log.error("doConnet连接报错了"+ e.getMessage());
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
//                    sendLoginData();
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
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            log.info("关闭tcp客户端报错了:" + e.getMessage());
        }
    }

    public static  void sendMsgToNetService(WebViewWebSocketFuctionEnum webViewWebSocketFuctionEnum, Object data) {
        switch (webViewWebSocketFuctionEnum) {
            case toNetInfo:
                NetInfoTask netInfoTask = JSONObject.parseObject(data.toString(), NetInfoTask.class);
                toNetInfo(webViewWebSocketFuctionEnum,netInfoTask);
                break;
            case deviceComp:
                NetDevCompFileTask netDevCompTask = JSONObject.parseObject(data.toString(), NetDevCompFileTask.class);
                deviceComp(webViewWebSocketFuctionEnum,netDevCompTask);
                break;
            case saveDevice:
                NetDevCompTask netDevCompTask2 = JSONObject.parseObject(data.toString(), NetDevCompTask.class);
                saveDevice(webViewWebSocketFuctionEnum,netDevCompTask2);
                break;
            case authOver:
                NetDevCompFileTask netDevCompFileTask = JSONObject.parseObject(data.toString(), NetDevCompFileTask.class);
                authOver(webViewWebSocketFuctionEnum,netDevCompFileTask);
                break;
            case toSearchNet:
                toSearchNet(webViewWebSocketFuctionEnum,new NetSearchNetTask());
                break;
            case toNetTcp:
                String ip = (String) data;
                toNetTcp(webViewWebSocketFuctionEnum,ip);
                break;
            case toConfigNet:
                NetConfigTask netDevCompTask3 = JSONObject.parseObject(data.toString(), NetConfigTask.class);
                toConfigNet(webViewWebSocketFuctionEnum,netDevCompTask3);
                break;
            case toAtNet:
                String at = (String) data;
                toAtNet(webViewWebSocketFuctionEnum,at);
                break;
            default:
        }
    }

    private static void toSearchNet(WebViewWebSocketFuctionEnum webViewWebSocketFuctionEnum, NetSearchNetTask netSearchNetTask) {
        if (!isSearching) {
            isSearching = true;
            BroadCastToCenter broadCastToCenter = new BroadCastToCenter(netSearchNetTask);
            broadCastToCenter.start();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    netSearchNetDtos = broadCastToCenter.getNetSearchNetDtos();
                    broadCastToCenter.stopStay();
                    isSearching = false;
                    backNets();
                }
            }, 3000l);
        }
    }

    private static void backNets() {
        NetSearchNetDtos netSearchNetDtoList = new NetSearchNetDtos();
        netSearchNetDtoList.setNetSearchNetDtos(netSearchNetDtos);
        String list = JSONObject.toJSONString(netSearchNetDtoList);
        GTcpClient.nioWebSocketHandler.backMsg(WebViewWebSocketFuctionEnum.backNets, list);
    }

    //手机←→网关 业务
    private static void toAtNet(WebViewWebSocketFuctionEnum webViewWebSocketFuctionEnum, String at) {
        AtTask atTask = new AtTask();
        atTask.setAt(at);
        WjProtocol wjProtocol = Sen_factory.getInstance(Sen_1000_0200.main, Sen_1000_0200.sub, atTask);
        if (wjProtocol == null)
            return;

        if (channel != null && channel.isActive()) {
            channel.writeAndFlush(wjProtocol);
            GTcpClient.nioWebSocketHandler.backMsg(webViewWebSocketFuctionEnum, "发送at命令成功");
        } else {
            queue.offer(wjProtocol);
        }
    }

    private static void toNetTcp(WebViewWebSocketFuctionEnum webViewWebSocketFuctionEnum, String netIp) {
        log.debug("connectNet  ip=" + netIp);
        ip = netIp;

        if (channel != null) {
//            closeConnect();
            try {
                log.debug("reconnectNet  ip=" + netIp);
                doConnect();
            } catch (InterruptedException e) {
                e.printStackTrace();
                log.debug("reconnectNet报错了  ip=" + netIp);
            }
        }

        startTcpClient();
    }

    //手机→网关 搜索网关(目前遍历)
//    private void toSearchNet() {
//
//        WjProtocol wjProtocol = Sen_factory.getInstance(Sen_1200_0000.main, Sen_1200_0000.sub, null);
//        if (wjProtocol == null)
//            return;
//
//        if (nettyManager != null) {
//            nettyManager.senMessage(wjProtocol);
//            this.sendMsgToActivity(null, TOAST, "发送搜索网关命令成功");
//        } else {
//            queue.offer(wjProtocol);
//        }
//    }

    //手机→网关 手机选择设备厂商后传递给网关信息
    private static void deviceComp(WebViewWebSocketFuctionEnum webViewWebSocketFuctionEnum, NetDevCompFileTask netDevCompTask) {

        WjProtocol wjProtocol = Sen_factory.getInstance(Sen_1200_0100.main, Sen_1200_0100.sub, netDevCompTask);
        if (wjProtocol == null)
            return;

        if (channel != null && channel.isActive()) {
            channel.writeAndFlush(wjProtocol);
            GTcpClient.nioWebSocketHandler.backMsg(webViewWebSocketFuctionEnum, "选择设备厂商后传递给网关信息成功");
        } else {
            queue.offer(wjProtocol);
        }
    }

    //手机→网关 手机注册后传递给网关信息
    private static void toNetInfo(WebViewWebSocketFuctionEnum webViewWebSocketFuctionEnum, NetInfoTask netInfoTask) {

        WjProtocol wjProtocol = Sen_factory.getInstance(Sen_0000_0200.main, Sen_0000_0200.sub, netInfoTask);
        if (wjProtocol == null)
            return;

        if (channel != null && channel.isActive()) {
            channel.writeAndFlush(wjProtocol);
            GTcpClient.nioWebSocketHandler.backMsg(webViewWebSocketFuctionEnum, "注册后传递给网关信息成功");
        } else {
            queue.offer(wjProtocol);
        }
    }

    //手机→网关 认证完成，网关获取设备列表
    private static void authOver(WebViewWebSocketFuctionEnum webViewWebSocketFuctionEnum, NetDevCompFileTask netDevCompFileTask) {

        WjProtocol wjProtocol = Sen_factory.getInstance(Sen_1200_0200.main, Sen_1200_0200.sub, netDevCompFileTask);
        if (wjProtocol == null)
            return;

        if (channel != null && channel.isActive()) {
            channel.writeAndFlush(wjProtocol);
            GTcpClient.nioWebSocketHandler.backMsg(webViewWebSocketFuctionEnum, "认证完成，网关获取设备列表成功");
        } else {
            queue.offer(wjProtocol);
        }
    }

    //手机→网关 手机配置房间后传递给网关信息
    private static void saveDevice(WebViewWebSocketFuctionEnum webViewWebSocketFuctionEnum, NetDevCompTask netDevCompTask) {

        WjProtocol wjProtocol = Sen_factory.getInstance(Sen_1200_0300.main, Sen_1200_0300.sub, netDevCompTask);
        if (wjProtocol == null)
            return;

        if (channel != null && channel.isActive()) {
            channel.writeAndFlush(wjProtocol);
            GTcpClient.nioWebSocketHandler.backMsg(webViewWebSocketFuctionEnum, "手机配置房间后传递给网关信息成功");
        } else {
            queue.offer(wjProtocol);
        }
    }

    //手机→网关 手机配置网关
    private static void toConfigNet(WebViewWebSocketFuctionEnum webViewWebSocketFuctionEnum, NetConfigTask netDevCompTask) {
        WjProtocol wjProtocol = Sen_factory.getInstance(Sen_1200_0400.main, Sen_1200_0400.sub, netDevCompTask);
        if (wjProtocol == null)
            return;

        if (channel != null && channel.isActive()) {
            channel.writeAndFlush(wjProtocol);
            GTcpClient.nioWebSocketHandler.backMsg(webViewWebSocketFuctionEnum, "手机配置网关任务成功");
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

    public static int startTcpClient() {
        if (ip != null && port != null) {

            return init();
        } else {
            return 1;
        }
    }

    public static void atTask(NetManageEnum netManageEnum, String tx, JSONObject objParam) {
        log.debug("收到"+netManageEnum +"atTask:" + tx);
        if (objParam != null)
            log.debug("收到"+netManageEnum+"返回的at业务:" + tx + ":" + objParam.toJSONString());

        GTcpClient.nioWebSocketHandler.backMsg(WebViewWebSocketFuctionEnum.netBack, tx);
    }

    public static void nettyNetSearchBack() {
        log.debug("nettyNetSearchBack");
    }

    public static void nettyNetFileDownOver(String tx, JSONObject objParam) {
        log.debug("nettyNetFileDownOver:" + objParam.toJSONString());
//        sendMsgToActivity(null, "2", ip);
        GTcpClient.nioWebSocketHandler.backMsg(WebViewWebSocketFuctionEnum.nettyNetFileDownOver, ip);
    }

    public static void nettyNetGetDevListOver(String tx, JSONObject objParam) {
        log.debug("nettyNetGetDevListOver:" + objParam);

        if (objParam == null) {
            return;
        }

        log.debug("nettyNetGetDevListOver:" + objParam.toJSONString());
        NetDevCompFileTask netDevCompFileTask = JSONObject.toJavaObject(objParam, NetDevCompFileTask.class);
//        sendMsgToActivity(netDevCompFileTask, "1", ip);
        GTcpClient.nioWebSocketHandler.backMsg(WebViewWebSocketFuctionEnum.nettyNetGetDevListOver, ip);
    }
}
