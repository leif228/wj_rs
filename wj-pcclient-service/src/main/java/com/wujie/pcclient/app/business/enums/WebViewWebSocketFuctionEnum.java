package com.wujie.pcclient.app.business.enums;

/**
 * @ProjectName: 20190409_jingxiangjia_admin
 * @Package: com.duoqio.boot.business.enums
 * @ClassName: PayType
 * @Description: 交互类型
 * @Date: 2020/6/2 10:33
 * @Author: dongcan
 * @copyright: 重庆物界
 * @website: www.wujie.com
 */
public enum WebViewWebSocketFuctionEnum {
    toHeart,
    toTcp,
    toNetInfo,
    deviceComp,
    saveDevice,
    authOver,
    //	  String getEventNo();
    toSure,
    sendChatMsg,
    toGenEvent,
    getMobileFzwno,
    toAt,
    toAtNet,
    toLightOn,
    toLightOff,
    toSearchNet,
    toNetTcp,
    toConfigNet,

    nettyNetFileDownOver,//"http://" + ip + ":8080/tcube_app/APP_code/APP_choose_device.php"
    nettyNetGetDevListOver,//"http://" + ip + ":8080/tcube_app/APP_code/APP_choose_device.php"
    backNets;
}