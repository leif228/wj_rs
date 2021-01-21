package com.wujie.mc.app.business.repository;

import com.wujie.common.dto.wj.TabsVersionDto;

import java.util.List;

public interface TabsVersionMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TabsVersionDto record);

    int insertSelective(TabsVersionDto record);

    TabsVersionDto selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TabsVersionDto record);

    int updateByPrimaryKey(TabsVersionDto record);

    List<TabsVersionDto> findAll();

    TabsVersionDto findByTabName(String name);
}