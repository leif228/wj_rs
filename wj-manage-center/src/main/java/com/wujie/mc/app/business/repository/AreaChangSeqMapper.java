package com.wujie.mc.app.business.repository;


import com.wujie.common.dto.wj.AreaChangSeqDto;
import com.wujie.mc.app.business.entity.AreaChangSeq;

import java.util.List;

public interface AreaChangSeqMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AreaChangSeq record);

    int insertSelective(AreaChangSeq record);

    AreaChangSeq selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AreaChangSeq record);

    int updateByPrimaryKey(AreaChangSeq record);

    AreaChangSeq sortByFzwaddr(String fzwStr);

    List<AreaChangSeq> acsAll();

    List<AreaChangSeqDto> findAll();

}