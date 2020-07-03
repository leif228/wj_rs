package com.wujie.ac.app.business.app.service.system;

import com.wujie.ac.app.business.entity.Node;
import com.wujie.ac.app.business.vo.NodeVo;
import com.wujie.common.base.ApiResult;
import com.wujie.common.dto.ResultVo;

import java.util.List;

public interface UserService {


    ApiResult deviceRegist(Long userId, String deviceSelected, String deviceName, String ip, String port, Long nodeId);


    ApiResult getChildNodes(Long nodeId);

    ApiResult getTreeData(Long nodeId);
}
