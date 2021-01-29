package com.wujie.ac.app.business.repository;


import com.wujie.ac.app.business.entity.BsStreet;

import java.util.List;

public interface BsStreetMapper {
    int deleteByPrimaryKey(Integer streetId);

    int insert(BsStreet record);

    int insertSelective(BsStreet record);

    BsStreet selectByPrimaryKey(Integer streetId);

    int updateByPrimaryKeySelective(BsStreet record);

    int updateByPrimaryKey(BsStreet record);

    List<BsStreet> findByA(String areaCode);

    List<BsStreet> findAll();

}