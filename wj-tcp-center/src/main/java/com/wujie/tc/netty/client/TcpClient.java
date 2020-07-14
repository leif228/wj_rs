package com.wujie.tc.netty.client;

import com.alibaba.fastjson.JSONObject;
import com.wujie.tc.app.business.util.WechatConstant;
import com.wujie.tc.netty.client.business.InBusinessHandler;
import com.wujie.tc.netty.client.business.WjEchoHandler;
import com.wujie.tc.netty.client.decoder.DecoderHandler;
import com.wujie.tc.netty.client.decoder.WjDecoderHandler;
import com.wujie.tc.netty.client.encoder.WjEncoderHandler;
import com.wujie.tc.netty.pojo.LoginTask;
import com.wujie.tc.netty.protocol.WjProtocol;
import com.wujie.tc.netty.utils.FileUtils;
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
    private static int port;
    private static NioEventLoopGroup worker = new NioEventLoopGroup();

    private static Channel channel;

    private static Bootstrap bootstrap;
    private static WechatConstant wechatConstant;

    public void init() throws InterruptedException {
        try {
            log.info("启动tcp客户端:去连接:" + ip + ":" + port);
            bootstrap = new Bootstrap();
            bootstrap.group(worker);
            bootstrap.channel(NioSocketChannel.class);
            bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
            bootstrap.handler(new ChannelInitializer() {
                @Override
                protected void initChannel(Channel ch) throws Exception {
//                ch.pipeline().addLast("logging",new LoggingHandler("DEBUG"));
                    //五秒没有收到消息 将IdleStateHandler 添加到 ChannelPipeline 中
                    ch.pipeline().addLast(new IdleStateHandler(0, 0, 30));
                    ch.pipeline().addLast(new WjEncoderHandler());
//                    ch.pipeline().addLast(new WjEchoHandler());
                    ch.pipeline().addLast(new WjDecoderHandler());//解码器，接收消息时候用
//                    ch.pipeline().addLast(new InBusinessHandler());//业务处理类，最终的消息会在这个handler中进行业务处理
                }
            });
//            bootstrap.remoteAddress(ip, port);
//            ChannelFuture future = bootstrap.connect().sync();//使用了Future来启动线程
//
//            future.channel().closeFuture().sync();//以异步的方式关闭端口
            doConnect();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public TcpClient(String ip, int port) {
        this.ip = ip;
        this.port = port;
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
                    sendData();
                    log.info("连接成功:" + ip + ":" + port);
                    System.out.println("连接成功");
                } else {
                    System.out.println("每隔5s重连....");
                    channelFuture.channel().eventLoop().schedule(new Runnable() {

                        @SneakyThrows
                        @Override
                        public void run() {
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
            channel.closeFuture().sync();//以异步的方式关闭端口
            worker.shutdownGracefully().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 向服务端发送消息
     */
    private static void sendData() {
        if (channel != null && channel.isActive()) {
            WjProtocol wjProtocol = new WjProtocol();
            wjProtocol.setPlat(Short.parseShort("20"));
            wjProtocol.setMaincmd(Short.parseShort("0"));
            wjProtocol.setSubcmd(Short.parseShort("1"));
            wjProtocol.setFormat("JS");
            wjProtocol.setBack(Short.parseShort("0"));

            LoginTask loginTask = new LoginTask();
            Properties properties = FileUtils.readFile(wechatConstant.getTcpClientConfigPath());
            if (properties.getProperty("fzwno") != null)
                loginTask.setOid(properties.getProperty("fzwno"));
            else
                loginTask.setOid("0000000000");

//        byte [] objectBytes= ByteUtils.InstanceObjectMapper().writeValueAsBytes(loginTask);

            String jsonStr = JSONObject.toJSONString(loginTask);
            log.debug(jsonStr);
            byte[] objectBytes = jsonStr.getBytes();

            int len = 21 + objectBytes.length;
            wjProtocol.setLen((short) len);
            wjProtocol.setUserdata(objectBytes);

            channel.writeAndFlush(wjProtocol);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        try {
            Properties properties = FileUtils.readFile("E:\\config.properties");

            if (properties.getProperty("ip") != null&&properties.getProperty("port")!=null)
                new TcpClient(properties.getProperty("ip"), Integer.valueOf(properties.getProperty("port"))).init();
            else
                new TcpClient("127.0.0.1", 8777).init();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean startTcpClient(WechatConstant wechatConstant) {
        TcpClient.wechatConstant = wechatConstant;
        try {
            Properties properties = FileUtils.readFile(wechatConstant.getTcpClientConfigPath());
            if (properties.getProperty("ip") != null&&properties.getProperty("port")!=null)
                new TcpClient(properties.getProperty("ip"), Integer.valueOf(properties.getProperty("port"))).init();
            else
                return false;

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
