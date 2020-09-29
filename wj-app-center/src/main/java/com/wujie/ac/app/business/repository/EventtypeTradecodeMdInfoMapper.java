package com.wujie.ac.app.business.repository;


import com.wujie.ac.app.business.entity.EventtypeTradecodeMdInfo;

import java.util.List;

public interface EventtypeTradecodeMdInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(EventtypeTradecodeMdInfo record);

    int insertSelective(EventtypeTradecodeMdInfo record);

    EventtypeTradecodeMdInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(EventtypeTradecodeMdInfo record);

    int updateByPrimaryKey(EventtypeTradecodeMdInfo record);

    List<EventtypeTradecodeMdInfo> findByEventId(Long eventTypeInfoId);
}