package com.wujie.tc.app.business.app.controller;

import com.wujie.common.base.ApiResult;
import com.wujie.tc.app.business.app.service.system.UserService;
import com.wujie.tc.app.business.entity.Node;
import com.wujie.tc.app.business.enums.ErrorEnum;
import com.wujie.tc.app.business.vo.NodeVo;
import com.wujie.tc.app.business.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName: UserController
 * @Description: 用户控制层
 * @Date: 2020/4/21 14:27
 */
@RestController
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/tcpClientConnect")
    public ApiResult tcpClientConnect(@RequestParam(value = "ip") String ip,
                                      @RequestParam(value = "port") String port,
                                      @RequestParam(value = "fzwno") String fzwno,
                                      @RequestParam(value = "deviceName") String deviceName,
                                      @RequestParam(value = "selfIp") String selfIp,
                                      @RequestParam(value = "selfPort") String selfPort,
                                      @RequestParam(value = "deviceSelected") String deviceSelected

    ) {
        return userService.tcpClientConnect(ip, port, fzwno, deviceName, selfIp, selfPort, deviceSelected);
    }

    @PostMapping("/getTcpClientConnectInfo")
    public ApiResult getTcpClientConnectInfo() {
        return userService.getTcpClientConnectInfo();
    }

    @PostMapping("/sendAtTask")
    public ApiResult sendAtTask(@RequestParam(value = "oid") String oid,
                                @RequestParam(value = "at") String at) {
        return userService.sendAtTask(oid,at);
    }

}
