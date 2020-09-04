package com.wujie.ac.app.business.app.service.system;

import com.wujie.ac.app.business.entity.AreaChangSeq;
import com.wujie.common.base.ApiResult;

public interface BaseDataService {

    ApiResult addrInit();

    ApiResult cityByP(Integer id);

    ApiResult areaByC(Integer id);

    ApiResult streetByA(Integer id);

    AreaChangSeq fzwaddrBySort(Integer sort);

    AreaChangSeq sortByFzwaddr(String fzw_str);
}
