package com.wujie.apps.app.business.app.controller;

import com.wujie.apps.app.business.app.service.system.UserService;
import com.wujie.common.base.ApiResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
                                      @RequestParam(value = "fzwno") String fzwno

    ) {
        return userService.tcpClientConnect(ip, port, fzwno);
    }

    @PostMapping("/getTcpClientConnectInfo")
    public ApiResult getTcpClientConnectInfo() {
        return userService.getTcpClientConnectInfo();
    }

    @PostMapping("/sendAtTask")
    public ApiResult sendAtTask(
            @RequestParam(value = "at") String at) {
        return userService.sendAtTask(at);
    }

    @PostMapping("/addUser")
    public ApiResult addUser(
            @RequestParam(value = "oid") String oid) {
        return userService.addUser(oid);
    }

    @PostMapping("/users")
    public ApiResult users() {
        return userService.users();
    }

}
