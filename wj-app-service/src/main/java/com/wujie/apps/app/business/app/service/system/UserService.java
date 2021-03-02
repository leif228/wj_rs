package com.wujie.apps.app.business.app.service.system;

import com.wujie.common.base.ApiResult;

public interface UserService {

    ApiResult tcpClientConnect(String ip, String port, String fzwno,String deviceName,String selfIp,String selfPort,String deviceSelected);

    ApiResult getTcpClientConnectInfo();

    ApiResult sendAtTask(String oid, String at);
}
