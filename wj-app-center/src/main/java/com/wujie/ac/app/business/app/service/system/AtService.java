package com.wujie.ac.app.business.app.service.system;

import com.wujie.common.base.ApiResult;

public interface AtService {
    ApiResult doAtTask(String tx);

    ApiResult atTask(String flag, String oid, String pri, String buss, String port, String cmd, String param) ;
}
