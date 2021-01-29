package com.wujie.ac.app.business.repository;


import com.wujie.ac.app.business.entity.BsCity;

import java.util.List;

public interface BsCityMapper {
    int deleteByPrimaryKey(Integer cityId);

    int insert(BsCity record);

    int insertSelective(BsCity record);

    BsCity selectByPrimaryKey(Integer cityId);

    int updateByPrimaryKeySelective(BsCity record);

    int updateByPrimaryKey(BsCity record);

    List<BsCity> findByP(String provinceCode);

    List<BsCity> findAll();

}