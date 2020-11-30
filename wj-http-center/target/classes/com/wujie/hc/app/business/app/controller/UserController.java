package com.wujie.hc.app.business.app.controller;

import com.wujie.common.base.ApiResult;
import com.wujie.common.dto.ResultVo;
import com.wujie.fclient.service.DispatchUserService;
import com.wujie.fclient.service.TcpUserService;
import com.wujie.hc.app.business.app.service.system.UserService;
import com.wujie.hc.app.business.vo.UserDetailsVo;
import com.wujie.hc.app.framework.auth.SecurityUtil;
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

    @PostMapping("/getChildNodes")
    public ApiResult getChildNodes(@RequestParam(value = "nodeId") Long nodeId) {
        return dispatchUserService.getChildNodes(nodeId);
    }

    @PostMapping("/getTreeData")
    public ApiResult getTreeData(@RequestParam(value = "nodeId") Long nodeId) {
        return dispatchUserService.getTreeData(nodeId);
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
        return dispatchUserService.userRegist(username, password, idcard, phone, userSelected, pSort, cSort, aSort, sSort);
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
                                      @RequestParam(value = "fzwno") String fzwno,
                                      @RequestParam(value = "deviceName") String deviceName,
                                      @RequestParam(value = "selfIp") String selfIp,
                                      @RequestParam(value = "selfPort") String selfPort,
                                      @RequestParam(value = "deviceSelected") String deviceSelected

    ) {
        return tcpUserService.tcpClientConnect(ip, port, fzwno, deviceName, selfIp, selfPort, deviceSelected);
    }

    @PostMapping("/getTcpClientConnectInfo")
    public ApiResult getTcpClientConnectInfo() {
        return tcpUserService.getTcpClientConnectInfo();
    }

    @PostMapping("/getFullFzwno")
    public ApiResult getFullFzwno(@RequestParam(value = "fzwno") String fzwno,
                                  @RequestParam(value = "deviceType") Integer deviceType) {
        return dispatchUserService.getFullFzwno(fzwno, deviceType);
    }

    @PostMapping("/deviceType")
    public ApiResult getAllDevType() {
        UserDetailsVo userInfo = SecurityUtil.getUserInfo();
        return dispatchUserService.getAllDevType(userInfo.getId());
    }

    @PostMapping("/owerLoginNotify")
    public ApiResult owerLoginNotify(@RequestParam(value = "OID") String OID,
                                     @RequestParam(value = "ServerIP") String ServerIP,
                                     @RequestParam(value = "ServerPort") String ServerPort,
                                     @RequestParam(value = "ServerOID") String ServerOID,
                                     @RequestParam(value = "OwnerServerOID") String OwnerServerOID
    ) {
        return dispatchUserService.owerLoginNotify(OID, ServerIP, ServerPort, ServerOID, OwnerServerOID);
    }

    @PostMapping("/wjhttp")
    public ResponseEntity wjhttp(@RequestBody byte[] data
    ) {
        return dispatchUserService.wjhttp(data);
    }

    @PostMapping("/deviceComp")
    public ApiResult deviceComp() {
        return dispatchUserService.deviceComp();
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
        return dispatchUserService.userRegistOwer(username, password, idcard, phone, userSelected, pSort, cSort, aSort, sSort);
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
        return dispatchUserService.deviceRegistManage(userId, deviceSelected, pSort, cSort, aSort, sSort, deviceName, ip, port);
    }

    @PostMapping("/searchNode")
    public ApiResult searchNode(
            @RequestParam(value = "pSort") Integer pSort,
            @RequestParam(value = "cSort") Integer cSort,
            @RequestParam(value = "aSort") Integer aSort,
            @RequestParam(value = "sSort") Integer sSort
    ) {
        return dispatchUserService.searchNode(pSort, cSort, aSort, sSort);
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
        return dispatchUserService.deviceRegistElse(rootIp, idcard, deviceSelected, deviceName, pSort, cSort, aSort, sSort,oneSum,oneTab,twoSum,twoTab,threeSum,threeTab);
    }

    @PostMapping("/acsAll")
    public ApiResult acsAll() {
        return dispatchUserService.acsAll();
    }

    @PostMapping("/seachOwerService")
    public ApiResult seachOwerService(@RequestParam(value = "oid") String oid
    ) {
        return dispatchUserService.seachOwerService(oid);
    }

    @PostMapping("/seachOwerUser")
    public ApiResult seachOwerUser(@RequestParam(value = "oid") String oid
    ) {
        return dispatchUserService.seachOwerUser(oid);
    }

    @PostMapping("/getClass1st")
    public ApiResult getClass1st() {
        return dispatchUserService.getClass1st();
    }

    @PostMapping("/getClass2nd")
    public ApiResult getClass2nd(@RequestParam(value = "id") Long id) {
        return dispatchUserService.getClass2nd(id);
    }

    @PostMapping("/getClass3rd")
    public ApiResult getClass3rd(@RequestParam(value = "id") Long id) {
        return dispatchUserService.getClass3rd(id);
    }

    @PostMapping("/getClass4th")
    public ApiResult getClass4th(@RequestParam(value = "id") Long id) {
        return dispatchUserService.getClass4th(id);
    }

    @PostMapping("/userTrade")
    public ApiResult userTrade(@RequestParam(value = "tid") String tid) {
        UserDetailsVo userInfo = SecurityUtil.getUserInfo();
        return dispatchUserService.userTrade(userInfo.getIdcard(), tid);
    }

    @PostMapping("/userTradeOwer")
    public ApiResult userTradeOwer(@RequestParam(value = "idcard") String idcard,
                                   @RequestParam(value = "tid") String tid) {
        return dispatchUserService.userTradeOwer(idcard, tid);
    }

    @PostMapping("/updataWjuserTrade")
    public ApiResult updataWjuserTrade(@RequestParam(value = "relation") String relation,
                                       @RequestParam(value = "trades") String trades) {
        return dispatchUserService.updataWjuserTrade(relation, trades);
    }

    @PostMapping("/getUserInfo")
    public ApiResult getUserInfo(@RequestParam(value = "id") Long id) {
        return dispatchUserService.getUserInfo(id);
    }

    @PostMapping("/getUserInfoAtOwer")
    public ApiResult getUserInfoAtOwer(@RequestParam(value = "idcard") String idcard) {
        return dispatchUserService.getUserInfoAtOwer(idcard);
    }
}
