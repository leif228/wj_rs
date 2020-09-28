package com.wujie.ac.app.business.repository;


import com.wujie.common.dto.wj.TradeCodeInfo;

import java.util.List;

public interface TradeCodeInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TradeCodeInfo record);

    int insertSelective(TradeCodeInfo record);

    TradeCodeInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TradeCodeInfo record);

    int updateByPrimaryKey(TradeCodeInfo record);

    List<TradeCodeInfo> getClass1st();

    List<TradeCodeInfo> getClass2nd(String class1st);

    List<TradeCodeInfo> getClass3rd(String class1st,String class2nd);

    List<TradeCodeInfo> getClass4th(String class1st,String class3rd);
}