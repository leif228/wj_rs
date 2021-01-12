package com.wujie.ac.app.business.repository;


import com.wujie.ac.app.business.entity.Devtype;
import com.wujie.common.dto.wj.DevtypeDto;

import java.util.List;

public interface DevtypeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Devtype record);

    int insertSelective(Devtype record);

    Devtype selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Devtype record);

    int updateByPrimaryKey(Devtype record);

    List<DevtypeDto> findAll();

    List<Devtype> getManageService(int id);

    List<Devtype> findMElse();
}