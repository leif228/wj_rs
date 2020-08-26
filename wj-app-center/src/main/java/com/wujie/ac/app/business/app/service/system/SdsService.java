package com.wujie.ac.app.business.app.service.system;

import com.wujie.common.base.ApiResult;
import org.springframework.transaction.annotation.Transactional;

public interface SdsService {
    ApiResult genEvent(String oid, String eventType, String content);

    @Transactional(rollbackFor = Exception.class)
    ApiResult doEvent(String oid, String eventType, String content, String eventNo);

    ApiResult myEventList(String oid);

    ApiResult events(String eventNo);
}
