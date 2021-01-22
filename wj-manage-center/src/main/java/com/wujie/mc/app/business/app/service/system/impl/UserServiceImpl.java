package com.wujie.mc.app.business.app.service.system.impl;

import com.alibaba.fastjson.JSONObject;
import com.wujie.common.base.ApiResult;
import com.wujie.common.dto.DeviceVo;
import com.wujie.common.dto.NodeVo;
import com.wujie.common.dto.wj.*;
import com.wujie.common.enums.ErrorEnum;
import com.wujie.common.enums.WjBaseTableCode;
import com.wujie.common.utils.MD5;
import com.wujie.fclient.service.TcpUserService;
import com.wujie.mc.app.business.app.service.system.AuthUserService;
import com.wujie.mc.app.business.app.service.system.UserService;
import com.wujie.mc.app.business.entity.*;
import com.wujie.mc.app.business.repository.*;
import com.wujie.mc.app.business.util.NumConvertUtil;
import com.wujie.mc.app.business.util.date.DateUtil;
import com.wujie.mc.app.business.vo.UserDetailsVo;
import com.wujie.mc.app.framework.auth.util.JwtTokenUtil;
import com.wujie.mc.app.business.repository.DeviceMapper;
import lombok.extern.slf4j.Slf4j;
import com.wujie.mc.app.business.util.MDA;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private TabsVersionMapper tabsVersionMapper;
    private AreaChangSeqMapper areaChangSeqMapper;
    private DevtypeMapper devtypeMapper;
    private BussInfoMapper bussInfoMapper;

    private WjuserMapper wjuserMapper;
    private AuthUserService authUserService;
    private PasswordEncoder passwordEncoder;
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    public UserServiceImpl(TabsVersionMapper tabsVersionMapper, NodeStandbyMapper nodeStandbyMapper, NodeMapper nodeMapper, WjuserMapper wjuserMapper, JwtTokenUtil jwtTokenUtil, PasswordEncoder passwordEncoder, AuthUserService authUserService, DeviceMapper deviceMapper) {
        this.tabsVersionMapper = tabsVersionMapper;
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
    public ApiResult getBaseTabType() {
        List<Map<String, Object>> maps = WjBaseTableCode.getMaps();
        return ApiResult.success(maps);
    }

    @Override
    public ApiResult updateTabByType(String name, String jsonObject) {
        try {
            WjBaseTableCode wjBaseTableCode = WjBaseTableCode.valueOf(name);
            switch (wjBaseTableCode) {
                case area_chang_seq:
                    AreaChangSeqDto areaChangSeqDto = JSONObject.parseObject(jsonObject, AreaChangSeqDto.class);

                    AreaChangSeq areaChangSeq = areaChangSeqMapper.selectByPrimaryKey(areaChangSeqDto.getId());
                    if (areaChangSeq == null) {
                        areaChangSeq = new AreaChangSeq();
                        BeanUtils.copyProperties(areaChangSeqDto, areaChangSeq);

                        areaChangSeq.setId(null);

                        areaChangSeqMapper.insertSelective(areaChangSeq);
                    } else {
                        areaChangSeq = new AreaChangSeq();
                        BeanUtils.copyProperties(areaChangSeqDto, areaChangSeq);

                        areaChangSeqMapper.updateByPrimaryKey(areaChangSeq);
                    }
                case devtype:
                    DevtypeDto devtypeDto = JSONObject.parseObject(jsonObject, DevtypeDto.class);

                    Devtype devtype = devtypeMapper.selectByPrimaryKey(devtypeDto.getId());
                    if (devtype == null) {
                        devtype = new Devtype();
                        BeanUtils.copyProperties(devtypeDto, devtype);

                        devtype.setId(null);

                        devtypeMapper.insertSelective(devtype);
                    } else {
                        devtype = new Devtype();
                        BeanUtils.copyProperties(devtypeDto, devtype);

                        devtypeMapper.updateByPrimaryKey(devtype);
                    }
                case buss_info:
                    BussInfoDto bussInfoDto = JSONObject.parseObject(jsonObject, BussInfoDto.class);

                    BussInfo bussInfo = bussInfoMapper.selectByPrimaryKey(bussInfoDto.getId());
                    if (bussInfo == null) {
                        bussInfo = new BussInfo();
                        BeanUtils.copyProperties(bussInfoDto, bussInfo);

                        bussInfo.setId(null);

                        bussInfoMapper.insertSelective(bussInfo);
                    } else {
                        bussInfo = new BussInfo();
                        BeanUtils.copyProperties(bussInfoDto, bussInfo);

                        bussInfoMapper.updateByPrimaryKey(bussInfo);
                    }
            }

            TabsVersionDto tabsVersionDto = tabsVersionMapper.findByTabName(wjBaseTableCode.name());
            tabsVersionDto.setVersion(tabsVersionDto.getVersion() + 1);
            tabsVersionMapper.updateByPrimaryKeySelective(tabsVersionDto);

            return ApiResult.success();
        } catch (Exception e) {
            log.debug("updateTabByType_报错了:" + e.getMessage());
            return ApiResult.error(e.getMessage());
        }
    }


}
