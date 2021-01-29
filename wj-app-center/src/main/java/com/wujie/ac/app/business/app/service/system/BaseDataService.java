package com.wujie.ac.app.business.app.service.system;

import com.wujie.ac.app.business.entity.AreaChangSeq;
import com.wujie.common.base.ApiResult;
import com.wujie.common.dto.wj.AreaChangSeqDto;

import java.util.List;

public interface BaseDataService {

    ApiResult addrInit();

    ApiResult cityByP(Integer id);

    ApiResult areaByC(Integer id);

    ApiResult streetByA(Integer id);

    AreaChangSeq fzwaddrBySort(Integer sort);

    AreaChangSeq sortByFzwaddr(String fzw_str);

    List<AreaChangSeqDto> acsAll();

    ApiResult findAllP();

    ApiResult findAllC();

    ApiResult findAllA();

    ApiResult findAllS();
}
