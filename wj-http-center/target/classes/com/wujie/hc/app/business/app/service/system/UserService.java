package com.wujie.hc.app.business.app.service.system;

import com.wujie.common.base.ApiResult;

public interface UserService {

    ApiResult userLogin(String username, String password);

    ApiResult deviceRegist(Long userId, String deviceSelected, String deviceName, String ip, String port, Long nodeId) ;

    ApiResult userRegist(String username, String password, String idcard, String phone, String userSelected);

    ApiResult getChildNodes(Long nodeId);

    ApiResult getTreeData(Long nodeId);

    ApiResult tcpClientConnect(String ip, String port, String fzwno);
}
