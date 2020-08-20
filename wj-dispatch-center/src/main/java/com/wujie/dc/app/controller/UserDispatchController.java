package com.wujie.dc.app.controller;

import com.wujie.common.base.ApiResult;
import com.wujie.fclient.service.AppUserService;
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
public class UserDispatchController {

    private AppUserService userService;

    @Autowired
    public UserDispatchController(AppUserService userService) {
        this.userService = userService;
    }


    @PostMapping("/getTreeData")
    public ApiResult getTreeData(@RequestParam(value = "nodeId") Long nodeId) {
        return userService.getTreeData(nodeId);
    }

    @PostMapping("/secDeviceRegist")
    public ApiResult secDeviceRegist(@RequestParam(value = "userId") Long userId,
                                     @RequestParam(value = "deviceSelected") String deviceSelected,
                                     @RequestParam(value = "deviceName") String deviceName,
                                     @RequestParam(value = "ip") String ip,
                                     @RequestParam(value = "port") String port,
                                     @RequestParam(value = "nodeId") Long nodeId,
                                     @RequestParam(value = "fzwno") String fzwno) {
        return userService.secDeviceRegist(userId, deviceSelected, deviceName, ip, port, nodeId, fzwno);
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
        return userService.preDeviceRegist(userId, deviceSelected, nodeId, pSort, cSort, aSort, sSort);
    }

    @PostMapping("/getChildNodes")
    public ApiResult getChildNodes(@RequestParam(value = "nodeId") Long nodeId) {
        return userService.getChildNodes(nodeId);
    }

    @PostMapping("/addrInit")
    public ApiResult addrInit() {
        return userService.addrInit();
    }

    @PostMapping("/cityByP")
    public ApiResult cityByP(@RequestParam(value = "id") Integer id) {
        return userService.cityByP(id);
    }

    @PostMapping("/areaByC")
    public ApiResult areaByC(@RequestParam(value = "id") Integer id) {
        return userService.areaByC(id);
    }

    @PostMapping("/streetByA")
    public ApiResult streetByA(@RequestParam(value = "id") Integer id) {
        return userService.streetByA(id);
    }

    @PostMapping("/getFullFzwno")
    public ApiResult getFullFzwno(@RequestParam(value = "fzwno") String fzwno,
                                  @RequestParam(value = "deviceType") Integer deviceType) {
        return userService.getFullFzwno(fzwno, deviceType);
    }

    @PostMapping("/getAllDevType")
    public ApiResult getAllDevType(@RequestParam(value = "userId") Long userId) {
        return userService.getAllDevType(userId);
    }

    @PostMapping("/owerLoginNotify")
    public ApiResult owerLoginNotify(@RequestParam(value = "OID") String OID,
                                     @RequestParam(value = "ServerIP") String ServerIP,
                                     @RequestParam(value = "ServerPort") String ServerPort,
                                     @RequestParam(value = "ServerOID") String ServerOID,
                                     @RequestParam(value = "OwnerServerOID") String OwnerServerOID
    ) {
        return userService.owerLoginNotify(OID, ServerIP, ServerPort, ServerOID, OwnerServerOID);
    }

    @PostMapping("/deviceComp")
    public ApiResult deviceComp() {
        return userService.deviceComp();
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

    @PostMapping("/userRegistOwer")
    public ApiResult userRegistOwer(@RequestParam(value = "username") String username,
                                    @RequestParam(value = "password") String password,
                                    @RequestParam(value = "idcard") String idcard,
                                    @RequestParam(value = "phone") String phone,
                                    @RequestParam(value = "userSelected") String userSelected,
                                    @RequestParam(value = "pSort") Integer pSort,
                                    @RequestParam(value = "cSort") Integer cSort,
                                    @RequestParam(value = "aSort") Integer aSort,
                                    @RequestParam(value = "sSort") Integer sSort
    ) {
        return userService.userRegistOwer(username, password, idcard, phone, userSelected, pSort, cSort, aSort, sSort);
    }
}
