package com.wujie.ac.app.business.repository;


import com.wujie.ac.app.business.entity.SdsEventInfo;
import com.wujie.common.dto.wj.SdsEventInfoDto;

import java.util.List;

public interface SdsEventInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SdsEventInfo record);

    int insertSelective(SdsEventInfo record);

    SdsEventInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SdsEventInfo record);

    int updateByPrimaryKey(SdsEventInfo record);

    List<SdsEventInfo> findByEventNo(String eventNo);
}