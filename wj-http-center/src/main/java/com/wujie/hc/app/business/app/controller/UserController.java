package com.wujie.hc.app.business.app.controller;

import com.wujie.common.base.ApiResult;
import com.wujie.common.dto.ResultVo;
import com.wujie.fclient.service.DispatchUserService;
import com.wujie.fclient.service.TcpUserService;
import com.wujie.hc.app.business.app.service.system.UserService;
import com.wujie.hc.app.business.vo.UserDetailsVo;
import com.wujie.hc.app.framework.auth.SecurityUtil;
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

    @Autowired
    public UserController(DispatchUserService dispatchUserService, UserService userService, TcpUserService tcpUserService) {
        this.dispatchUserService = dispatchUserService;
        this.tcpUserService = tcpUserService;
        this.userService = userService;
    }

    @PostMapping("/secDeviceRegist")
    public ApiResult secDeviceRegist(@RequestParam(value = "userId") Long userId,
                                     @RequestParam(value = "deviceSelected") String deviceSelected,
                                     @RequestParam(value = "deviceName") String deviceName,
                                     @RequestParam(value = "ip") String ip,
                                     @RequestParam(value = "port") String port,
                                     @RequestParam(value = "nodeId") Long nodeId,
                                     @RequestParam(value = "fzwno") String fzwno) {
        UserDetailsVo userInfo = SecurityUtil.getUserInfo();
        return dispatchUserService.secDeviceRegist(userInfo.getId(), deviceSelected, deviceName, ip, port, nodeId, fzwno);
    }

    @PostMapping("/preDeviceRegist")
    public ApiResult preDeviceRegist(@RequestParam(value = "userId") Long userId,
                                     @RequestParam(value = "deviceSelected") String deviceSelected,
                                     @RequestParam(value = "nodeId") Long nodeId,
                                     @RequestParam(value = "pSort") Integer pSort,
                                     @RequestParam(value = "cSort") Integer cSort,
                                     @RequestParam(value = "aSort") Integer aSort,
                                     @RequestParam(value = "sSort") Integer sSort
    ) {
        UserDetailsVo userInfo = SecurityUtil.getUserInfo();
        return dispatchUserService.preDeviceRegist(userInfo.getId(), deviceSelected, nodeId, pSort, cSort, aSort, sSort);
    }

    @PostMapping("/deviceType")
    public ApiResult deviceType(
    ) {
        UserDetailsVo userInfo = SecurityUtil.getUserInfo();
        return userService.deviceType(userInfo);
    }

    @PostMapping("/getChildNodes")
    public ApiResult getChildNodes(@RequestParam(value = "nodeId") Long nodeId) {
        return dispatchUserService.getChildNodes(nodeId);
    }

    @PostMapping("/getTreeData")
    public ApiResult getTreeData(@RequestParam(value = "nodeId") Long nodeId) {
        return dispatchUserService.getTreeData(nodeId);
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

    @PostMapping("/tcpClientConnect")
    public ApiResult tcpClientConnect(@RequestParam(value = "ip") String ip,
                                      @RequestParam(value = "port") String port,
                                      @RequestParam(value = "fzwno") String fzwno

    ) {
        return tcpUserService.tcpClientConnect(ip, port, fzwno);
    }

    @PostMapping("/getTcpClientConnectInfo")
    public ApiResult getTcpClientConnectInfo() {
        return tcpUserService.getTcpClientConnectInfo();
    }

    @PostMapping("/addrInit")
    public ApiResult addrInit() {
        return dispatchUserService.addrInit();
    }

    @PostMapping("/cityByP")
    public ApiResult cityByP(@RequestParam(value = "id") Integer id) {
        return dispatchUserService.cityByP(id);
    }

    @PostMapping("/areaByC")
    public ApiResult areaByC(@RequestParam(value = "id") Integer id) {
        return dispatchUserService.areaByC(id);
    }

    @PostMapping("/streetByA")
    public ApiResult streetByA(@RequestParam(value = "id") Integer id) {
        return dispatchUserService.streetByA(id);
    }
}
