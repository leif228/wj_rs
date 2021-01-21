package com.wujie.mc.app.business.repository;


import com.wujie.common.dto.wj.DevtypeDto;
import com.wujie.mc.app.business.entity.Devtype;

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