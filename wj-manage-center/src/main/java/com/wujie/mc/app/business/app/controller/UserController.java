package com.wujie.mc.app.business.app.controller;

import com.wujie.common.base.ApiResult;
import com.wujie.fclient.service.AppUserService;
import com.wujie.fclient.service.DispatchUserService;
import com.wujie.fclient.service.TcpUserService;
import com.wujie.mc.app.business.app.service.system.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName: UserController
 * @Description: 用户控制层
 * @Date: 2020/4/21 14:27
 */
@RestController
public class UserController {

    private DispatchUserService dispatchUserService;
    private TcpUserService tcpUserService;
    private UserService userService;
    private AppUserService appUserService;

    @Autowired
    public UserController(AppUserService appUserService,DispatchUserService dispatchUserService, UserService userService, TcpUserService tcpUserService) {
        this.appUserService = appUserService;
        this.dispatchUserService = dispatchUserService;
        this.tcpUserService = tcpUserService;
        this.userService = userService;
    }

    @PostMapping("/userRegist")
    public ApiResult userRegist(@RequestParam(value = "username") String username,
                                @RequestParam(value = "password") String password,
                                @RequestParam(value = "idcard") String idcard,
                                @RequestParam(value = "phone") String phone,
                                @RequestParam(value = "userSelected") String userSelected,
                                @RequestParam(value = "pSort") Integer pSort,
                                @RequestParam(value = "cSort") Integer cSort,
                                @RequestParam(value = "aSort") Integer aSort,
                                @RequestParam(value = "sSort") Integer sSort
    ) {
        return userService.userRegist(username, password, idcard, phone, userSelected, pSort, cSort, aSort, sSort);
    }

    @PostMapping("/userLogin")
    public ApiResult userLogin(@RequestParam(value = "username") String username,
                               @RequestParam(value = "password") String password
    ) {
        return userService.userLogin(username, password);
    }

    @PostMapping("/getBaseTabType")
    public ApiResult getBaseTabType() {
        return userService.getBaseTabType();
    }

    @PostMapping("/getTabByType")
    public ApiResult getTabByType(@RequestParam(value = "name") String name) {
        return appUserService.getTabByType(name);
    }

    @PostMapping("/updateTabByType")
    public ApiResult updateTabByType(@RequestParam(value = "name") String name,
                                     @RequestParam(value = "jsonObject") String jsonObject) {
        return userService.updateTabByType(name,jsonObject);
    }

    @PostMapping("/getTreeData")
    public ApiResult getTreeData(@RequestParam(value = "nodeId") Long nodeId) {
        return appUserService.getTreeData(nodeId);
    }

}
