package com.wujie.ac.app.business.app.service.system.impl;

import com.wujie.ac.app.business.util.NumConvertUtil;
import com.wujie.common.base.ApiResult;

public class Test2 {
    public static void main(String[] args) {
        doAtTask("AT@Nchn0L0a002020091100011000000000E00200010500A001000100010027chn0L0a00202009110001100000000011110102#*");
    }
    public static ApiResult doAtTask(String tx) {
        try {
            if (tx == null || "".equals(tx))
                return ApiResult.error("传输的数据错误！数据为：" + tx);


            int headpoint = tx.indexOf(AtServiceImpl.HEARD);//找出帧头
            int endpoint = tx.indexOf(AtServiceImpl.END);//找出帧尾

            if (headpoint != -1 && endpoint != -1) {
                //AT@Nchn0L0a002020091100011000000000E00200010500A001000100010027chn0L0a00202009110001100000000011110102#*
                int index = 0;
                String strhead = tx.substring(index, AtServiceImpl.HEARD.length());//取出帧头
                index += strhead.length();
                String flag = tx.substring(index, index + 1);
                index += flag.length();
                String oid = tx.substring(index, index + 39);
                index += oid.length();
                String pri = tx.substring(index, index + 4);
                index += pri.length();
                String buss = tx.substring(index, index + 4);
                index += buss.length();
                String port = tx.substring(index, index + 4);
                index += port.length();
                String cmd = tx.substring(index, index + 4);
                index += cmd.length();
                String paramLen = tx.substring(index, index + 4);
                int len = NumConvertUtil.HexStringToInt(paramLen);
                index += paramLen.length();
                String param = tx.substring(index, index + len);

                index += param.length();
                String strend = tx.substring(index, index + AtServiceImpl.END.length());//取出帧尾
            } else {
                return ApiResult.error("传输的数据格式错误！数据为：" + tx);
            }

            return null;
        } catch (Exception e) {
            return ApiResult.error("AT数据解析出错了！");
        }
    }
}
