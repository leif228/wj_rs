package com.wujie.ac.app.business.app.service.system.impl;

import com.alibaba.fastjson.JSONObject;
import com.wujie.ac.app.business.entity.LoginServer;
import com.wujie.ac.app.business.entity.wjhttp.*;
import com.wujie.ac.app.business.util.date.DateUtil;

import javax.servlet.http.HttpServletResponse;
import java.nio.ByteBuffer;
import java.util.Arrays;

public class Test {
    public static void main(String[] args) {
        WjProtocol wjProtocol = new WjProtocol();
        wjProtocol.setPlat(new byte[]{0x50, 0x00});
        wjProtocol.setMaincmd(new byte[]{0x01, 0x00});
        wjProtocol.setSubcmd(new byte[]{0x01, 0x00});
        wjProtocol.setFormat("JS");
        wjProtocol.setBack(new byte[]{0x00, 0x00});

        int len = 0;
        byte[] objectBytes = null;
        if(true){
            OwerLogin netInfoTask = new OwerLogin();
            netInfoTask.setOID("55");
            netInfoTask.setOwnerServerOID("chn3849874523485203845920834");
            netInfoTask.setServerIP("192.168.1.33");
            netInfoTask.setServerPort("8777");
            netInfoTask.setServerOID("chn84084488348498584985948");

            String jsonStr = JSONObject.toJSONString(netInfoTask);
            objectBytes = jsonStr.getBytes();

            len = objectBytes.length;
        }

        len = WjProtocol.MIN_DATA_LEN + len;
        wjProtocol.setLen(wjProtocol.short2byte((short) len));
        wjProtocol.setUserdata(objectBytes);
        wjProtocol.setCheckSum(wjProtocol.getCheckSum(wjProtocol));

        byte[] bytes = wjProtocol.getAllByteArray();
        System.out.println(Arrays.toString(bytes));
        int bs = bytes.length;

        wjhttp(bytes,null);
    }
    public static void snd(){
        WjProtocol wjProtocol = new WjProtocol();
        wjProtocol.setPlat(new byte[]{0x20,0x00});
        wjProtocol.setMaincmd(new byte[]{0x00,0x00});
        wjProtocol.setSubcmd(new byte[]{0x00,0x00});
        wjProtocol.setFormat("TX");
        wjProtocol.setBack(new byte[]{0x00,0x00});

        int len = WjProtocol.MIN_DATA_LEN + 0;
        wjProtocol.setLen(wjProtocol.short2byte((short) len));
    }
    public static void wjhttp(byte[] data, HttpServletResponse response) {
        //log.debug("=====收到wjhttp的长度：" + data.length);
        //log.debug("=====收到wjhttp的data：" + Arrays.toString(data));

        ByteBuffer buf = ByteBuffer.wrap(data);
        try {
            WjProtocol wjProtocol = decode(buf);
            doTask(wjProtocol,response);
        } catch (Exception e) {
            //log.debug( "wjhttp===error:" + e.getMessage());
            doError(response);
        }

    }

    private static void doError(HttpServletResponse response) {

    }
    private static void doTask(WjProtocol wjProtocol, HttpServletResponse response) throws Exception{
        com.alibaba.fastjson.JSONObject objParam = null;
        String tx = null;
        if (WjProtocol.FORMAT_TX.equals(wjProtocol.getFormat())) {
            if (wjProtocol.getUserdata() != null) {

                String dataStr = new String(wjProtocol.getUserdata());
                tx = dataStr;
            }
        } else if (WjProtocol.FORMAT_JS.equals(wjProtocol.getFormat())) {
            if (wjProtocol.getUserdata() != null) {

                String jsonStr = new String(wjProtocol.getUserdata());
                objParam = com.alibaba.fastjson.JSONObject.parseObject(jsonStr);
            }
        } else if (WjProtocol.FORMAT_AT.equals(wjProtocol.getFormat())) {
            if (wjProtocol.getUserdata() != null) {

                tx = new String(wjProtocol.getUserdata());
            }
        }
        //通用处理
        try {
            String classPath = "com.wujie.ac.app.business.entity.wjhttp";
            String className = "Rec_" + wjProtocol.IntToHexStringLimit2(wjProtocol.getMaincmd()[0]) + wjProtocol.IntToHexStringLimit2(wjProtocol.getMaincmd()[1]) + "_"
                    + wjProtocol.IntToHexStringLimit2(wjProtocol.getSubcmd()[0]) + wjProtocol.IntToHexStringLimit2(wjProtocol.getSubcmd()[1]);
            className = classPath + "." + className;
            Class genClass = Class.forName(className);
            Rec_task_i rec_task_i = (Rec_task_i) genClass.newInstance();
            rec_task_i.doTask(response, tx, objParam);
            if(rec_task_i instanceof Rec_0100_0100){
                OwerLogin owerLogin = (OwerLogin) rec_task_i.backUserData();

                LoginServer loginServer = new LoginServer();
                loginServer.setOid(owerLogin.getOID());
                loginServer.setServerIp(owerLogin.getServerIP());
                loginServer.setServerPort(owerLogin.getServerPort());
                loginServer.setServerOid(owerLogin.getServerOID());
                loginServer.setOwerServerOid(owerLogin.getOwnerServerOID());
                loginServer.setCreatTime(DateUtil.getDate());

//                loginServerMapper.insertSelective(loginServer);
            }
        } catch (Exception e) {
            //log.debug( "TaskHandler.doProtocol_报错了:" + e.getMessage());
            throw new Exception( "TaskHandler.doProtocol_报错了:" + e.getMessage());
        }
    }

