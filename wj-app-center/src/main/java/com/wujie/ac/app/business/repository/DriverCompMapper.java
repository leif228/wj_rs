package com.wujie.ac.app.business.repository;


import com.wujie.ac.app.business.entity.DriverComp;

import java.util.List;

public interface DriverCompMapper {
    int deleteByPrimaryKey(Long id);

    int insert(DriverComp record);

    int insertSelective(DriverComp record);

    DriverComp selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(DriverComp record);

    int updateByPrimaryKey(DriverComp record);

    List<DriverComp> findAll();
}