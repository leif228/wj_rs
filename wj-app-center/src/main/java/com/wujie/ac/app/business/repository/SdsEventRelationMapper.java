package com.wujie.ac.app.business.repository;


import com.wujie.ac.app.business.entity.SdsEventRelation;

public interface SdsEventRelationMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SdsEventRelation record);

    int insertSelective(SdsEventRelation record);

    SdsEventRelation selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SdsEventRelation record);

    int updateByPrimaryKey(SdsEventRelation record);

    SdsEventRelation findByEventNo(String eventNo);
}