package com.wujie.ac.app.business.app.service.system;

import com.wujie.common.base.ApiResult;
import org.springframework.transaction.annotation.Transactional;

public interface SdsService {
    ApiResult genEvent(String oid, String eventType, String content);

    @Transactional(rollbackFor = Exception.class)
    ApiResult doEvent(String oid, String eventType, String content, String eventNo);

    ApiResult getRelationTypes();

    //    @Transactional(rollbackFor = Exception.class)
    ApiResult addUser(String oid, String relationId, String tooid);

    ApiResult myEventList(String oid);

    ApiResult events(String eventNo);

    ApiResult myUserList(String oid);
}
