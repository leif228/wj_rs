package com.wujie.apps.app.business.app.service.system.impl;

import com.wujie.apps.app.business.app.service.system.UserService;
import com.wujie.apps.app.business.entity.AppsEvent;
import com.wujie.apps.app.business.entity.AppsUser;
import com.wujie.apps.app.business.repository.DeviceMapper;
import com.wujie.apps.app.business.util.WechatConstant;
import com.wujie.apps.netty.client.TcpClient;
import com.wujie.apps.netty.server.ChannelManager;
import com.wujie.apps.netty.utils.FileUtils;
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

    private com.wujie.apps.app.business.repository.DeviceMapper deviceMapper;
    private com.wujie.apps.app.business.repository.NodeMapper nodeMapper;
    private com.wujie.apps.app.business.repository.NodeStandbyMapper nodeStandbyMapper;
    private com.wujie.apps.app.business.repository.WjuserMapper wjuserMapper;
    private WechatConstant wechatConstant;
    private ChannelManager channelManager;
    private com.wujie.apps.app.business.repository.AppsUserMapper appsUserMapper;
    private com.wujie.apps.app.business.repository.AppsEventMapper appsEventMapper;
//    private AppUserService appUserService;

    @Autowired
    public UserServiceImpl(com.wujie.apps.app.business.repository.AppsEventMapper appsEventMapper, com.wujie.apps.app.business.repository.AppsUserMapper appsUserMapper, ChannelManager channelManager, WechatConstant wechatConstant, com.wujie.apps.app.business.repository.NodeStandbyMapper nodeStandbyMapper, com.wujie.apps.app.business.repository.NodeMapper nodeMapper, com.wujie.apps.app.business.repository.WjuserMapper wjuserMapper, DeviceMapper deviceMapper) {
//        this.appUserService = appUserService;
        this.appsEventMapper = appsEventMapper;
        this.appsUserMapper = appsUserMapper;
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
    public ApiResult addUser(String oid) {
        try {
            AppsUser appsUser = appsUserMapper.findByOid(oid);
            if (appsUser == null) {
                appsUser = new AppsUser();
                appsUser.setCreatTime(DateUtils.getNowDate());
                appsUser.setOid(oid);

                appsUserMapper.insertSelective(appsUser);
            } else {
                throw new Exception("已经添加过oid=" + oid);
            }
            return ApiResult.success();
        } catch (Exception e) {
            return ApiResult.error(e.getMessage());
        }
    }

    @Override
    public ApiResult users() {
        try {
            List<AppsUser> list = appsUserMapper.findAll();

            return ApiResult.success(list);
        } catch (Exception e) {
            return ApiResult.error(e.getMessage());
        }
    }

    @Override
    public ApiResult delUser(Long id) {
        try {
            appsUserMapper.deleteByPrimaryKey(id);

            return ApiResult.success();
        } catch (Exception e) {
            return ApiResult.error(e.getMessage());
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

    @Override
    public ApiResult addEvent(String event) {
        try {
            AppsEvent appsEvent = new AppsEvent();
            appsEvent.setEvent(event);

            appsEventMapper.insertSelective(appsEvent);

            return ApiResult.success();
        } catch (Exception e) {
            return ApiResult.error(e.getMessage());
        }
    }

    @Override
    public ApiResult events() {
        try {
            List<AppsEvent> list = appsEventMapper.findAll();

            return ApiResult.success(list);
        } catch (Exception e) {
            return ApiResult.error(e.getMessage());
        }
    }

    @Override
    public ApiResult delEvent(Long id) {
        try {
            appsEventMapper.deleteByPrimaryKey(id);

            return ApiResult.success();
        } catch (Exception e) {
            return ApiResult.error(e.getMessage());
        }
    }

}
