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

    @PostMapping("/genEvent")
    public ApiResult genEvent(@RequestParam(value = "oid") String oid,
                              @RequestParam(value = "eventType") String eventType,
                              @RequestParam(value = "content") String content) {
        return sdsService.genEvent(oid,eventType,content);
    }

    @PostMapping("/doEvent")
    public ApiResult doEvent(@RequestParam(value = "oid") String oid,
                             @RequestParam(value = "eventType") String eventType,
                             @RequestParam(value = "content") String content,
                             @RequestParam(value = "genOid") String genOid,
                             @RequestParam(value = "eventNo") String eventNo) {
        return sdsService.doEvent(oid,eventType,content,eventNo,genOid);
    }

    @PostMapping("/doEventWrite")
    public ApiResult doEventWrite(@RequestParam(value = "oid") String oid,
                                  @RequestParam(value = "eventType") String eventType,
                                  @RequestParam(value = "content") String content,
                                  @RequestParam(value = "eventNo") String eventNo) {
        return sdsService.doEventWrite(oid,eventType,content,eventNo);
    }

    @PostMapping("/pushEvent")
    public ApiResult pushEvent(@RequestParam(value = "oid") String oid,
                               @RequestParam(value = "eventType") String eventType,
                               @RequestParam(value = "content") String content,
                               @RequestParam(value = "targetOid") String targetOid,
                               @RequestParam(value = "eventNo") String eventNo) {
        return sdsService.pushEvent(oid,eventType,content,eventNo,targetOid);
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
}