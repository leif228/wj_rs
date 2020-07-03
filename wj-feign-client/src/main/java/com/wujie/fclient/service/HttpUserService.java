
package com.wujie.fclient.service;

import com.wujie.common.base.ApiResult;
import com.wujie.common.enums.ErrorEnum;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 用户服务接口
 *
 * @author leif
 * @version 1.0.0
 * @since 2020/7/2
 */
@FeignClient(
        value = "wj-hc-center",
        fallback = HttpUserService.HttpUserServiceFallBack.class

)
public interface HttpUserService {

    @PostMapping("/deviceRegist")
    public ApiResult deviceRegist(@RequestParam(value = "userId") Long userId,
                                  @RequestParam(value = "deviceSelected") String deviceSelected,
                                  @RequestParam(value = "deviceName") String deviceName,
                                  @RequestParam(value = "ip") String ip,
                                  @RequestParam(value = "port") String port,
                                  @RequestParam(value = "nodeId") Long nodeId);

    @PostMapping("/getChildNodes")
    public ApiResult getChildNodes(@RequestParam(value = "nodeId") Long nodeId);


    @PostMapping("/getTreeData")
    public ApiResult getTreeData(@RequestParam(value = "nodeId") Long nodeId);

    @PostMapping("/userRegist")
    public ApiResult userRegist(@RequestParam(value = "username") String username,
                                @RequestParam(value = "password") String password,
                                @RequestParam(value = "idcard") String idcard,
                                @RequestParam(value = "phone") String phone,
                                @RequestParam(value = "userSelected") String userSelected);

    @PostMapping("/userLogin")
    public ApiResult userLogin(@RequestParam(value = "username") String username,
                               @RequestParam(value = "password") String password
    );

    @Component
    class HttpUserServiceFallBack implements HttpUserService {

        @Override
        public ApiResult deviceRegist(Long userId, String deviceSelected, String deviceName, String ip, String port, Long nodeId) {
            return ApiResult.error(ErrorEnum.ERR_HSERVICE_NOT);
        }

        @Override
        public ApiResult getChildNodes(Long nodeId) {
            return ApiResult.error(ErrorEnum.ERR_HSERVICE_NOT);
        }

        @Override
        public ApiResult getTreeData(Long nodeId) {
            return ApiResult.error(ErrorEnum.ERR_HSERVICE_NOT);
        }

        @Override
        public ApiResult userRegist(String username, String password, String idcard, String phone, String userSelected) {
            return ApiResult.error(ErrorEnum.ERR_HSERVICE_NOT);
        }

        @Override
        public ApiResult userLogin(String username, String password) {
            return ApiResult.error(ErrorEnum.ERR_HSERVICE_NOT);
        }

    }


}