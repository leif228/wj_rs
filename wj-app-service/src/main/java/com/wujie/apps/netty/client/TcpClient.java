package com.wujie.apps.netty.client;

import com.wujie.apps.app.business.util.WechatConstant;
import com.wujie.apps.netty.client.decoder.TaskHandler;
import com.wujie.apps.netty.client.decoder.WjDecoderHandler;
import com.wujie.apps.netty.client.encoder.WjEncoderHandler;
import com.wujie.apps.netty.client.send.Sen_0000_0100;
import com.wujie.apps.netty.client.send.Sen_1000_0000;
import com.wujie.apps.netty.client.send.Sen_factory;
import com.wujie.apps.netty.pojo.AtTask;
import com.wujie.apps.netty.pojo.LoginTask;
import com.wujie.apps.netty.protocol.WjProtocol;
import com.wujie.apps.netty.utils.FileUtils;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

@Slf4j
public class TcpClient {

    private static String ip;
    private static Integer port;
    private static NioEventLoopGroup worker;

    private static Channel channel;

    private static Bootstrap bootstrap;
    private static WechatConstant wechatConstant;

    public static int init() {
        try {
            log.info("启动tcp客户端:去连接:" + ip + ":" + port);
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
        com.wujie.apps.netty.client.TcpClient.wechatConstant = wechatConstant;
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
