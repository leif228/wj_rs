package com.wujie.ac.app.business.app.service.system.impl;

import com.wujie.ac.app.business.app.service.system.TradeDataService;
import com.wujie.ac.app.business.repository.TradeCodeInfoMapper;
import com.wujie.common.dto.wj.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class TradeDataServiceImpl implements TradeDataService {

    private TradeCodeInfoMapper tradeCodeInfoMapper;

    @Autowired
    public TradeDataServiceImpl(TradeCodeInfoMapper tradeCodeInfoMapper) {
        this.tradeCodeInfoMapper = tradeCodeInfoMapper;
    }


    @Override
    public List<TradeCodeInfo> getClass1st() {
        return tradeCodeInfoMapper.getClass1st();
    }

    @Override
    public List<TradeCodeInfo> getClass2nd(String class1st) {
        return tradeCodeInfoMapper.getClass2nd(class1st);
    }

    @Override
    public List<TradeCodeInfo> getClass3rd(String class1st, String class2nd) {
        return tradeCodeInfoMapper.getClass3rd(class1st,class2nd);
    }

    @Override
    public List<TradeCodeInfo> getClass4th(String class1st, String class3rd) {
        return tradeCodeInfoMapper.getClass4th(class1st,class3rd);
    }

    @Override
    public TradeCodeInfo getById(Long id) {
        return tradeCodeInfoMapper.selectByPrimaryKey(id);
    }

}
