package com.wujie.ac.app.business.repository;


import com.wujie.ac.app.business.entity.EventtypeBussMdInfo;

import java.util.List;

public interface EventtypeBussMdInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(EventtypeBussMdInfo record);

    int insertSelective(EventtypeBussMdInfo record);

    EventtypeBussMdInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(EventtypeBussMdInfo record);

    int updateByPrimaryKey(EventtypeBussMdInfo record);

    List<EventtypeBussMdInfo> findbyBussId(Long bussId);
}