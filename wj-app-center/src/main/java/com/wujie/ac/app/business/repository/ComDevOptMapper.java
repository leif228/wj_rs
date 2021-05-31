package com.wujie.ac.app.business.repository;


import com.wujie.ac.app.business.entity.ComDevOpt;
import com.wujie.common.dto.wj.ComDevOptDto;

import java.util.List;

public interface ComDevOptMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ComDevOpt record);

    int insertSelective(ComDevOpt record);

    ComDevOpt selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ComDevOpt record);

    int updateByPrimaryKey(ComDevOpt record);

    List<ComDevOptDto> findAll();
}