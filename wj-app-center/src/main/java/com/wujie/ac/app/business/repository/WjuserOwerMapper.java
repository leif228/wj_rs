package com.wujie.ac.app.business.repository;


import com.wujie.ac.app.business.entity.WjuserOwer;

public interface WjuserOwerMapper {
    int deleteByPrimaryKey(Long id);

    int insert(WjuserOwer record);

    int insertSelective(WjuserOwer record);

    WjuserOwer selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(WjuserOwer record);

    int updateByPrimaryKey(WjuserOwer record);

    WjuserOwer findByUserInfoName(String username);

    WjuserOwer findByIdCard(String idcard);

    WjuserOwer findByFzwnoLikeCAT(String s);

    WjuserOwer findByOid(String targetOid);

    WjuserOwer findByRelationLikeOid(String oid);
}