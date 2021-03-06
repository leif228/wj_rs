package com.wujie.dc.app.controller;

import com.wujie.common.base.ApiResult;
import com.wujie.fclient.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

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

    @PostMapping("/wjhttp")
    public ResponseEntity wjhttp(@RequestBody byte[] data
    ) {
        return userService.wjhttp(data);
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

    @PostMapping("/deviceRegistManage")
    public ApiResult deviceRegistManage(@RequestParam(value = "userId") Long userId,
                                        @RequestParam(value = "deviceSelected") String deviceSelected,
                                        @RequestParam(value = "pSort") Integer pSort,
                                        @RequestParam(value = "cSort") Integer cSort,
                                        @RequestParam(value = "aSort") Integer aSort,
                                        @RequestParam(value = "sSort") Integer sSort,
                                        @RequestParam(value = "deviceName") String deviceName,
                                        @RequestParam(value = "ip") String ip,
                                        @RequestParam(value = "port") String port
    ) {
        return userService.deviceRegistManage(userId, deviceSelected, pSort, cSort, aSort, sSort, deviceName, ip, port);
    }

    @PostMapping("/searchNode")
    public ApiResult searchNode(
            @RequestParam(value = "pSort") Integer pSort,
            @RequestParam(value = "cSort") Integer cSort,
            @RequestParam(value = "aSort") Integer aSort,
            @RequestParam(value = "sSort") Integer sSort
    ) {
        return userService.searchNode(pSort, cSort, aSort, sSort);
    }

    @PostMapping("/deviceRegistElse")
    public ApiResult deviceRegistElse(@RequestParam(value = "rootIp") String rootIp,
                                      @RequestParam(value = "idcard") String idcard,
                                      @RequestParam(value = "deviceSelected") String deviceSelected,
                                      @RequestParam(value = "deviceName") String deviceName,
                                      @RequestParam(value = "pSort") Integer pSort,
                                      @RequestParam(value = "cSort") Integer cSort,
                                      @RequestParam(value = "aSort") Integer aSort,
                                      @RequestParam(value = "sSort") Integer sSort,
                                      @RequestParam(value = "oneSum") Integer oneSum,
                                      @RequestParam(value = "oneTab") String oneTab,
                                      @RequestParam(value = "twoSum") Integer twoSum,
                                      @RequestParam(value = "twoTab") String twoTab,
                                      @RequestParam(value = "threeSum") Integer threeSum,
                                      @RequestParam(value = "threeTab") String threeTab
    ) {
        return userService.deviceRegistElse(rootIp, idcard, deviceSelected, deviceName, pSort, cSort, aSort, sSort,oneSum,oneTab,twoSum,twoTab,threeSum,threeTab);
    }

    @PostMapping("/acsAll")
    public ApiResult acsAll() {
        return userService.acsAll();
    }

    @PostMapping("/seachOwerService")
    public ApiResult seachOwerService(@RequestParam(value = "oid") String oid
    ) {
        return userService.seachOwerService(oid);
    }

    @PostMapping("/seachChinaAddr")
    public ApiResult seachChinaAddr(@RequestParam(value = "oid") String oid
    ) {
        return userService.seachChinaAddr(oid);
    }
    @PostMapping("/seachOwerUser")
    public ApiResult seachOwerUser(@RequestParam(value = "oid") String oid
    ) {
        return userService.seachOwerUser(oid);
    }

    @PostMapping("/getClass1st")
    public ApiResult getClass1st() {
        return userService.getClass1st();
    }

    @PostMapping("/getClass2nd")
    public ApiResult getClass2nd(@RequestParam(value = "id") Long id) {
        return userService.getClass2nd(id);
    }

    @PostMapping("/getClass3rd")
    public ApiResult getClass3rd(@RequestParam(value = "id") Long id) {
        return userService.getClass3rd(id);
    }

    @PostMapping("/getClass4th")
    public ApiResult getClass4th(@RequestParam(value = "id") Long id) {
        return userService.getClass4th(id);
    }

    @PostMapping("/userTrade")
    public ApiResult userTrade(@RequestParam(value = "idcard") String idcard,
                               @RequestParam(value = "tid") String tid) {
        return userService.userTrade(idcard, tid);
    }

    @PostMapping("/userTradeOwer")
    public ApiResult userTradeOwer(@RequestParam(value = "idcard") String idcard,
                                   @RequestParam(value = "tid") String tid) {
        return userService.userTradeOwer(idcard, tid);
    }

    @PostMapping("/updataWjuserTrade")
    public ApiResult updataWjuserTrade(@RequestParam(value = "relation") String relation,
                                       @RequestParam(value = "trades") String trades) {
        return userService.updataWjuserTrade(relation, trades);
    }

    @PostMapping("/getUserInfo")
    public ApiResult getUserInfo(@RequestParam(value = "id") Long id) {
        return userService.getUserInfo(id);
    }

    @PostMapping("/getUserInfoAtOwer")
    public ApiResult getUserInfoAtOwer(@RequestParam(value = "idcard") String idcard) {
        return userService.getUserInfoAtOwer(idcard);
    }

    @PostMapping("/getTabsVersion")
    public ApiResult getTabsVersion() {
        return userService.getTabsVersion();
    }

    @PostMapping("/getTabsBuss")
    public ApiResult getTabsBuss() {
        return userService.getTabsBuss();
    }

    @PostMapping("/getTabsDevType")
    public ApiResult getTabsDevType() {
        return userService.getTabsDevType();
    }

    @PostMapping("/getTabsAreacs")
    public ApiResult getTabsAreacs() {
        return userService.getTabsAreacs();
    }
}
