package com.wujie.apps.app.business.repository;


import com.wujie.apps.app.business.entity.AppsEvent;

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