package com.wujie.tc.app.business.app.service.system;

import com.wujie.common.base.ApiResult;
import com.wujie.tc.app.business.entity.Node;
import com.wujie.tc.app.business.vo.NodeVo;
import com.wujie.tc.app.business.vo.ResultVo;

import java.util.List;

public interface UserService {

    ApiResult getTreeData(Long nodeId);
}
