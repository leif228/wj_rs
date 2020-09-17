
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

    @PostMapping("/tcpClientConnect")
    public ApiResult tcpClientConnect(@RequestParam(value = "ip") String ip,
                                      @RequestParam(value = "port") String port,
                                      @RequestParam(value = "fzwno") String fzwno,
                                      @RequestParam(value = "deviceName") String deviceName,
                                      @RequestParam(value = "selfIp") String selfIp,
                                      @RequestParam(value = "selfPort") String selfPort,
                                      @RequestParam(value = "deviceSelected") String deviceSelected

    );

    @PostMapping("/getTcpClientConnectInfo")
    ApiResult getTcpClientConnectInfo();

    @PostMapping("/sendAtTask")
    public ApiResult sendAtTask(@RequestParam(value = "oid") String oid,
                                @RequestParam(value = "at") String at);

    @Component
    class TcpUserServiceFallBack implements TcpUserService {


        @Override
        public ApiResult tcpClientConnect(String ip, String port, String fzwno, String deviceName, String selfIp, String selfPort, String deviceSelected) {
            return ApiResult.error(ErrorEnum.ERR_TSERVICE_NOT);
        }

        @Override
        public ApiResult getTcpClientConnectInfo() {
            return ApiResult.error(ErrorEnum.ERR_TSERVICE_NOT);
        }

        @Override
        public ApiResult sendAtTask(String oid, String at) {
            return ApiResult.error(ErrorEnum.ERR_TSERVICE_NOT);
        }


    }


}