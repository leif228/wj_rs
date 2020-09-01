package com.wujie.ac.app.business.app.service.system;

import com.wujie.common.base.ApiResult;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletResponse;

public interface UserService {

    ApiResult getTreeData(Long nodeId);

    ApiResult deviceRegistManage(Long userId, String deviceSelected, Integer pSort, Integer cSort, Integer aSort, Integer sSort, String deviceName, String ip, String port);

    ApiResult recodeOwerNodeInfo(String deviceSelected, String deviceName, String ip, String port, String fzwno);

    ApiResult preDeviceRegist(Long userId, String deviceSelected, Long nodeId, Integer pSort, Integer cSort, Integer aSort, Integer sSort);

    ApiResult searchNode(Integer pSort, Integer cSort, Integer aSort, Integer sSort);

    ApiResult deviceRegistElse(String rootIp, String idCard, String deviceSelected, String deviceName, Integer pSort, Integer cSort, Integer aSort, Integer sSort);

    ApiResult secDeviceRegist(Long userId, String deviceSelected, String deviceName, String ip, String port, Long nodeId, String fzwno);

    ApiResult getChildNodes(Long nodeId);

    ApiResult genAndSaveFullFzwno(String fzwno, Integer deviceType, String deviceName);

    ApiResult getAllDevType(Long userId);

    ApiResult owerLoginNotify(String oid, String serverIp, String serverPort, String serverOid, String owerServerOid);

    ApiResult deviceComp();

    ApiResult userRegist(String username, String password, String idcard, String phone, String userSelected, Integer pSort, Integer cSort, Integer aSort, Integer sSort);

    ApiResult userRegistOwer(String username, String password, String idcard, String phone, String userSelected, Integer pSort, Integer cSort, Integer aSort, Integer sSort);

    ResponseEntity wjhttp(byte[] data, HttpServletResponse response);
}
