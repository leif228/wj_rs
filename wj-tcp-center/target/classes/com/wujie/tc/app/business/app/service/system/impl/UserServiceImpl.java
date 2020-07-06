package com.wujie.tc.app.business.app.service.system.impl;

import com.wujie.common.base.ApiResult;
import com.wujie.common.dto.DeviceVo;
import com.wujie.common.dto.NodeVo;
import com.wujie.common.enums.ErrorEnum;
import com.wujie.tc.app.business.app.service.system.UserService;
import com.wujie.tc.app.business.entity.Device;
import com.wujie.tc.app.business.entity.Node;
import com.wujie.tc.app.business.entity.NodeStandby;
import com.wujie.tc.app.business.entity.Wjuser;
import com.wujie.tc.app.business.repository.DeviceMapper;
import com.wujie.tc.app.business.repository.NodeMapper;
import com.wujie.tc.app.business.repository.NodeStandbyMapper;
import com.wujie.tc.app.business.repository.WjuserMapper;
import com.wujie.tc.app.business.util.MDA;
import com.wujie.tc.app.business.util.NumConvertUtil;
import com.wujie.tc.app.business.util.WechatConstant;
import com.wujie.tc.app.business.util.date.DateUtil;
import com.wujie.tc.netty.client.TcpClient;
import com.wujie.tc.netty.utils.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private DeviceMapper deviceMapper;
    private NodeMapper nodeMapper;
    private NodeStandbyMapper nodeStandbyMapper;
    private WjuserMapper wjuserMapper;
    private WechatConstant wechatConstant;

    @Autowired
    public UserServiceImpl(WechatConstant wechatConstant,NodeStandbyMapper nodeStandbyMapper, NodeMapper nodeMapper, WjuserMapper wjuserMapper, DeviceMapper deviceMapper) {
        this.nodeStandbyMapper = nodeStandbyMapper;
        this.wechatConstant = wechatConstant;
        this.nodeMapper = nodeMapper;
        this.deviceMapper = deviceMapper;
        this.wjuserMapper = wjuserMapper;
    }

    @Override
    public ApiResult getTreeData(Long nodeId) {
        NodeVo nodeVo = new NodeVo();
        List<NodeVo> list = nodeMapper.getAllChildNodeVosLayer();
        if (list.size() > 0) {
            Map<Integer, List<NodeVo>> map = list.stream().collect(Collectors.groupingBy(NodeVo::getLayer));
            int mapSize = map.size();
            if (mapSize > 0) {
                int firstLayer = 1;
                for (int i = firstLayer; i <= mapSize; i++) {
                    List<NodeVo> list0 = map.get(i);
                    int j = i + 1;
                    if (j > mapSize)
                        break;
                    List<NodeVo> list1 = map.get(j);
                    for (NodeVo parent : list0) {
                        if (!parent.getName().contains(":"))
                            parent.setName(parent.getName() + "(" + parent.getIp() + ":" + parent.getPort() + ")");
                        for (NodeVo child : list1) {
                            if (parent.getRgt() > child.getRgt() && parent.getLft() < child.getLft()) {
                                parent.getChildren().add(child);
                                if (!child.getName().contains(":"))
                                    child.setName(child.getName() + "(" + child.getIp() + ":" + child.getPort() + ")");
                            }
                        }
                    }
                }
                nodeVo = map.get(firstLayer).get(0);
                log.debug(nodeVo.toString());
            }
        }

        return ApiResult.success(nodeVo);
    }

    @Override
    public ApiResult tcpClientConnect(String ip, String port, String fzwno) {
        Map<String, String> keyValueMap = new HashMap<>();
        keyValueMap.put("ip", ip);
        keyValueMap.put("port", port);
        keyValueMap.put("fzwno", fzwno);
        log.debug("===============================ip:"+ip+":port"+port);
        boolean issuccess = FileUtils.updatePropertiess(wechatConstant.getTcpClientConfigPath(), keyValueMap);
        if (issuccess) {
            boolean isconnect = TcpClient.startTcpClient(wechatConstant);
            if (isconnect)
                return ApiResult.success();
            else{
                log.debug("===============================连接tcp服务器失败：" + ip + ":" + port);
                return ApiResult.error(ErrorEnum.ERR_CONNECTTCP_NOT);
            }
        } else {
            log.debug("===============================写cconfig.properties失败！");
            return ApiResult.error(ErrorEnum.ERR_CCONFIGWRITE_NOT);
        }
    }

}
