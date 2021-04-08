package com.wujie.ac.app.business.app.controller;

import com.wujie.ac.app.business.app.service.system.SdsService;
import com.wujie.common.base.ApiResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName: SdsController
 * @Description: sds控制层
 * @Date: 2020/4/21 14:27
 */
@RestController
public class SdsController {

    private SdsService sdsService;

    @Autowired
    public SdsController(SdsService sdsService) {
        this.sdsService = sdsService;
    }

    @PostMapping("/doGenEvent")
    public ApiResult doGenEvent(@RequestParam(value = "oid") String oid,
                                @RequestParam(value = "eventType") String eventType,
                                @RequestParam(value = "content") String content,
                                @RequestParam(value = "bussInfoId") String bussInfoId) {
        return sdsService.doGenEvent(oid, eventType, content, bussInfoId);
    }

    @PostMapping("/genEvent")
    public ApiResult genEvent(@RequestParam(value = "oid") String oid,
                              @RequestParam(value = "eventType") String eventType,
                              @RequestParam(value = "content") String content,
                              @RequestParam(value = "bussInfoId") String bussInfoId) {
        return sdsService.genEvent(oid, eventType, content, bussInfoId);
    }

    @PostMapping("/doEvent")
    public ApiResult doEvent(@RequestParam(value = "oid") String oid,
                             @RequestParam(value = "eventType") String eventType,
                             @RequestParam(value = "content") String content,
                             @RequestParam(value = "eventNo") String eventNo,
                             @RequestParam(value = "bussInfoId") String bussInfoId) {
        return sdsService.doEvent(oid, eventType, content, eventNo, bussInfoId);
    }

    @PostMapping("/doEventWrite")
    public ApiResult doEventWrite(@RequestParam(value = "oid") String oid,
                                  @RequestParam(value = "eventType") String eventType,
                                  @RequestParam(value = "content") String content,
                                  @RequestParam(value = "eventNo") String eventNo,
                                  @RequestParam(value = "bussInfoId") String bussInfoId) {
        return sdsService.doEventWrite(oid, eventType, content, eventNo, bussInfoId);
    }

    @PostMapping("/pushEvent")
    public ApiResult pushEvent(@RequestParam(value = "oid") String oid,
                               @RequestParam(value = "eventType") String eventType,
                               @RequestParam(value = "content") String content,
                               @RequestParam(value = "targetOid") String targetOid,
                               @RequestParam(value = "eventNo") String eventNo,
                               @RequestParam(value = "bussInfoId") String bussInfoId) {
        return sdsService.pushEvent(oid, eventType, content, eventNo, targetOid, bussInfoId);
    }

    @PostMapping("/areaServiceAndSend")
    public ApiResult areaServiceAndSend(@RequestParam(value = "fromOid") String fromOid,
                                        @RequestParam(value = "eventType") String eventType,
                                        @RequestParam(value = "content") String content,
                                        @RequestParam(value = "toOid") String toOid,
                                        @RequestParam(value = "eventNo") String eventNo,
                                        @RequestParam(value = "bussInfoId") String bussInfoId) {
        return sdsService.areaServiceAndSend(fromOid, eventType, content, eventNo, toOid, bussInfoId);
    }

    @PostMapping("/pushTask")
    public ApiResult pushTask(@RequestParam(value = "oid") String oid,
                              @RequestParam(value = "eventType") String eventType,
                              @RequestParam(value = "content") String content,
                              @RequestParam(value = "targetOid") String targetOid,
                              @RequestParam(value = "eventNo") String eventNo,
                              @RequestParam(value = "bussInfoId") String bussInfoId) {
        return sdsService.pushTask(oid, eventType, content, eventNo, targetOid, bussInfoId);
    }

    @PostMapping("/myEventList")
    public ApiResult myEventList(@RequestParam(value = "oid") String oid) {
        return sdsService.myEventList(oid);
    }

    @PostMapping("/searchOwerUserInfo")
    public ApiResult searchOwerUserInfo(@RequestParam(value = "oid") String oid) {
        return sdsService.searchOwerUserInfo(oid);
    }

    @PostMapping("/events")
    public ApiResult events(@RequestParam(value = "oid") String oid, @RequestParam(value = "eventNo") String eventNo) {
        return sdsService.events(oid, eventNo);
    }

    @PostMapping("/searchEvents")
    public ApiResult searchEvents(@RequestParam(value = "eventNo") String eventNo) {
        return sdsService.searchEvents(eventNo);
    }

    @PostMapping("/addUser")
    public ApiResult addUser(@RequestParam(value = "oid") String oid,
                             @RequestParam(value = "relationId") String relationId,
                             @RequestParam(value = "tooid") String tooid) {
        return sdsService.addUser(oid, relationId, tooid);
    }

    @PostMapping("/myUserList")
    public ApiResult myUserList(@RequestParam(value = "oid") String oid) {
        return sdsService.myUserList(oid);
    }

    @PostMapping("/getRelationTypes")
    public ApiResult getRelationTypes() {
        return sdsService.getRelationTypes();
    }

    @PostMapping("/tradeTaskAtAreaSev")
    public ApiResult tradeTaskAtAreaSev(@RequestParam(value = "eventNo") String eventNo,
                                        @RequestParam(value = "oid") String oid,
                                        @RequestParam(value = "eventType") String eventType,
                                        @RequestParam(value = "content") String content,
                                        @RequestParam(value = "bussInfoId") String bussInfoId) {
        return sdsService.tradeTaskAtAreaSev(eventNo, oid, eventType, content, bussInfoId);
    }

    @PostMapping("/tradeTaskAtAreaSevAtArea")
    public ApiResult tradeTaskAtAreaSevAtArea(@RequestParam(value = "eventNo") String eventNo,
                                        @RequestParam(value = "oid") String oid,
                                        @RequestParam(value = "eventType") String eventType,
                                        @RequestParam(value = "content") String content,
                                        @RequestParam(value = "bussInfoId") String bussInfoId) {
        return sdsService.tradeTaskAtAreaSevAtArea(eventNo, oid, eventType, content, bussInfoId);
    }

    @PostMapping("/updataSdsEventRelation")
    public ApiResult updataSdsEventRelation(@RequestParam(value = "eventNo") String eventNo,
                                              @RequestParam(value = "targetOids") String targetOids) {
        return sdsService.updataSdsEventRelation(eventNo, targetOids);
    }

    @PostMapping("/clubUserManage")
    public ApiResult clubUserManage(@RequestParam(value = "oid") String oid,
                                    @RequestParam(value = "operaterOid") String operaterOid,
                               @RequestParam(value = "eventNo") String eventNo,
                               @RequestParam(value = "msgType") String msgType) {
        return sdsService.clubUserManage(oid,operaterOid, eventNo, msgType);
    }

}