    private static WjProtocol decode(ByteBuffer in) throws Exception {
        System.out.println(in.capacity());
        //log.debug("=====收到wjhttp转buf长度：" + in.capacity());
        if (in.capacity() >= WjProtocol.MIN_DATA_LEN) {
            //log.debug("开始解码数据……");
            WjProtocol wjProtocol = new WjProtocol();
            //标记读操作的指针
//            in.markReaderIndex();
            byte[] headerbyte = new byte[6];//##6

            in.get(headerbyte);
            //log.debug("数据解码的headerbyte===headerbyte[6]：" + Arrays.toString(headerbyte));
            String headerStr = new String(headerbyte);
            wjProtocol.setHeader(headerStr);

            if (WjProtocol.PROTOCOL_HEADER.equals(headerStr)) {
                //log.debug("数据开头格式正确");
                //读取字节数据的长度
//                short lenShort = in.readShort();//##2
                byte[] lenShortbyte = new byte[2];//##2
                in.get(lenShortbyte);
                //log.debug("数据解码的lenShortbyte===byte[2]：" + Arrays.toString(lenShortbyte));
                wjProtocol.setLen(lenShortbyte);

                int len = wjProtocol.byte2shortSmall(lenShortbyte);
                //log.debug("数据解码的长度：" + len);
                int subHeaderLen = len - 8;
                //数据可读长度必须要大于len，因为结尾还有一字节的解释标志位
                int rr = in.remaining();
                if (subHeaderLen < in.remaining()) {
                    //log.debug(String.format("数据长度不够，数据协议len长度为：%1$d,数据包实际可读内容为：%2$d正在等待处理拆包……", len, in.remaining()));
                    throw new Exception(String.format("数据长度不够，数据协议len长度为：%1$d,数据包实际可读内容为：%2$d正在等待处理拆包……", len, in.remaining()));
//                    in.rewind();
                    /*
                     **结束解码，这种情况说明数据没有到齐，在父类ByteToMessageDecoder的callDecode中会对out和in进行判断
                     * 如果in里面还有可读内容即in.isReadable为true,cumulation中的内容会进行保留，，直到下一次数据到来，将两帧的数据合并起来，再解码。
                     * 以此解决拆包问题
                     */
//                    return;
                }
                byte verChar = in.get();//##1
                //log.debug("数据解码的verChar===byte[1]：" + verChar);
                wjProtocol.setVer(verChar);
                byte encryptChar = in.get();//##1
                //log.debug("数据解码的encryptChar===byte[1]：" + encryptChar);
                wjProtocol.setEncrypt(encryptChar);

//                short platShort = in.readShort();//##2
                byte[] platShortbyte = new byte[2];//##2
                in.get(platShortbyte);
                //log.debug("数据解码的platShortbyte===byte[2]：" + Arrays.toString(platShortbyte));
                wjProtocol.setPlat(platShortbyte);

//                short maincmdShort = in.readShort();//##2
                byte[] maincmdShortbyte = new byte[2];//##2
                in.get(maincmdShortbyte);
                //log.debug("数据解码的maincmdShortbyte===byte[2]：" + Arrays.toString(maincmdShortbyte));
                wjProtocol.setMaincmd(maincmdShortbyte);

//                short subcmdShort = in.readShort();//##2
                byte[] subcmdShortbyte = new byte[2];//##2
                in.get(subcmdShortbyte);
                //log.debug("数据解码的subcmdShortbyte===byte[2]：" + Arrays.toString(subcmdShortbyte));
                wjProtocol.setSubcmd(subcmdShortbyte);

                byte[] formatbyte = new byte[2];//##2
                in.get(formatbyte);
                //log.debug("数据解码的formatbyte===byte[2]：" + Arrays.toString(formatbyte));
                String format = new String(formatbyte);
                wjProtocol.setFormat(format);

//                short backShort = in.readShort();//##2
                byte[] backShortbyte = new byte[2];//##2
                in.get(backShortbyte);
                //log.debug("数据解码的backShortbyte===byte[2]：" + Arrays.toString(backShortbyte));
                wjProtocol.setBack(backShortbyte);

                int dataLen = len - WjProtocol.MIN_DATA_LEN;
                if (dataLen > 0) {
                    byte[] data = new byte[dataLen];
                    in.get(data);//读取核心的数据##n
                    //log.debug("数据解码的data===byte[" + dataLen + "]：" + Arrays.toString(data));
                    wjProtocol.setUserdata(data);
                }

                byte checkSumChar = in.get();//##1
                //log.debug("数据解码的checkSumChar===byte[1]：" + checkSumChar);
                wjProtocol.setCheckSum(checkSumChar);

                boolean check = wjProtocol.checkXOR(wjProtocol.getCheckSumArray(wjProtocol), checkSumChar);
                if (!check) {
                    //log.debug("数据异或校验不对");

                    throw new Exception("数据异或校验不对");
//                    return;
                }

                return wjProtocol;
            } else {
                //log.debug("开头不对，可能不是期待的客服端发送的数，将自动略过这一个字节");

                throw new Exception("开头不对，可能不是期待的客服端发送的数，将自动略过这一个字节");
//                return;
            }
        } else {
            //log.debug("数据长度不符合要求，期待最小长度是：" + WjProtocol.MIN_DATA_LEN + " 字节");

            throw new Exception("数据长度不符合要求，期待最小长度是：" + WjProtocol.MIN_DATA_LEN + " 字节");
//            return;
        }
    }
}
