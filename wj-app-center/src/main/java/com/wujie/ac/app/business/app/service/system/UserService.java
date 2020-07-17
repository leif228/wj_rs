package com.wujie.ac.app.business.app.service.system;

import com.wujie.ac.app.business.entity.Node;
import com.wujie.ac.app.business.vo.NodeVo;
import com.wujie.common.base.ApiResult;
import com.wujie.common.dto.ResultVo;

import java.util.List;

public interface UserService {

    ApiResult getTreeData(Long nodeId);

    ApiResult preDeviceRegist(Long userId, String deviceSelected, Long nodeId, Integer pSort, Integer cSort, Integer aSort, Integer sSort);

    ApiResult secDeviceRegist(Long userId, String deviceSelected, String deviceName, String ip, String port, Long nodeId, String fzwno);

    ApiResult getChildNodes(Long nodeId);
}
