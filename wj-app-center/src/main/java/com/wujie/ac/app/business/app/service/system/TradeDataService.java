package com.wujie.ac.app.business.app.service.system;

import com.wujie.common.dto.wj.TradeCodeInfo;

import java.util.List;

public interface TradeDataService {

    List<TradeCodeInfo> getClass1st();

    List<TradeCodeInfo> getClass2nd(String class1st);

    List<TradeCodeInfo> getClass3rd(String class1st,String class2nd);

    List<TradeCodeInfo> getClass4th(String class1st,String class3rd);

    TradeCodeInfo getById(Long id);

}
