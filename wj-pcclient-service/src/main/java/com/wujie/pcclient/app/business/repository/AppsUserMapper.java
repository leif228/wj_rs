package com.wujie.pcclient.app.business.repository;


import com.wujie.pcclient.app.business.entity.AppsUser;

import java.util.List;

public interface AppsUserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(AppsUser record);

    int insertSelective(AppsUser record);

    AppsUser selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(AppsUser record);

    int updateByPrimaryKey(AppsUser record);

    AppsUser findByOid(String oid);

    List<AppsUser> findAll();

}