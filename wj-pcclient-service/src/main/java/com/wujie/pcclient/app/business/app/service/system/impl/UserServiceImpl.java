package com.wujie.pcclient.app.business.app.service.system.impl;

import com.wujie.pcclient.app.business.app.service.system.UserService;
import com.wujie.pcclient.app.business.entity.AppsEvent;
import com.wujie.pcclient.app.business.entity.AppsUser;
import com.wujie.pcclient.app.business.util.WechatConstant;
import com.wujie.pcclient.netty.client.TcpClient;
import com.wujie.pcclient.netty.server.ChannelManager;
import com.wujie.pcclient.netty.utils.FileUtils;
import com.wujie.common.base.ApiResult;
import com.wujie.common.enums.ErrorEnum;
import com.wujie.common.utils.DateUtils;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private WechatConstant wechatConstant;
    private ChannelManager channelManager;

    @Autowired
    public UserServiceImpl(WechatConstant wechatConstant,ChannelManager channelManager) {
        this.channelManager = channelManager;
        this.wechatConstant = wechatConstant;
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

        log.error("tcp服务接收到sendAtTask.at=" + at);
        int result = TcpClient.sendAtTask(at);
        if (result == 0) {

            log.error("+++++++++++++++++sendAtTask成功at=" + at);
            return ApiResult.success("成功发送at=" + at);
        } else if (result == 1) {
            log.error("+++++++++++++++++sendAtTask发送失败！用户不在线！at=" + at);
            return ApiResult.error("发送失败！用户不在线！at=" + at);
        } else {
            log.error("+++++++++++++++++sendAtTask发送失败！其它错误！at=" + at);
            return ApiResult.error("发送失败！其它错误！at=" + at);
        }

    }



    @Override
    public ApiResult serverInfo() {
        try {
            Properties properties = FileUtils.readFile(wechatConstant.getTcpClientConfigPath());

            String fzwno = properties.getProperty("fzwno");

            return ApiResult.success(fzwno);
        } catch (Exception e) {
            return ApiResult.error(e.getMessage());
        }
    }


}
