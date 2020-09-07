package com.wujie.ac.app.business.repository;


import com.wujie.ac.app.business.entity.SdsPercomRelation;

import java.util.List;

public interface SdsPercomRelationMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SdsPercomRelation record);

    int insertSelective(SdsPercomRelation record);

    SdsPercomRelation selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SdsPercomRelation record);

    int updateByPrimaryKey(SdsPercomRelation record);

    List<SdsPercomRelation> findBySelfOidAndWeight(String selfOid, Integer eventRelationLevel);

    SdsPercomRelation findByOidAndTargetOid(String oid, String tooid);

    List<SdsPercomRelation> findBySelfOid(String oid);
}