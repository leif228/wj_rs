package com.wujie.pcclient.app.business.app.service.system;

import com.wujie.common.base.ApiResult;

public interface UserService {

    ApiResult tcpClientConnect(String ip, String port, String fzwno);

    ApiResult getTcpClientConnectInfo();

    ApiResult sendAtTask(String at);


    ApiResult serverInfo();

}
