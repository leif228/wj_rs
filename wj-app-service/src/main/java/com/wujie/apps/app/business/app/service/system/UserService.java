package com.wujie.apps.app.business.app.service.system;

import com.wujie.common.base.ApiResult;

public interface UserService {

    ApiResult tcpClientConnect(String ip, String port, String fzwno);

    ApiResult getTcpClientConnectInfo();

    ApiResult sendAtTask(String at);

    ApiResult addUser(String oid);

    ApiResult users();

    ApiResult delUser(Long id);

    ApiResult serverInfo();

    ApiResult addEvent(String event);

    ApiResult events();

    ApiResult delEvent(Long id);
}
