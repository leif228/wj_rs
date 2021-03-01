
package com.wujie.fclient.service;

import com.wujie.common.base.ApiResult;
import com.wujie.common.dto.ResultVo;
import com.wujie.common.enums.ErrorEnum;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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

    @PostMapping("/secDeviceRegist")
    public ApiResult secDeviceRegist(@RequestParam(value = "userId") Long userId,
                                     @RequestParam(value = "deviceSelected") String deviceSelected,
                                     @RequestParam(value = "deviceName") String deviceName,
                                     @RequestParam(value = "ip") String ip,
                                     @RequestParam(value = "port") String port,
                                     @RequestParam(value = "nodeId") Long nodeId,
                                     @RequestParam(value = "fzwno") String fzwno);


    @PostMapping("/preDeviceRegist")
    public ApiResult preDeviceRegist(@RequestParam(value = "userId") Long userId,
                                     @RequestParam(value = "deviceSelected") String deviceSelected,
                                     @RequestParam(value = "nodeId") Long nodeId,
                                     @RequestParam(value = "pSort") Integer pSort,
                                     @RequestParam(value = "cSort") Integer cSort,
                                     @RequestParam(value = "aSort") Integer aSort,
                                     @RequestParam(value = "sSort") Integer sSort);

    @PostMapping("/getChildNodes")
    public ApiResult getChildNodes(@RequestParam(value = "nodeId") Long nodeId);

    @PostMapping("/addrInit")
    public ApiResult addrInit();

    @PostMapping("/cityByP")
    public ApiResult cityByP(@RequestParam(value = "id") Integer id);

    @PostMapping("/areaByC")
    public ApiResult areaByC(@RequestParam(value = "id") Integer id);

    @PostMapping("/streetByA")
    public ApiResult streetByA(@RequestParam(value = "id") Integer id);

    @PostMapping("/getFullFzwno")
    public ApiResult getFullFzwno(@RequestParam(value = "fzwno") String fzwno,
                                  @RequestParam(value = "deviceType") Integer deviceType);

    @PostMapping("/getAllDevType")
    public ApiResult getAllDevType(@RequestParam(value = "userId") Long userId);

    @PostMapping("/owerLoginNotify")
    public ApiResult owerLoginNotify(@RequestParam(value = "OID") String OID,
                                     @RequestParam(value = "ServerIP") String ServerIP,
                                     @RequestParam(value = "ServerPort") String ServerPort,
                                     @RequestParam(value = "ServerOID") String ServerOID,
                                     @RequestParam(value = "OwnerServerOID") String OwnerServerOID
    );


    @PostMapping("/wjhttp")
    public ResponseEntity wjhttp(@RequestBody byte[] data
    );

    @PostMapping("/deviceComp")
    public ApiResult deviceComp();

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
    );

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
    );

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
    );


    @PostMapping("/searchNode")
    public ApiResult searchNode(
            @RequestParam(value = "pSort") Integer pSort,
            @RequestParam(value = "cSort") Integer cSort,
            @RequestParam(value = "aSort") Integer aSort,
            @RequestParam(value = "sSort") Integer sSort
    );

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
    );

    @PostMapping("/acsAll")
    public ApiResult acsAll();

    @PostMapping("/doGenEvent")
    public ApiResult doGenEvent(@RequestParam(value = "oid") String oid,
                                @RequestParam(value = "eventType") String eventType,
                                @RequestParam(value = "content") String content,
                                @RequestParam(value = "bussInfoId") String bussInfoId);

    @PostMapping("/genEvent")
    public ApiResult genEvent(@RequestParam(value = "oid") String oid,
                              @RequestParam(value = "eventType") String eventType,
                              @RequestParam(value = "content") String content,
                              @RequestParam(value = "bussInfoId") String bussInfoId);

    @PostMapping("/doEvent")
    public ApiResult doEvent(@RequestParam(value = "oid") String oid,
                             @RequestParam(value = "eventType") String eventType,
                             @RequestParam(value = "content") String content,
                             @RequestParam(value = "bussInfoId") String bussInfoId,
                             @RequestParam(value = "eventNo") String eventNo);


    @PostMapping("/doEventWrite")
    public ApiResult doEventWrite(@RequestParam(value = "oid") String oid,
                                  @RequestParam(value = "eventType") String eventType,
                                  @RequestParam(value = "content") String content,
                                  @RequestParam(value = "eventNo") String eventNo,
                                  @RequestParam(value = "bussInfoId") String bussInfoId) ;

    @PostMapping("/pushEvent")
    public ApiResult pushEvent(@RequestParam(value = "oid") String oid,
                               @RequestParam(value = "eventType") String eventType,
                               @RequestParam(value = "content") String content,
                               @RequestParam(value = "targetOid") String targetOid,
                               @RequestParam(value = "eventNo") String eventNo,
                               @RequestParam(value = "bussInfoId") String bussInfoId);

    @PostMapping("/pushTask")
    public ApiResult pushTask(@RequestParam(value = "oid") String oid,
                              @RequestParam(value = "eventType") String eventType,
                              @RequestParam(value = "content") String content,
                              @RequestParam(value = "targetOid") String targetOid,
                              @RequestParam(value = "eventNo") String eventNo,
                              @RequestParam(value = "bussInfoId") String bussInfoId);

    @PostMapping("/areaServiceAndSend")
    public ApiResult areaServiceAndSend(@RequestParam(value = "fromOid") String fromOid,
                                        @RequestParam(value = "eventType") String eventType,
                                        @RequestParam(value = "content") String content,
                                        @RequestParam(value = "toOid") String toOid,
                                        @RequestParam(value = "eventNo") String eventNo,
                                        @RequestParam(value = "bussInfoId") String bussInfoId);

    @PostMapping("/myEventList")
    public ApiResult myEventList(@RequestParam(value = "oid") String oid);

    @PostMapping("/events")
    public ApiResult events(@RequestParam(value = "oid") String oid, @RequestParam(value = "eventNo") String eventNo);

    @PostMapping("/searchOwerUserInfo")
    public ApiResult searchOwerUserInfo(@RequestParam(value = "oid") String oid);

    @PostMapping("/searchEvents")
    public ApiResult searchEvents(@RequestParam(value = "eventNo") String eventNo);

    @PostMapping("/seachOwerService")
    public ApiResult seachOwerService(@RequestParam(value = "oid") String oid
    );

    @PostMapping("/seachChinaAddr")
    public ApiResult seachChinaAddr(@RequestParam(value = "oid") String oid
    );

    @PostMapping("/seachOwerUser")
    public ApiResult seachOwerUser(@RequestParam(value = "oid") String oid
    );

    @PostMapping("/addUser")
    public ApiResult addUser(@RequestParam(value = "oid") String oid,
                             @RequestParam(value = "relationId") String relationId,
                             @RequestParam(value = "tooid") String tooid);

    @PostMapping("/myUserList")
    public ApiResult myUserList(@RequestParam(value = "oid") String oid);

    @PostMapping("/getRelationTypes")
    public ApiResult getRelationTypes();

    @PostMapping("/atTask")
    public ApiResult atTask(@RequestParam(value = "flag") String flag,
                            @RequestParam(value = "oid") String oid,
                            @RequestParam(value = "pri") String pri,
                            @RequestParam(value = "buss") String buss,
                            @RequestParam(value = "port") String port,
                            @RequestParam(value = "cmd") String cmd,
                            @RequestParam(value = "param") String param);

    @PostMapping("/getClass1st")
    public ApiResult getClass1st();

    @PostMapping("/getClass2nd")
    public ApiResult getClass2nd(@RequestParam(value = "id") Long id);

    @PostMapping("/getClass3rd")
    public ApiResult getClass3rd(@RequestParam(value = "id") Long id) ;
    @PostMapping("/getClass4th")
    public ApiResult getClass4th(@RequestParam(value = "id") Long id) ;

    @PostMapping("/userTrade")
    public ApiResult userTrade(@RequestParam(value = "idcard") String idcard,
                               @RequestParam(value = "tid") String tid);

    @PostMapping("/userTradeOwer")
    public ApiResult userTradeOwer(@RequestParam(value = "idcard") String idcard,
                                   @RequestParam(value = "tid") String tid) ;

    @PostMapping("/updataWjuserTrade")
    public ApiResult updataWjuserTrade(@RequestParam(value = "relation") String relation,
                                       @RequestParam(value = "trades") String trades);

    @PostMapping("/tradeTaskAtAreaSev")
    public ApiResult tradeTaskAtAreaSev(@RequestParam(value = "eventNo") String eventNo,
                                        @RequestParam(value = "oid") String oid,
                                        @RequestParam(value = "eventType") String eventType,
                                        @RequestParam(value = "content") String content,
                                        @RequestParam(value = "bussInfoId") String bussInfoId);

    @PostMapping("/tradeTaskAtAreaSevAtArea")
    public ApiResult tradeTaskAtAreaSevAtArea(@RequestParam(value = "eventNo") String eventNo,
                                              @RequestParam(value = "oid") String oid,
                                              @RequestParam(value = "eventType") String eventType,
                                              @RequestParam(value = "content") String content,
                                              @RequestParam(value = "bussInfoId") String bussInfoId);

    @PostMapping("/updataSdsEventRelation")
    public ApiResult updataSdsEventRelation(@RequestParam(value = "eventNo") String eventNo,
                                            @RequestParam(value = "targetOids") String targetOids);
    @PostMapping("/getUserInfo")
    public ApiResult getUserInfo(@RequestParam(value = "id") Long id);

    @PostMapping("/getUserInfoAtOwer")
    public ApiResult getUserInfoAtOwer(@RequestParam(value = "idcard") String idcard);

    @PostMapping("/getTabsVersion")
    public ApiResult getTabsVersion();

    @PostMapping("/getTabsBuss")
    public ApiResult getTabsBuss();

    @PostMapping("/getTabsDevType")
    public ApiResult getTabsDevType();

    @PostMapping("/getTabsAreacs")
    public ApiResult getTabsAreacs();

    @Component
    class DispatchUserServiceFallBack implements com.wujie.fclient.service.DispatchUserService {


        @Override
        public ApiResult getTreeData(Long nodeId) {
            return ApiResult.error(ErrorEnum.ERR_DSERVICE_NOT);
        }

        @Override
        public ApiResult secDeviceRegist(Long userId, String deviceSelected, String deviceName, String ip, String port, Long nodeId, String fzwno) {
            return ApiResult.error(ErrorEnum.ERR_DSERVICE_NOT);
        }

        @Override
        public ApiResult preDeviceRegist(Long userId, String deviceSelected, Long nodeId, Integer pSort, Integer cSort, Integer aSort, Integer sSort) {
            return ApiResult.error(ErrorEnum.ERR_DSERVICE_NOT);
        }

        @Override
        public ApiResult getChildNodes(Long nodeId) {
            return ApiResult.error(ErrorEnum.ERR_DSERVICE_NOT);
        }

        @Override
        public ApiResult addrInit() {
            return ApiResult.error(ErrorEnum.ERR_DSERVICE_NOT);
        }

        @Override
        public ApiResult cityByP(Integer id) {
            return ApiResult.error(ErrorEnum.ERR_DSERVICE_NOT);
        }

        @Override
        public ApiResult areaByC(Integer id) {
            return ApiResult.error(ErrorEnum.ERR_DSERVICE_NOT);
        }

        @Override
        public ApiResult streetByA(Integer id) {
            return ApiResult.error(ErrorEnum.ERR_DSERVICE_NOT);
        }

        @Override
        public ApiResult getFullFzwno(String fzwno, Integer deviceType) {
            return ApiResult.error(ErrorEnum.ERR_DSERVICE_NOT);
        }

        @Override
        public ApiResult getAllDevType(Long userId) {
            return ApiResult.error(ErrorEnum.ERR_DSERVICE_NOT);
        }

        @Override
        public ApiResult owerLoginNotify(String OID, String ServerIP, String ServerPort, String ServerOID, String OwnerServerOID) {
            return ApiResult.error(ErrorEnum.ERR_DSERVICE_NOT);
        }

        @Override
        public ResponseEntity wjhttp(byte[] data) {
            return ResponseEntity.ok().body(ErrorEnum.ERR_DSERVICE_NOT.getErrMsg());
        }

        @Override
        public ApiResult deviceComp() {
            return ApiResult.error(ErrorEnum.ERR_DSERVICE_NOT);
        }

        @Override
        public ApiResult userRegist(String username, String password, String idcard, String phone, String userSelected, Integer pSort, Integer cSort, Integer aSort, Integer sSort) {
            return ApiResult.error(ErrorEnum.ERR_DSERVICE_NOT);
        }

        @Override
        public ApiResult userRegistOwer(String username, String password, String idcard, String phone, String userSelected, Integer pSort, Integer cSort, Integer aSort, Integer sSort) {
            return ApiResult.error(ErrorEnum.ERR_DSERVICE_NOT);
        }

        @Override
        public ApiResult deviceRegistManage(Long userId, String deviceSelected, Integer pSort, Integer cSort, Integer aSort, Integer sSort, String deviceName, String ip, String port) {
            return ApiResult.error(ErrorEnum.ERR_DSERVICE_NOT);
        }

        @Override
        public ApiResult searchNode(Integer pSort, Integer cSort, Integer aSort, Integer sSort) {
            return ApiResult.error(ErrorEnum.ERR_DSERVICE_NOT);
        }

        @Override
        public ApiResult deviceRegistElse(String rootIp, String idcard, String deviceSelected, String deviceName, Integer pSort, Integer cSort, Integer aSort, Integer sSort, Integer oneSum, String oneTab, Integer twoSum, String twoTab, Integer threeSum, String threeTab) {
            return ApiResult.error(ErrorEnum.ERR_DSERVICE_NOT);
        }

        @Override
        public ApiResult acsAll() {
            return ApiResult.error(ErrorEnum.ERR_DSERVICE_NOT);
        }

        @Override
        public ApiResult doGenEvent(String oid, String eventType, String content, String bussInfoId) {
            return ApiResult.error(ErrorEnum.ERR_DSERVICE_NOT);
        }

        @Override
        public ApiResult genEvent(String oid, String eventType, String content, String bussInfoId) {
            return ApiResult.error(ErrorEnum.ERR_DSERVICE_NOT);
        }

        @Override
        public ApiResult doEvent(String oid, String eventType, String content, String eventNo, String bussInfoId) {
            return ApiResult.error(ErrorEnum.ERR_DSERVICE_NOT);
        }

        @Override
        public ApiResult doEventWrite(String oid, String eventType, String content, String eventNo, String bussInfoId) {
            return ApiResult.error(ErrorEnum.ERR_DSERVICE_NOT);
        }

        @Override
        public ApiResult pushEvent(String oid, String eventType, String content, String targetOid, String eventNo, String bussInfoId) {
            return ApiResult.error(ErrorEnum.ERR_DSERVICE_NOT);
        }

        @Override
        public ApiResult pushTask(String oid, String eventType, String content, String targetOid, String eventNo, String bussInfoId) {
            return ApiResult.error(ErrorEnum.ERR_DSERVICE_NOT);
        }

        @Override
        public ApiResult areaServiceAndSend(String fromOid, String eventType, String content, String toOid, String eventNo, String bussInfoId) {
            return ApiResult.error(ErrorEnum.ERR_DSERVICE_NOT);
        }

        @Override
        public ApiResult myEventList(String oid) {
            return ApiResult.error(ErrorEnum.ERR_DSERVICE_NOT);
        }

        @Override
        public ApiResult events(String oid, String eventNo) {
            return ApiResult.error(ErrorEnum.ERR_DSERVICE_NOT);
        }

        @Override
        public ApiResult searchOwerUserInfo(String oid) {
            return ApiResult.error(ErrorEnum.ERR_DSERVICE_NOT);
        }

        @Override
        public ApiResult searchEvents(String eventNo) {
            return ApiResult.error(ErrorEnum.ERR_DSERVICE_NOT);
        }

        @Override
        public ApiResult seachOwerService(String oid) {
            return ApiResult.error(ErrorEnum.ERR_DSERVICE_NOT);
        }

        @Override
        public ApiResult seachChinaAddr(String oid) {
            return ApiResult.error(ErrorEnum.ERR_DSERVICE_NOT);
        }

        @Override
        public ApiResult seachOwerUser(String oid) {
            return ApiResult.error(ErrorEnum.ERR_DSERVICE_NOT);
        }

        @Override
        public ApiResult addUser(String oid, String relationId, String tooid) {
            return ApiResult.error(ErrorEnum.ERR_DSERVICE_NOT);
        }

        @Override
        public ApiResult myUserList(String oid) {
            return ApiResult.error(ErrorEnum.ERR_DSERVICE_NOT);
        }

        @Override
        public ApiResult getRelationTypes() {
            return ApiResult.error(ErrorEnum.ERR_DSERVICE_NOT);
        }

        @Override
        public ApiResult atTask(String flag, String oid, String pri, String buss, String port, String cmd, String param) {
            return ApiResult.error(ErrorEnum.ERR_DSERVICE_NOT);
        }

        @Override
        public ApiResult getClass1st() {
            return ApiResult.error(ErrorEnum.ERR_DSERVICE_NOT);
        }

        @Override
        public ApiResult getClass2nd(Long id) {
            return ApiResult.error(ErrorEnum.ERR_DSERVICE_NOT);
        }

        @Override
        public ApiResult getClass3rd(Long id) {
            return ApiResult.error(ErrorEnum.ERR_DSERVICE_NOT);
        }

        @Override
        public ApiResult getClass4th(Long id) {
            return ApiResult.error(ErrorEnum.ERR_DSERVICE_NOT);
        }

        @Override
        public ApiResult userTrade(String idcard, String tid) {
            return ApiResult.error(ErrorEnum.ERR_DSERVICE_NOT);

        }

        @Override
        public ApiResult userTradeOwer(String idcard, String tid) {
            return ApiResult.error(ErrorEnum.ERR_DSERVICE_NOT);
        }

        @Override
        public ApiResult updataWjuserTrade(String relation, String trades) {
            return ApiResult.error(ErrorEnum.ERR_DSERVICE_NOT);
        }

        @Override
        public ApiResult tradeTaskAtAreaSev(String eventNo, String oid, String eventType, String content, String bussInfoId) {
            return ApiResult.error(ErrorEnum.ERR_DSERVICE_NOT);
        }

        @Override
        public ApiResult tradeTaskAtAreaSevAtArea(String eventNo, String oid, String eventType, String content, String bussInfoId) {
            return ApiResult.error(ErrorEnum.ERR_DSERVICE_NOT);
        }

        @Override
        public ApiResult updataSdsEventRelation(String eventNo, String targetOids) {
            return ApiResult.error(ErrorEnum.ERR_DSERVICE_NOT);
        }

        @Override
        public ApiResult getUserInfo(Long id) {
            return ApiResult.error(ErrorEnum.ERR_DSERVICE_NOT);
        }

        @Override
        public ApiResult getUserInfoAtOwer(String idcard) {
            return ApiResult.error(ErrorEnum.ERR_DSERVICE_NOT);
        }

        @Override
        public ApiResult getTabsVersion() {
            return ApiResult.error(ErrorEnum.ERR_DSERVICE_NOT);
        }

        @Override
        public ApiResult getTabsBuss() {
            return ApiResult.error(ErrorEnum.ERR_DSERVICE_NOT);
        }

        @Override
        public ApiResult getTabsDevType() {
            return ApiResult.error(ErrorEnum.ERR_DSERVICE_NOT);
        }

        @Override
        public ApiResult getTabsAreacs() {
            return ApiResult.error(ErrorEnum.ERR_DSERVICE_NOT);
        }

    }


}