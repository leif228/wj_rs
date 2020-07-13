
package com.wujie.fclient.service;

import com.wujie.common.base.ApiResult;
import com.wujie.common.dto.ResultVo;
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
        value = "wj-dc-center",
        fallback = DispatchUserService.DispatchUserServiceFallBack.class
)
public interface DispatchUserService {



    @PostMapping("/getTreeData")
    public ApiResult getTreeData(@RequestParam(value = "nodeId") Long nodeId);


    @Component
    class DispatchUserServiceFallBack implements com.wujie.fclient.service.DispatchUserService {


        @Override
        public ApiResult getTreeData(Long nodeId) {
            return ApiResult.error(ErrorEnum.ERR_DSERVICE_NOT);
        }


    }


}