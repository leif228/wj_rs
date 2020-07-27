package com.wujie.hc.app.business.repository;


import com.wujie.hc.app.business.entity.Wjuser;
import org.apache.ibatis.annotations.Options;

public interface WjuserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Wjuser record);

    @Options(useGeneratedKeys = true)
    int insertSelective(Wjuser record);

    Wjuser selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Wjuser record);

    int updateByPrimaryKey(Wjuser record);

    Wjuser findByUserInfoName(String username);

    Wjuser findByIdCard(String idcard);
}