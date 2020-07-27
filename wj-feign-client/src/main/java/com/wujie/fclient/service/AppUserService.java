
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
        value = "wj-ac-center",
        fallback = AppUserService.AppUserServiceFallBack.class
)
public interface AppUserService {


    @PostMapping("/getTreeData")
    public ApiResult getTreeData(@RequestParam(value = "nodeId") Long nodeId);

    @PostMapping("/secDeviceRegist")
    public ApiResult secDeviceRegist(@RequestParam(value = "userId") Long userId,
                                     @RequestParam(value = "deviceSelected") String deviceSelected,
                                     @RequestParam(value = "deviceName") String deviceName,
                                     @RequestParam(value = "ip") String ip,
                                     @RequestParam(value = "port") String port,
                                     @RequestParam(value = "nodeId") Long nodeId,
                                     @RequestParam(value = "fzwno") String fzwno);


    @PostMapping("/preDeviceRegist")
    public ApiResult preDeviceRegist(@RequestParam(value = "userId") Long userId,
                                     @RequestParam(value = "deviceSelected") String deviceSelected,
                                     @RequestParam(value = "nodeId") Long nodeId,
                                     @RequestParam(value = "pSort") Integer pSort,
                                     @RequestParam(value = "cSort") Integer cSort,
                                     @RequestParam(value = "aSort") Integer aSort,
                                     @RequestParam(value = "sSort") Integer sSort);

    @PostMapping("/getChildNodes")
    public ApiResult getChildNodes(@RequestParam(value = "nodeId") Long nodeId);

    @PostMapping("/addrInit")
    public ApiResult addrInit();

    @PostMapping("/cityByP")
    public ApiResult cityByP(@RequestParam(value = "id") Integer id);

    @PostMapping("/areaByC")
    public ApiResult areaByC(@RequestParam(value = "id") Integer id);

    @PostMapping("/streetByA")
    public ApiResult streetByA(@RequestParam(value = "id") Integer id);

    @PostMapping("/getFullFzwno")
    public ApiResult getFullFzwno(@RequestParam(value = "fzwno") String fzwno,
                                  @RequestParam(value = "deviceType") Integer deviceType);

    @PostMapping("/getAllDevType")
    public ApiResult getAllDevType(@RequestParam(value = "userId") Long userId);

    @Component
    class AppUserServiceFallBack implements com.wujie.fclient.service.AppUserService {

        @Override
        public ApiResult getTreeData(Long nodeId) {
            return ApiResult.error(ErrorEnum.ERR_ASERVICE_NOT);
        }

        @Override
        public ApiResult secDeviceRegist(Long userId, String deviceSelected, String deviceName, String ip, String port, Long nodeId, String fzwno) {
            return ApiResult.error(ErrorEnum.ERR_ASERVICE_NOT);
        }

        @Override
        public ApiResult preDeviceRegist(Long userId, String deviceSelected, Long nodeId, Integer pSort, Integer cSort, Integer aSort, Integer sSort) {
            return ApiResult.error(ErrorEnum.ERR_ASERVICE_NOT);
        }

        @Override
        public ApiResult getChildNodes(Long nodeId) {
            return ApiResult.error(ErrorEnum.ERR_ASERVICE_NOT);
        }

        @Override
        public ApiResult addrInit() {
            return ApiResult.error(ErrorEnum.ERR_ASERVICE_NOT);
        }

        @Override
        public ApiResult cityByP(Integer id) {
            return ApiResult.error(ErrorEnum.ERR_ASERVICE_NOT);
        }

        @Override
        public ApiResult areaByC(Integer id) {
            return ApiResult.error(ErrorEnum.ERR_ASERVICE_NOT);
        }

        @Override
        public ApiResult streetByA(Integer id) {
            return ApiResult.error(ErrorEnum.ERR_ASERVICE_NOT);
        }

        @Override
        public ApiResult getFullFzwno(String fzwno, Integer deviceType) {
            return ApiResult.error(ErrorEnum.ERR_ASERVICE_NOT);
        }

        @Override
        public ApiResult getAllDevType(Long userId) {
            return ApiResult.error(ErrorEnum.ERR_ASERVICE_NOT);
        }

    }


}