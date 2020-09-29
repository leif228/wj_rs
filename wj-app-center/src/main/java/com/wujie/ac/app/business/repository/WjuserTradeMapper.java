package com.wujie.ac.app.business.repository;


import com.wujie.ac.app.business.entity.WjuserTrade;

import java.util.List;

public interface WjuserTradeMapper {
    int deleteByPrimaryKey(Long id);

    int insert(WjuserTrade record);

    int insertSelective(WjuserTrade record);

    WjuserTrade selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(WjuserTrade record);

    int updateByPrimaryKey(WjuserTrade record);

    WjuserTrade findByOid(String relation);

    List<WjuserTrade> findByLikeTrades(String tradeCodeInfoId);
}