package com.wujie.ac.app.business.repository;


import com.wujie.ac.app.business.entity.SdsRelationTypeInfo;

import java.util.List;

public interface SdsRelationTypeInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SdsRelationTypeInfo record);

    int insertSelective(SdsRelationTypeInfo record);

    SdsRelationTypeInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SdsRelationTypeInfo record);

    int updateByPrimaryKey(SdsRelationTypeInfo record);

    List<SdsRelationTypeInfo> findAll();
}