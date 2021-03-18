package com.wujie.pcclient.app.business.repository;


import com.wujie.pcclient.app.business.entity.AppsEvent;

import java.util.List;

public interface AppsEventMapper {
    int deleteByPrimaryKey(Long id);

    int insert(AppsEvent record);

    int insertSelective(AppsEvent record);

    AppsEvent selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(AppsEvent record);

    int updateByPrimaryKey(AppsEvent record);

    List<AppsEvent> findAll();
}