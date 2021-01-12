package com.wujie.ac.app.business.repository;


import com.wujie.ac.app.business.entity.BussInfo;
import com.wujie.common.dto.wj.BussInfoDto;

import java.util.List;

public interface BussInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(BussInfo record);

    int insertSelective(BussInfo record);

    BussInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BussInfo record);

    int updateByPrimaryKey(BussInfo record);

    BussInfo findByBussAndCmd(String buss, String cmd);

    List<BussInfoDto> findAll();
}