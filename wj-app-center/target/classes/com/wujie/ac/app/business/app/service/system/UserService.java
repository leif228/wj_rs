package com.wujie.ac.app.business.app.service.system;

import com.wujie.common.base.ApiResult;

public interface UserService {

    ApiResult getTreeData(Long nodeId);

    ApiResult preDeviceRegist(Long userId, String deviceSelected, Long nodeId, Integer pSort, Integer cSort, Integer aSort, Integer sSort);

    ApiResult secDeviceRegist(Long userId, String deviceSelected, String deviceName, String ip, String port, Long nodeId, String fzwno);

    ApiResult getChildNodes(Long nodeId);

    ApiResult getFullFzwno(String fzwno, Integer deviceType);

    ApiResult getAllDevType(Long userId);

    ApiResult owerLoginNotify(String oid, String serverIp, String serverPort, String serverOid, String owerServerOid);

    ApiResult deviceComp();
}
