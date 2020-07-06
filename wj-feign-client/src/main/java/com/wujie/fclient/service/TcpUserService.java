
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
        value = "wj-tc-center",
        fallback = TcpUserService.TcpUserServiceFallBack.class
)
public interface TcpUserService {

    @PostMapping("/getTreeData")
    public ApiResult getTreeData(@RequestParam(value = "nodeId") Long nodeId);

    @PostMapping("/tcpClientConnect")
    ApiResult tcpClientConnect(@RequestParam(value = "ip") String ip,
                               @RequestParam(value = "port") String port,
                               @RequestParam(value = "fzwno") String fzwno);


    @Component
    class TcpUserServiceFallBack implements TcpUserService {

        @Override
        public ApiResult getTreeData(Long nodeId) {
            return ApiResult.error(ErrorEnum.ERR_TSERVICE_NOT);
        }

        @Override
        public ApiResult tcpClientConnect(String ip, String port, String fzwno) {
            return ApiResult.error(ErrorEnum.ERR_TSERVICE_NOT);
        }


    }


}