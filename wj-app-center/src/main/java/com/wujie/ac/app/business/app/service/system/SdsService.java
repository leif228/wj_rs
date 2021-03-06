package com.wujie.ac.app.business.app.service.system;

import com.wujie.common.base.ApiResult;
import org.springframework.transaction.annotation.Transactional;

public interface SdsService {
    ApiResult doGenEvent(String oid, String eventType, String content, String bussInfoId, String flag, String pri, String port);

    ApiResult genEvent(String oid, String eventType, String content, String bussInfoId, String flag, String pri, String port);

    ApiResult updataSdsEventRelation(String eventNo, String targetOids);

    ApiResult tradeTaskAtAreaSev(String eventNo, String oid, String eventType, String content, String bussInfoId);

    ApiResult tradeTaskAtAreaSevAtArea(String eventNo, String oid, String eventType, String content, String bussInfoId);

    //    @Transactional(rollbackFor = Exception.class)
    ApiResult doEvent(String oid, String eventType, String content, String eventNo, String bussInfoId, String flag, String pri, String port);

    ApiResult getRelationTypes();

    //    @Transactional(rollbackFor = Exception.class)
    ApiResult addUser(String oid, String relationId, String tooid);

    ApiResult myEventList(String oid);

    ApiResult searchEvents(String eventNo);

    ApiResult searchOwerUserInfo(String oid);

    ApiResult events(String oid, String eventNo);

    @Transactional(rollbackFor = Exception.class)
    ApiResult doEventWrite(String oid, String eventType, String content, String eventNo, String bussInfoId, String flag, String pri, String port);

    //    @Transactional(rollbackFor = Exception.class)
    ApiResult pushTask(String oid, String eventType, String content, String eventNo, String targetOid, String bussInfoId, String flag, String pri, String port);

    ApiResult areaServiceAndSend(String fromOid, String eventType, String content, String eventNo, String toOid, String bussInfoId, String flag, String pri, String port);

    //    @Transactional(rollbackFor = Exception.class)
    ApiResult pushEvent(String genOid, String eventType, String content, String eventNo, String targetOid, String bussInfoId, String relativeEventNo, String flag, String pri, String port);

    ApiResult myUserList(String oid);

    ApiResult doClubUserManage(String flag, String oid, String pri, String buss, String port, String cmd, String param);

    ApiResult clubUserManage(String oid,String operaterOid, String eventNo, String msgType);

    ApiResult doNewClub(String flag, String oid, String pri, String buss, String port, String cmd, String param);

    ApiResult searchOriginEventno(String eventNo);

    ApiResult clubUsers(String eventNo);

    ApiResult searchClubUsers(String eventNo);

    ApiResult searchSqlite(String oid, String db, String table);
}
