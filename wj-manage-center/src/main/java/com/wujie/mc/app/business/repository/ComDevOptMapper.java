package com.wujie.mc.app.business.repository;


import com.wujie.mc.app.business.entity.ComDevOpt;

public interface ComDevOptMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ComDevOpt record);

    int insertSelective(ComDevOpt record);

    ComDevOpt selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ComDevOpt record);

    int updateByPrimaryKey(ComDevOpt record);
}