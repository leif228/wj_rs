package com.wujie.ac.app.business.repository;


import com.wujie.ac.app.business.entity.SdsEventTypeInfo;

public interface SdsEventTypeInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SdsEventTypeInfo record);

    int insertSelective(SdsEventTypeInfo record);

    SdsEventTypeInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SdsEventTypeInfo record);

    int updateByPrimaryKey(SdsEventTypeInfo record);
}