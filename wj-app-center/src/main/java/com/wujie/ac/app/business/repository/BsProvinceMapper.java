package com.wujie.ac.app.business.repository;


import com.wujie.ac.app.business.entity.BsProvince;

import java.util.List;

public interface BsProvinceMapper {
    int deleteByPrimaryKey(Integer provinceId);

    int insert(BsProvince record);

    int insertSelective(BsProvince record);

    BsProvince selectByPrimaryKey(Integer provinceId);

    int updateByPrimaryKeySelective(BsProvince record);

    int updateByPrimaryKey(BsProvince record);

    List<BsProvince> findAll();

    BsProvince getPBySort(Integer pSort);
}