package com.wujie.ac.app.business.repository;


import com.wujie.ac.app.business.entity.LoginServer;

public interface LoginServerMapper {
    int deleteByPrimaryKey(Long id);

    int insert(LoginServer record);

    int insertSelective(LoginServer record);

    LoginServer selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(LoginServer record);

    int updateByPrimaryKey(LoginServer record);

    LoginServer findLastByOid(String toOid);
}