package com.wujie.ac.app.business.repository;


import com.wujie.ac.app.business.entity.NodeInfoOwer;

public interface NodeInfoOwerMapper {
    int deleteByPrimaryKey(Long id);

    int insert(NodeInfoOwer record);

    int insertSelective(NodeInfoOwer record);

    NodeInfoOwer selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(NodeInfoOwer record);

    int updateByPrimaryKey(NodeInfoOwer record);
}