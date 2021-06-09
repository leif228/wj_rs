package com.wujie.tc.app.business.app.service.system.impl;

import com.wujie.common.base.ApiResult;
import com.wujie.common.enums.ErrorEnum;
import com.wujie.fclient.service.AppUserService;
import com.wujie.tc.app.business.app.service.system.UserService;
import com.wujie.tc.app.business.repository.DeviceMapper;
import com.wujie.tc.app.business.repository.NodeMapper;
import com.wujie.tc.app.business.repository.NodeStandbyMapper;
import com.wujie.tc.app.business.repository.WjuserMapper;
import com.wujie.tc.app.business.util.WechatConstant;
import com.wujie.tc.netty.client.TcpClient;
import com.wujie.tc.netty.pojo.AtTask;
import com.wujie.tc.netty.protocol.WjProtocol;
import com.wujie.tc.netty.server.ChannelManager;
import com.wujie.tc.netty.server.send.Sen_1000_0000;
import com.wujie.tc.netty.server.send.Sen_factory;
import com.wujie.tc.netty.utils.FileUtils;
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
    private AppUserService appUserService;

    @Autowired
    public UserServiceImpl(AppUserService appUserService, ChannelManager channelManager, WechatConstant wechatConstant, NodeStandbyMapper nodeStandbyMapper, NodeMapper nodeMapper, WjuserMapper wjuserMapper, DeviceMapper deviceMapper) {
        this.appUserService = appUserService;
        this.channelManager = channelManager;
        this.nodeStandbyMapper = nodeStandbyMapper;
        this.wechatConstant = wechatConstant;
        this.nodeMapper = nodeMapper;
        this.deviceMapper = deviceMapper;
        this.wjuserMapper = wjuserMapper;
    }

    @Override
    public ApiResult tcpClientConnect(String ip, String port, String fzwno, String deviceName, String selfIp, String selfPort, String deviceSelected) {
        ApiResult apiResult = appUserService.recodeOwerNodeInfo(deviceSelected,deviceName,selfIp,selfPort,fzwno);
        if(!apiResult.get(ApiResult.RETURNCODE).equals(ApiResult.SUCCESS))
            return apiResult;

        Map<String, String> keyValueMap = new HashMap<>();
        keyValueMap.put("ip", ip);
        keyValueMap.put("port", port);
        keyValueMap.put("fzwno", fzwno);
        log.info("===============================写cconfig.properties成功！ip:" + ip + ":port" + port);
        boolean issuccess = FileUtils.updatePropertiess(wechatConstant.getTcpClientConfigPath(), keyValueMap);
        if (issuccess) {
//            TcpClient.closeConnect();
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
    public ApiResult sendAtTask(String oid, String at) {

        log.error("tcp服务接收到sendAtTask.at="+at);
        Map<String, Channel> map = channelManager.deviceChannels;
        Channel channel = map.get(oid);
        if(channel != null){
            AtTask atTask = new AtTask();
            atTask.setAt(at);

            WjProtocol wjProtocol = Sen_factory.getInstance(Sen_1000_0000.main, Sen_1000_0000.sub,atTask);
            if(wjProtocol == null)
                return ApiResult.error("发送失败！服务端错误！oid="+oid);

            channel.write(wjProtocol);
            channel.flush();

            log.error("+++++++++++++++++sendAtTask成功oid="+oid);
            return ApiResult.success("sendAtTask成功oid="+oid);
        }else{
            log.error("+++++++++++++++++sendAtTask失败！用户不在线！oid="+oid);
            return ApiResult.error("发送失败！用户不在线！oid="+oid);
        }

    }

}
