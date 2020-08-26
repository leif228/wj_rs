package com.wujie.ac.app.business.repository;


import com.wujie.ac.app.business.entity.SdsEventPersonRecord;
import com.wujie.common.dto.wj.SdsEventListDto;

import java.util.List;

public interface SdsEventPersonRecordMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SdsEventPersonRecord record);

    int insertSelective(SdsEventPersonRecord record);

    SdsEventPersonRecord selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SdsEventPersonRecord record);

    int updateByPrimaryKey(SdsEventPersonRecord record);

    SdsEventPersonRecord findByEventNoAndOid(String eventNo, String oid);

    List<SdsEventListDto> findByOid(String oid);

}