package com.wujie.apps.app.business.repository;


import com.wujie.apps.app.business.entity.NodeStandby;

public interface NodeStandbyMapper {
    int deleteByPrimaryKey(Long id);

    int insert(NodeStandby record);

    int insertSelective(NodeStandby record);

    NodeStandby selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(NodeStandby record);

    int updateByPrimaryKey(NodeStandby record);

    NodeStandby findByNodeAndType(Long nodeId, int type);
}