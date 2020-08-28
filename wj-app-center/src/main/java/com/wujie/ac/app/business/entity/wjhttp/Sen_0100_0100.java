package com.wujie.ac.app.business.entity.wjhttp;


public class Sen_0100_0100 implements Sen_i {
    public static final byte[] main = new byte[]{0x01, 0x00};
    public static final byte[] sub = new byte[]{0x01, 0x00};

    @Override
    public WjProtocol generateWj(BaseTask baseTask) {
        WjProtocol wjProtocol = new WjProtocol();
        wjProtocol.setPlat(new byte[]{0x20, 0x00});
        wjProtocol.setMaincmd(main);
        wjProtocol.setSubcmd(sub);
        wjProtocol.setFormat(WjProtocol.FORMAT_JS);
        wjProtocol.setBack(new byte[]{0x01, 0x00});

        int len = 0;
        byte[] objectBytes = null;
//        if(baseTask != null){
//            NetInfoTask netInfoTask = (NetInfoTask) baseTask;
//
//            String jsonStr = JSONObject.toJSONString(netInfoTask);
//            Log.v(WjProtocol.TAG, jsonStr);
//            objectBytes = jsonStr.getBytes();
//
//            len = objectBytes.length;
//        }

        len = WjProtocol.MIN_DATA_LEN + len;
        wjProtocol.setLen(wjProtocol.short2byte((short) len));
        wjProtocol.setUserdata(objectBytes);

        return wjProtocol;
    }
}
