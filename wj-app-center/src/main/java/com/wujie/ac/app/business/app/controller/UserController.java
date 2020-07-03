package com.wujie.ac.app.business.app.controller;

import com.wujie.ac.app.business.app.service.system.UserService;
import com.wujie.ac.app.business.entity.Node;
import com.wujie.ac.app.business.vo.NodeVo;
import com.wujie.common.base.ApiResult;
import com.wujie.common.dto.ResultVo;
import com.wujie.common.enums.ErrorEnum;
import com.wujie.fclient.service.HttpUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName: UserController
 * @Description: 用户控制层
 * @Date: 2020/4/21 14:27
 */
@RestController
public class UserController {

    private UserService userService;
    private HttpUserService httpUserService;

    @Autowired
    public UserController(UserService userService,HttpUserService httpUserService) {
        this.userService = userService;
        this.httpUserService = httpUserService;
    }

    @PostMapping("/deviceRegist")
    public ApiResult deviceRegist(@RequestParam(value = "userId") Long userId,
                                  @RequestParam(value = "deviceSelected") String deviceSelected,
                                  @RequestParam(value = "deviceName") String deviceName,
                                  @RequestParam(value = "ip") String ip,
                                  @RequestParam(value = "port") String port,
                                  @RequestParam(value = "nodeId") Long nodeId) {
        try {
            return userService.deviceRegist(userId, deviceSelected, deviceName, ip, port, nodeId);
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResult.error(ErrorEnum.ERR_ADD_NODE);
        }
    }

    @PostMapping("/getChildNodes")
    public ApiResult getChildNodes(@RequestParam(value = "nodeId") Long nodeId) {
        return httpUserService.getChildNodes(nodeId);
    }

    @PostMapping("/getTreeData")
    public ApiResult getTreeData(@RequestParam(value = "nodeId") Long nodeId) {
        return userService.getTreeData(nodeId);
    }


}
