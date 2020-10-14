package com.wujie.dc.app.controller;

import com.wujie.common.base.ApiResult;
import com.wujie.fclient.service.AppUserService;
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

    private AppUserService sdsService;

    @Autowired
    public SdsController(AppUserService sdsService) {
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

    @PostMapping("/pushTask")
    public ApiResult pushTask(@RequestParam(value = "oid") String oid,
                              @RequestParam(value = "eventType") String eventType,
                              @RequestParam(value = "content") String content,
                              @RequestParam(value = "targetOid") String targetOid,
                              @RequestParam(value = "eventNo") String eventNo,
                              @RequestParam(value = "bussInfoId") String bussInfoId) {
        return sdsService.pushTask(oid, eventType, content, eventNo, targetOid, bussInfoId);
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

    @PostMapping("/myEventList")
    public ApiResult myEventList(@RequestParam(value = "oid") String oid) {
        return sdsService.myEventList(oid);
    }

    @PostMapping("/events")
    public ApiResult events(@RequestParam(value = "oid") String oid, @RequestParam(value = "eventNo") String eventNo) {
        return sdsService.events(oid,eventNo);
    }

    @PostMapping("/searchOwerUserInfo")
    public ApiResult searchOwerUserInfo(@RequestParam(value = "oid") String oid) {
        return sdsService.searchOwerUserInfo(oid);
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

    @PostMapping("/atTask")
    public ApiResult atTask(@RequestParam(value = "flag") String flag,
                            @RequestParam(value = "oid") String oid,
                            @RequestParam(value = "pri") String pri,
                            @RequestParam(value = "buss") String buss,
                            @RequestParam(value = "port") String port,
                            @RequestParam(value = "cmd") String cmd,
                            @RequestParam(value = "param") String param) {
        return sdsService.atTask( flag,  oid,  pri,  buss,  port,  cmd,  param);
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
}