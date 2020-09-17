package com.wujie.tc.netty.server.send;


import com.wujie.tc.netty.pojo.AtTask;
import com.wujie.tc.netty.pojo.BaseTask;
import com.wujie.tc.netty.pojo.Sen_i;
import com.wujie.tc.netty.protocol.WjProtocol;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Sen_1000_0000 implements Sen_i {
    public static final byte[] main = new byte[]{0x10, 0x00};
    public static final byte[] sub = new byte[]{0x00, 0x00};

    @Override
    public WjProtocol generateWj(BaseTask baseTask) {
        WjProtocol wjProtocol = new WjProtocol();
        wjProtocol.setPlat(new byte[]{0x20, 0x00});
        wjProtocol.setMaincmd(main);
        wjProtocol.setSubcmd(sub);
        wjProtocol.setFormat(WjProtocol.FORMAT_AT);
        wjProtocol.setBack(new byte[]{0x00, 0x00});

        int len = 0;
        byte[] objectBytes = null;
        if(baseTask != null){
            AtTask atTask = (AtTask) baseTask;

            log.debug("at=" + atTask.getAt());
            objectBytes = atTask.getAt().getBytes();

            len = objectBytes.length;
        }

        len = WjProtocol.MIN_DATA_LEN + len;
        wjProtocol.setLen(wjProtocol.short2byte((short) len));
        wjProtocol.setUserdata(objectBytes);

        return wjProtocol;
    }
}
