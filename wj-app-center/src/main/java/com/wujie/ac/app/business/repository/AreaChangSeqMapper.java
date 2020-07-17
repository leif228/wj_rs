package com.wujie.ac.app.business.repository;


import com.wujie.ac.app.business.entity.AreaChangSeq;

public interface AreaChangSeqMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AreaChangSeq record);

    int insertSelective(AreaChangSeq record);

    AreaChangSeq selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AreaChangSeq record);

    int updateByPrimaryKey(AreaChangSeq record);
}