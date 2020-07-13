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
import com.wujie.tc.netty.server.ChannelManager;
import com.wujie.tc.netty.utils.FileUtils;
import io.netty.channel.Channel;
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
    private ChannelManager channelManager;

    @Autowired
    public UserServiceImpl(ChannelManager channelManager, WechatConstant wechatConstant, NodeStandbyMapper nodeStandbyMapper, NodeMapper nodeMapper, WjuserMapper wjuserMapper, DeviceMapper deviceMapper) {
        this.channelManager = channelManager;
        this.nodeStandbyMapper = nodeStandbyMapper;
        this.wechatConstant = wechatConstant;
        this.nodeMapper = nodeMapper;
        this.deviceMapper = deviceMapper;
        this.wjuserMapper = wjuserMapper;
    }

    @Override
    public ApiResult tcpClientConnect(String ip, String port, String fzwno) {
        Map<String, String> keyValueMap = new HashMap<>();
        keyValueMap.put("ip", ip);
        keyValueMap.put("port", port);
        keyValueMap.put("fzwno", fzwno);
        log.info("===============================ip:" + ip + ":port" + port);
        boolean issuccess = FileUtils.updatePropertiess(wechatConstant.getTcpClientConfigPath(), keyValueMap);
        if (issuccess) {
            boolean isconnect = TcpClient.startTcpClient(wechatConstant);
            if (isconnect) {
                log.info("===============================连接tcp服务器成功：" + ip + ":" + port);
                return ApiResult.success();
            } else {
                log.info("===============================连接tcp服务器失败：" + ip + ":" + port);
                return ApiResult.error(ErrorEnum.ERR_CONNECTTCP_NOT);
            }
        } else {
            log.info("===============================写cconfig.properties失败！");
            return ApiResult.error(ErrorEnum.ERR_CCONFIGWRITE_NOT);
        }
    }

    @Override
    public ApiResult getTcpClientConnectInfo() {
        String fzwnos = "";

        Map<String, Channel> map = channelManager.deviceChannels;
        for (String key : map.keySet()) {
            fzwnos = fzwnos + key + ",";
        }
        if (!"".equals(fzwnos))
            fzwnos = fzwnos.substring(0, fzwnos.length() - 1);
        return ApiResult.success(fzwnos);
    }

}
