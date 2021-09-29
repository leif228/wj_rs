package com.wujie.hc.app.business.app.service.system.impl;

import com.wujie.common.base.ApiResult;
import com.wujie.common.dto.DeviceVo;
import com.wujie.common.dto.NodeVo;
import com.wujie.common.dto.wj.DeviceTypeDto;
import com.wujie.common.enums.ErrorEnum;
import com.wujie.common.utils.MD5;
import com.wujie.fclient.service.FileUserService;
import com.wujie.fclient.service.TcpUserService;
import com.wujie.hc.app.business.app.service.system.AuthUserService;
import com.wujie.hc.app.business.app.service.system.UserService;
import com.wujie.hc.app.business.entity.*;
import com.wujie.hc.app.business.repository.*;
import com.wujie.hc.app.business.util.NumConvertUtil;
import com.wujie.hc.app.business.util.date.DateUtil;
import com.wujie.hc.app.business.vo.UserDetailsVo;
import com.wujie.hc.app.framework.auth.util.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import com.wujie.hc.app.business.util.MDA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private DeviceMapper deviceMapper;
    private NodeMapper nodeMapper;
    private NodeStandbyMapper nodeStandbyMapper;
    private WjuserMapper wjuserMapper;
    private AuthUserService authUserService;
    private PasswordEncoder passwordEncoder;
    private JwtTokenUtil jwtTokenUtil;

    private FileUserService fileUserService;
    private WjuserOwerMapper wjuserOwerMapper;

    @Autowired
    public UserServiceImpl(WjuserOwerMapper wjuserOwerMapper,FileUserService fileUserService,NodeStandbyMapper nodeStandbyMapper, NodeMapper nodeMapper, WjuserMapper wjuserMapper, JwtTokenUtil jwtTokenUtil, PasswordEncoder passwordEncoder, AuthUserService authUserService, DeviceMapper deviceMapper) {
        this.wjuserOwerMapper = wjuserOwerMapper;
        this.fileUserService = fileUserService;
        this.nodeStandbyMapper = nodeStandbyMapper;
        this.nodeMapper = nodeMapper;
        this.deviceMapper = deviceMapper;
        this.wjuserMapper = wjuserMapper;
        this.jwtTokenUtil = jwtTokenUtil;
        this.passwordEncoder = passwordEncoder;
        this.authUserService = authUserService;
    }

    @Override
    public ApiResult userLogin(String username, String password) {
        UserDetailsVo userInfoVo = (UserDetailsVo) authUserService.loadUserByUsername(username);
//        if (!passwordEncoder.matches(password, userInfoVo.getPassword())) {
//            return ApiResult.error(ErrorEnum.USERNAME_PASS_ERR);
//        }
        if (!MD5.checkpw(password, userInfoVo.getPassword())) {
            return ApiResult.error(ErrorEnum.USERNAME_PASS_ERR);
        }
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userInfoVo, null, userInfoVo.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtTokenUtil.generateToken(userInfoVo);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("token", token);
        resultMap.put("user", userInfoVo);
        return ApiResult.success(resultMap);
    }

    @Override
    public ApiResult preDeviceRegist(Long userId, String deviceSelected, Long nodeId) {
        Wjuser wjuser = wjuserMapper.selectByPrimaryKey(userId);
        if (wjuser == null)
            return ApiResult.error(ErrorEnum.NOT_USER_ERR);

        //生成泛在网编号
        String fzwNo = "";

        String country = "chn";
        String trade = "0";//通用（互联网）:0，电力：1，军队：2，政府：3
        String area = wjuser.getIdcard().substring(0, 5);
        String timeStr = DateUtil.getDateTime("yyyyMMdd");
        String seqno = "";//序列号表示该国家该地区当天注册的序号，以16进制字符串的形式表现，如FFFF表示65535号
        String catMaxFzwno = deviceMapper.findByFzwnoLikeCAT(country + area + timeStr);
        if (null == catMaxFzwno) {
            seqno = NumConvertUtil.IntToHexStringLimit4(1);
        } else {
            String limitPre = catMaxFzwno.substring(0, 21).substring(17);
            int seqInt = NumConvertUtil.HexStringToInt(limitPre);
            seqno = NumConvertUtil.IntToHexStringLimit4(seqInt + 1);//生成当前序号
            if (seqno == null)
                return ApiResult.error(ErrorEnum.ERR_SEQNO_MAX);
        }
        int userType = wjuser.getUserType();//1表示个人2团体、公司等

        fzwNo = country + trade + area + timeStr + seqno + userType;

        DeviceVo deviceVo = new DeviceVo();
        deviceVo.setIp("");
        deviceVo.setPort("");
        deviceVo.setFzwno(fzwNo);

        //增加下级节点
        if (MDA.numEnum.ZERO.ordinal() == Integer.valueOf(deviceSelected)) {
            NodeStandby preNodeStandby = nodeStandbyMapper.findByNodeAndType(nodeId, MDA.numEnum.ZERO.ordinal());
            deviceVo.setIp(preNodeStandby.getIp());
            deviceVo.setPort(preNodeStandby.getPort());
        }

        log.info("设备注册第一步成功" + fzwNo);

        return ApiResult.success("设备注册第一步成功", deviceVo);

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ApiResult secDeviceRegist(Long userId, String deviceSelected, String deviceName, String ip, String port, Long nodeId, String fzwno) {
        Wjuser wjuser = wjuserMapper.selectByPrimaryKey(userId);
        if (wjuser == null)
            return ApiResult.error(ErrorEnum.NOT_USER_ERR);

        Device device = new Device();
        device.setUserId(userId);
        device.setDeviceType(Integer.valueOf(deviceSelected));
        device.setDeviceName(deviceName);
        device.setIp(ip);
        device.setPort(port);
        device.setCreatTime(DateUtil.getDate());

        device.setFzwno(fzwno);
        deviceMapper.insertSelective(device);

        //增加下级节点
        if (MDA.numEnum.ZERO.ordinal() == Integer.valueOf(deviceSelected)) {
            Node preNode = nodeMapper.selectByPrimaryKey(nodeId);
            NodeStandby preNodeStandby = nodeStandbyMapper.findByNodeAndType(nodeId, MDA.numEnum.ZERO.ordinal());

            nodeMapper.updateRgt(preNode.getRgt());
            nodeMapper.updateLft(preNode.getRgt());

            Node currNode = new Node();
            currNode.setName(deviceName);
            currNode.setLft(preNode.getRgt());
            currNode.setRgt(preNode.getRgt() + 1);
            currNode.setCreatTime(DateUtil.getDate());
            nodeMapper.insertSelective(currNode);

            NodeStandby nodeStandby = new NodeStandby();
            nodeStandby.setNodeId(currNode.getId());
            nodeStandby.setDeviceId(device.getId());
            nodeStandby.setIp(ip);
            nodeStandby.setPort(port);
            nodeStandby.setType(MDA.numEnum.ZERO.ordinal());//默认设置为当前节点的主服务器
            nodeStandby.setCreatTime(DateUtil.getDate());
            nodeStandbyMapper.insertSelective(nodeStandby);
        }

        log.info("设备注册成功");

        return ApiResult.success("设备注册成功");
    }

    @Override
    public ApiResult uploadHead(MultipartFile file, String idcard) {
        try{
            WjuserOwer wjuserOwer = wjuserOwerMapper.findByIdCard(idcard);
            if(wjuserOwer == null)
                return ApiResult.error("找不到用户！！");

            ApiResult result = fileUserService.uploadFile(file);
            String imgUrl = "";
            if(ApiResult.SUCCESS.equals(result.get(ApiResult.RETURNCODE))){
                imgUrl = (String) result.get(ApiResult.MESSAGE);

                log.info("==uploadHeadUrl=="+imgUrl);
                wjuserOwer.setHeadIconUrl(imgUrl);
                wjuserOwerMapper.updateByPrimaryKeySelective(wjuserOwer);

                return ApiResult.success(imgUrl);
            }else {
                return result;
            }
        }catch (Exception e){
            return ApiResult.error(e.getMessage());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ApiResult deviceRegist(Long userId, String deviceSelected, String deviceName, String ip, String port, Long nodeId) {
//        Wjuser wjuser = wjuserMapper.selectByPrimaryKey(userId);
//        if (wjuser == null)
//            return ApiResult.error(ErrorEnum.NOT_USER_ERR);
//
//        Device device = new Device();
//        device.setUserId(userId);
//        device.setDeviceType(Integer.valueOf(deviceSelected));
//        device.setDeviceName(deviceName);
//        device.setIp(ip);
//        device.setPort(port);
//        device.setCreatTime(DateUtil.getDate());
//
//        //生成泛在网编号
//        String fzwNo = "";
//
//        String country = "chn";
//        String area = wjuser.getIdcard().substring(0, 6);
//        String timeStr = DateUtil.getDateTime("yyyyMMdd");
//        String seqno = "";//序列号表示该国家该地区当天注册的序号，以16进制字符串的形式表现，如FFFF表示65535号
//        String catMaxFzwno = deviceMapper.findByFzwnoLikeCAT(country + area + timeStr);
//        if (null == catMaxFzwno) {
//            seqno = NumConvertUtil.IntToHexStringLimit4(1);
//        } else {
//            String limitPre = catMaxFzwno.substring(0, 21).substring(17);
//            int seqInt = NumConvertUtil.HexStringToInt(limitPre);
//            seqno = NumConvertUtil.IntToHexStringLimit4(seqInt + 1);//生成当前序号
//            if (seqno == null)
//                return ApiResult.error(ErrorEnum.ERR_SEQNO_MAX);
//        }
//        int userType = wjuser.getUserType();//1表示个人2团体、公司等
//
//        fzwNo = country + area + timeStr + seqno + userType;
//
//        device.setFzwno(fzwNo);
//        deviceMapper.insertSelective(device);
//
        DeviceVo deviceVo = new DeviceVo();
//        deviceVo.setIp("");
//        deviceVo.setPort("");
//        deviceVo.setFzwno(fzwNo);
//
//        //增加下级节点
//        if (MDA.numEnum.ZERO.ordinal() == Integer.valueOf(deviceSelected)) {
//            Node preNode = nodeMapper.selectByPrimaryKey(nodeId);
//            NodeStandby preNodeStandby = nodeStandbyMapper.findByNodeAndType(nodeId, MDA.numEnum.ZERO.ordinal());
//            deviceVo.setIp(preNodeStandby.getIp());
//            deviceVo.setPort(preNodeStandby.getPort());
//
//            nodeMapper.updateRgt(preNode.getRgt());
//            nodeMapper.updateLft(preNode.getRgt());
//
//            Node currNode = new Node();
//            currNode.setName(deviceName);
//            currNode.setLft(preNode.getRgt());
//            currNode.setRgt(preNode.getRgt() + 1);
//            currNode.setCreatTime(DateUtil.getDate());
//            nodeMapper.insertSelective(currNode);
//
//            NodeStandby nodeStandby = new NodeStandby();
//            nodeStandby.setNodeId(currNode.getId());
//            nodeStandby.setDeviceId(device.getId());
//            nodeStandby.setIp(ip);
//            nodeStandby.setPort(port);
//            nodeStandby.setType(MDA.numEnum.ZERO.ordinal());//默认设置为当前节点的主服务器
//            nodeStandby.setCreatTime(DateUtil.getDate());
//            nodeStandbyMapper.insertSelective(nodeStandby);
//        }
//
//        log.info("设备注册成功" + fzwNo);
//
        return ApiResult.success(deviceVo);
    }

    @Override
    public ApiResult userRegist(String username, String password, String idcard, String phone, String userSelected, Integer pSort, Integer cSort, Integer aSort, Integer sSort) {
        Wjuser wjuser = wjuserMapper.findByUserInfoName(username);
        if (wjuser != null)
            return ApiResult.error(ErrorEnum.PRESENCE_USER_ERR);
        wjuser = wjuserMapper.findByIdCard(idcard);
        if (wjuser != null)
            return ApiResult.error(ErrorEnum.PRESENCE_USER_IDCARD_ERR);

        wjuser = new Wjuser();
        wjuser.setUserName(username);
        wjuser.setPassWord(passwordEncoder.encode(password));
        wjuser.setIdcard(idcard);
        wjuser.setUserType(Integer.valueOf(userSelected));
        wjuser.setPhone(phone);
        wjuser.setCreatTime(DateUtil.getDate());
        wjuser.setPSort(pSort);
        wjuser.setCSort(cSort);
        wjuser.setASort(aSort);
        wjuser.setSSort(sSort);

        wjuserMapper.insertSelective(wjuser);

        UserDetailsVo userDetailsVo = (UserDetailsVo) authUserService.loadUserByUsername(username);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetailsVo, null, userDetailsVo.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtTokenUtil.generateToken(userDetailsVo);

        log.info("用户注册成功");

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("token", token);
        resultMap.put("user", userDetailsVo);
        return ApiResult.success(resultMap);
    }

    @Override
    public ApiResult getChildNodes(Long nodeId) {
        Node node = nodeMapper.selectByPrimaryKey(nodeId);
        List<Node> childs = nodeMapper.getChildNodes(node.getLft(), node.getRgt());

        return ApiResult.success(childs);
    }


}
