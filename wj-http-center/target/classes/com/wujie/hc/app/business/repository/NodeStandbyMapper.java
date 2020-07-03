package com.wujie.hc.app.business.repository;


import com.wujie.hc.app.business.entity.NodeStandby;
import org.apache.ibatis.annotations.Options;

public interface NodeStandbyMapper {
    int deleteByPrimaryKey(Long id);

    int insert(NodeStandby record);

    @Options(useGeneratedKeys = true)
    int insertSelective(NodeStandby record);

    NodeStandby selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(NodeStandby record);

    int updateByPrimaryKey(NodeStandby record);

    NodeStandby findByNodeAndType(Long nodeId, int type);
}