package com.wujie.ac.app.business.repository;


import com.wujie.ac.app.business.entity.BsArea;

import java.util.List;

public interface BsAreaMapper {
    int deleteByPrimaryKey(Integer areaId);

    int insert(BsArea record);

    int insertSelective(BsArea record);

    BsArea selectByPrimaryKey(Integer areaId);

    int updateByPrimaryKeySelective(BsArea record);

    int updateByPrimaryKey(BsArea record);

    List<BsArea> findByC(String cityCode);

    List<BsArea> findAll();

}