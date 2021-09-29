package com.wujie.hc.app.business.app.service.system;

import com.wujie.common.base.ApiResult;
import com.wujie.hc.app.business.vo.UserDetailsVo;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {

    ApiResult userLogin(String username, String password);

    ApiResult deviceRegist(Long userId, String deviceSelected, String deviceName, String ip, String port, Long nodeId) ;

    ApiResult userRegist(String username, String password, String idcard, String phone, String userSelected, Integer pSort, Integer cSort, Integer aSort, Integer sSort);

    ApiResult getChildNodes(Long nodeId);

    ApiResult preDeviceRegist(Long userId, String deviceSelected, Long nodeId);

    ApiResult secDeviceRegist(Long userId, String deviceSelected, String deviceName, String ip, String port, Long nodeId, String fzwno);

    ApiResult uploadHead(MultipartFile file, String idcard);
}
