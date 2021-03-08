package com.wujie.apps.app.business.app.service.system.impl;

import com.wujie.apps.app.business.app.service.system.UserService;
import com.wujie.apps.app.business.repository.DeviceMapper;
import com.wujie.apps.app.business.repository.NodeMapper;
import com.wujie.apps.app.business.repository.NodeStandbyMapper;
import com.wujie.apps.app.business.repository.WjuserMapper;
import com.wujie.apps.app.business.util.WechatConstant;
import com.wujie.apps.netty.client.TcpClient;
import com.wujie.apps.netty.server.ChannelManager;
import com.wujie.apps.netty.utils.FileUtils;
import com.wujie.common.base.ApiResult;
import com.wujie.common.enums.ErrorEnum;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private DeviceMapper deviceMapper;
    private NodeMapper nodeMapper;
    private NodeStandbyMapper nodeStandbyMapper;
    private WjuserMapper wjuserMapper;
    private WechatConstant wechatConstant;
    private ChannelManager channelManager;
//    private AppUserService appUserService;

    @Autowired
    public UserServiceImpl( ChannelManager channelManager, WechatConstant wechatConstant, NodeStandbyMapper nodeStandbyMapper, NodeMapper nodeMapper, WjuserMapper wjuserMapper, DeviceMapper deviceMapper) {
//        this.appUserService = appUserService;
        this.channelManager = channelManager;
        this.nodeStandbyMapper = nodeStandbyMapper;
        this.wechatConstant = wechatConstant;
        this.nodeMapper = nodeMapper;
        this.deviceMapper = deviceMapper;
        this.wjuserMapper = wjuserMapper;
    }

    @Override
    public ApiResult tcpClientConnect(String ip, String port, String fzwno) {
//        ApiResult apiResult = appUserService.recodeOwerNodeInfo(deviceSelected,deviceName,selfIp,selfPort,fzwno);
//        if(!apiResult.get(ApiResult.RETURNCODE).equals(ApiResult.SUCCESS))
//            return apiResult;

        Map<String, String> keyValueMap = new HashMap<>();
        keyValueMap.put("ip", ip);
        keyValueMap.put("port", port);
        keyValueMap.put("fzwno", fzwno);
        log.info("===============================写cconfig.properties成功！ip:" + ip + ":port" + port);
        boolean issuccess = FileUtils.updatePropertiess(wechatConstant.getTcpClientConfigPath(), keyValueMap);
        if (issuccess) {
            TcpClient.startTcpClient(wechatConstant);

            return ApiResult.success("写配置文件成功");
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

    @Override
    public ApiResult sendAtTask(String at) {

        log.error("tcp服务接收到sendAtTask.at="+at);
        int result = TcpClient.sendAtTask(at);
        if(result == 0){

            log.error("+++++++++++++++++sendAtTask成功at="+at);
            return ApiResult.success("成功发送at="+at);
        }else if(result ==1){
            log.error("+++++++++++++++++sendAtTask发送失败！用户不在线！at="+at);
            return ApiResult.error("发送失败！用户不在线！at="+at);
        }else {
            log.error("+++++++++++++++++sendAtTask发送失败！其它错误！at="+at);
            return ApiResult.error("发送失败！其它错误！at="+at);
        }

    }

}
