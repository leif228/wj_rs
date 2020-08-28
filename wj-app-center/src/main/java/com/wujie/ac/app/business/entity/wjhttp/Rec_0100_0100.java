package com.wujie.ac.app.business.entity.wjhttp;


import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

/**
 * 终端→服务 心跳ping
 */
@Slf4j
public class Rec_0100_0100 implements Rec_task_i {
    private BaseTask baseTask;
    @Override
    public void doTask(HttpServletResponse response, String tx, JSONObject objParam) {
        try {

            OwerLogin loginTask = JSONObject.toJavaObject(objParam, OwerLogin.class);
            baseTask = loginTask;
            log.debug("收到的userdata解析后结果："+loginTask.toString());
            sendIdle(response);
        } catch (IOException e) {
            log.debug(e.getMessage());
        }
    }

    @Override
    public BaseTask backUserData() {
        return baseTask;
    }

    private void sendIdle(HttpServletResponse response) throws IOException {
//        WjProtocol wjProtocol = new WjProtocol();
//        wjProtocol.setPlat(new byte[]{0x20, 0x00});
//        wjProtocol.setMaincmd(new byte[]{0x00, 0x00});
//        wjProtocol.setSubcmd(new byte[]{0x00, 0x00});
//        wjProtocol.setFormat("TX");
//        wjProtocol.setBack(new byte[]{0x01, 0x00});
//
//        int len = WjProtocol.MIN_DATA_LEN + 0;
//        wjProtocol.setLen(wjProtocol.short2byte((short) len));

        WjProtocol wjProtocol = Sen_factory.getInstance(Sen_0100_0100.main, Sen_0100_0100.sub, null);
        if (wjProtocol == null)
            return;

        wjProtocol.setCheckSum(wjProtocol.getCheckSum(wjProtocol));

        byte[] bytes = wjProtocol.getAllByteArray();
        log.debug("getAllByteArray.length========"+bytes.length);
        log.debug("getAllByteArray.bytes========"+ Arrays.toString(bytes));
        response.setContentType("application/octet-stream; charset=UTF-8");
        ServletOutputStream servletOutputStream = response.getOutputStream();
        servletOutputStream.write(bytes);
        servletOutputStream.flush();

    }
}
