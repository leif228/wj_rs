package com.wujie.ac.app.business.repository;


import com.wujie.ac.app.business.entity.Fzwno;

public interface FzwnoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Fzwno record);

    int insertSelective(Fzwno record);

    Fzwno selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Fzwno record);

    int updateByPrimaryKey(Fzwno record);

    Fzwno findMax(String fzwno, Integer devtypeId);
}