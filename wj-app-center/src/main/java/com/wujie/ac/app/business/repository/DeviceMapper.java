package com.wujie.ac.app.business.repository;


import com.wujie.ac.app.business.entity.Device;
import org.apache.ibatis.annotations.Options;

public interface DeviceMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Device record);

    @Options(useGeneratedKeys = true)
    int insertSelective(Device record);

    Device selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Device record);

    int updateByPrimaryKey(Device record);

    Device findByFzwnoLikeCAT(String cat);
}