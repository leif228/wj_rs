package com.wujie.ac.app.business.app.service.system.impl;

import com.google.gson.Gson;
import com.wujie.ac.app.business.app.service.system.SdsService;
import com.wujie.ac.app.business.entity.*;
import com.wujie.ac.app.business.repository.*;
import com.wujie.ac.app.business.util.date.DateUtil;
import com.wujie.ac.app.framework.util.request.BaseRestfulUtil;
import com.wujie.common.base.ApiResult;
import com.wujie.common.dto.DeviceVo;
import com.wujie.common.dto.wj.*;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Slf4j
public class SdsServiceImpl implements SdsService {

    private SdsEventInfoMapper sdsEventInfoMapper;
    private SdsEventPersonRecordMapper sdsEventPersonRecordMapper;
    private SdsEventRelationMapper sdsEventRelationMapper;
    private SdsEventTypeInfoMapper sdsEventTypeInfoMapper;
    private SdsPercomRelationMapper sdsPercomRelationMapper;
    private SdsRelationTypeInfoMapper sdsRelationTypeInfoMapper;
    private WjuserOwerMapper wjuserOwerMapper;
    private NodeInfoOwerMapper nodeInfoOwerMapper;

    private static String ststus1 = "产生事件";
    private static String ststus2 = "事件进行中";
    private static String ststus3 = "事件结束";

    @Autowired
    public SdsServiceImpl(NodeInfoOwerMapper nodeInfoOwerMapper, SdsEventInfoMapper sdsEventInfoMapper, SdsEventPersonRecordMapper sdsEventPersonRecordMapper, SdsEventRelationMapper sdsEventRelationMapper,
                          SdsEventTypeInfoMapper sdsEventTypeInfoMapper, SdsPercomRelationMapper sdsPercomRelationMapper, SdsRelationTypeInfoMapper sdsRelationTypeInfoMapper,
                          WjuserOwerMapper wjuserOwerMapper) {
        this.nodeInfoOwerMapper = nodeInfoOwerMapper;
        this.sdsEventInfoMapper = sdsEventInfoMapper;
        this.sdsEventPersonRecordMapper = sdsEventPersonRecordMapper;
        this.sdsEventRelationMapper = sdsEventRelationMapper;
        this.sdsEventTypeInfoMapper = sdsEventTypeInfoMapper;
        this.sdsPercomRelationMapper = sdsPercomRelationMapper;
        this.sdsRelationTypeInfoMapper = sdsRelationTypeInfoMapper;
        this.wjuserOwerMapper = wjuserOwerMapper;
    }

    /**
     * 产生事件号
     */
    private String genEventNo(String eventType) {
        Date date = new Date();
        SimpleDateFormat sdformat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String id = sdformat.format(date);
        return id + "-" + eventType;
    }

    /**
     * 查找事件关系，返回json数组
     */
    private String genEventRelation(String oid, String eventType) throws Exception {
        SdsEventTypeInfo sdsEventTypeInfo = sdsEventTypeInfoMapper.selectByPrimaryKey(Long.valueOf(eventType));
        if (sdsEventTypeInfo == null)
            throw new Exception("找不到对应的事件类型信息！");

        List<WjuserOwer> wjuserOwerList = new ArrayList<>();
        List<SdsPercomRelation> list = sdsPercomRelationMapper.findBySelfOidAndWeight(oid, sdsEventTypeInfo.getEventRelationLevel());
        for (SdsPercomRelation sdsPercomRelation : list) {
            WjuserOwer wjuserOwer = wjuserOwerMapper.findByOid(sdsPercomRelation.getTargetOid());
            wjuserOwerList.add(wjuserOwer);
        }

        WjuserOwer[] wjuserOwers = (WjuserOwer[]) wjuserOwerList.toArray();

        return new Gson().toJson(wjuserOwers);
    }

    /**
     * 产生新事件
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ApiResult genEvent(String oid, String eventType, String content) {
        try {
            String eventNo = this.genEventNo(eventType);

            //查找事件关系组织
            SdsEventRelation sdsEventRelation = new SdsEventRelation();
            sdsEventRelation.setEventNo(eventNo);
            sdsEventRelation.setEventRelationJson(this.genEventRelation(oid, eventType));
            sdsEventRelation.setStatus(ststus1);
            sdsEventRelation.setUpdateTime(DateUtil.getDate());

            sdsEventRelationMapper.insertSelective(sdsEventRelation);

            //个人事件记录
            SdsEventPersonRecord sdsEventPersonRecord = new SdsEventPersonRecord();
            sdsEventPersonRecord.setCreatTime(DateUtil.getDate());
            sdsEventPersonRecord.setEventNo(eventNo);
            sdsEventPersonRecord.setOid(oid);
            sdsEventPersonRecord.setStatus(ststus1);

            sdsEventPersonRecordMapper.insertSelective(sdsEventPersonRecord);

            //事件流程记录
            SdsEventInfo sdsEventInfo = new SdsEventInfo();
            sdsEventInfo.setContent(content);
            sdsEventInfo.setCreatTime(DateUtil.getDate());
            sdsEventInfo.setEventNo(eventNo);
            sdsEventInfo.setEventTypeInfoId(Long.valueOf(eventType));
            sdsEventInfo.setOid(oid);
            sdsEventInfo.setStatus(ststus1);

            sdsEventInfoMapper.insertSelective(sdsEventInfo);

            return ApiResult.success();
        } catch (Exception e) {
            return ApiResult.error(e.getMessage());
        }
    }

    /**
     * 处理事件
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ApiResult doEvent(String oid, String eventType, String content, String eventNo) {
        try {

            //判断个人事件是否记录过
            SdsEventPersonRecord sdsEventPersonRecord = sdsEventPersonRecordMapper.findByEventNoAndOid(eventNo, oid);
            if (sdsEventPersonRecord == null) {

                sdsEventPersonRecord = new SdsEventPersonRecord();
                sdsEventPersonRecord.setCreatTime(DateUtil.getDate());
                sdsEventPersonRecord.setEventNo(eventNo);
                sdsEventPersonRecord.setOid(oid);
                sdsEventPersonRecord.setStatus(ststus1);

                sdsEventPersonRecordMapper.insertSelective(sdsEventPersonRecord);
            }

            //事件流程记录
            SdsEventInfo sdsEventInfo = new SdsEventInfo();
            sdsEventInfo.setContent(content);
            sdsEventInfo.setCreatTime(DateUtil.getDate());
            sdsEventInfo.setEventNo(eventNo);
            sdsEventInfo.setEventTypeInfoId(Long.valueOf(eventType));
            sdsEventInfo.setOid(oid);
            sdsEventInfo.setStatus(ststus1);

            sdsEventInfoMapper.insertSelective(sdsEventInfo);

            return ApiResult.success();
        } catch (Exception e) {
            return ApiResult.error(e.getMessage());
        }
    }

    @Override
    public ApiResult myUserList(String oid) {
        List<UserListDto> users = new ArrayList<>();
        try {
            List<SdsPercomRelation> sdsPercomRelations = sdsPercomRelationMapper.findBySelfOid(oid);
            for (SdsPercomRelation sdsPercomRelation : sdsPercomRelations) {
                UserListDto userListDto = new UserListDto();
                userListDto.setUserName(sdsPercomRelation.getNickname());

                SdsRelationTypeInfo sdsRelationTypeInfo = sdsRelationTypeInfoMapper.selectByPrimaryKey(sdsPercomRelation.getRelationTypeInfoId());
                userListDto.setRelation(sdsRelationTypeInfo.getRelationName());

                users.add(userListDto);
            }
        } catch (Exception e) {
            return ApiResult.error(e.getMessage());
        }
        return ApiResult.success(users);
    }

    @Override
    public ApiResult getRelationTypes() {
        List<SdsRelationTypeInfoDto> dtos = new ArrayList<>();
        try {
            List<SdsRelationTypeInfo> sdsRelationTypeInfos = sdsRelationTypeInfoMapper.findAll();
            for (SdsRelationTypeInfo sdsRelationTypeInfo : sdsRelationTypeInfos) {
                SdsRelationTypeInfoDto sdsRelationTypeInfoDto = new SdsRelationTypeInfoDto();
                BeanUtils.copyProperties(sdsRelationTypeInfo,sdsRelationTypeInfoDto);

                dtos.add(sdsRelationTypeInfoDto);
            }
        } catch (Exception e) {
            return ApiResult.error(e.getMessage());
        }
        return ApiResult.success(dtos);
    }

    /**
     * 添加关系
     */
    @Override
//    @Transactional(rollbackFor = Exception.class)
    public ApiResult addUser(String oid, String relationId, String tooid) {
        try {
            //判断是否已经添加过
            SdsPercomRelation sdsPercomRelation = sdsPercomRelationMapper.findByOidAndTargetOid(oid, tooid);
            if (sdsPercomRelation != null)
                return ApiResult.error("已经添加过了！不能重复添加！");

            //根据tooid获取管理服务器ip
            NodeInfoOwer nodeInfoOwer = nodeInfoOwerMapper.selectByPrimaryKey(1l);
            String rootIp = nodeInfoOwer.getRootIp();
            OwerServiceDto owerServiceDto = this.seachOwerServiceHttp(rootIp, tooid);

            //tooid管理服务器查找tooid
            WjuserOwerDto wjuserOwerDto = this.seachOwerUserHttp(owerServiceDto.getIp(), tooid);

            //self管理服务器添加关系表
            SdsRelationTypeInfo sdsRelationTypeInfo = sdsRelationTypeInfoMapper.selectByPrimaryKey(Long.valueOf(relationId));
            if (sdsRelationTypeInfo == null)
                return ApiResult.error("关系类型基础数据错误！relationId=" + relationId);

            sdsPercomRelation = new SdsPercomRelation();
            sdsPercomRelation.setRelationTypeInfoId(Long.valueOf(relationId));
            sdsPercomRelation.setSelfOid(oid);
            sdsPercomRelation.setTargetOid(tooid);
            sdsPercomRelation.setTargetOwerIp(wjuserOwerDto.getOwerIp());
            sdsPercomRelation.setNickname(wjuserOwerDto.getUserName());
            sdsPercomRelation.setWeight(sdsRelationTypeInfo.getWeight());

            sdsPercomRelationMapper.insertSelective(sdsPercomRelation);

            return ApiResult.success();
        } catch (Exception e) {
            return ApiResult.error(e.getMessage());
        }
    }

    private WjuserOwerDto seachOwerUserHttp(String ip, String oid) throws Exception {
        String url = "http://" + ip + ":" + "9999/seachOwerUser";
        String params = "";
        Map<String, String> map = new HashMap<>();
        map.put("oid", oid);
        params = new Gson().toJson(map);
        JSONObject jsonObject = BaseRestfulUtil.doPostForJson(url, map);
        if (jsonObject != null) {
            String code = (String) jsonObject.get(ApiResult.RETURNCODE);
            if (ApiResult.SUCCESS.equals(code)) {
                JSONObject data = (JSONObject) jsonObject.get(ApiResult.CONTENT);

                WjuserOwerDto deviceVo = (WjuserOwerDto) JSONObject.toBean(data, WjuserOwerDto.class);
                log.info("++++++++++++++++请求seachOwerUser成功:" + data.toString());
                return deviceVo;
            } else {
                log.info("++++++++++++++++请求seachOwerUser失败:" + "服务端错误：" + jsonObject.get(ApiResult.MESSAGE));
                throw new Exception("seachOwerUser失败：服务端错误：" + jsonObject.get(ApiResult.MESSAGE));
            }
        } else {
            log.info("++++++++++++++++请求seachOwerUser失败:" + "连接错误");
            throw new Exception("seachOwerUser失败:连接错误");
        }
    }

    private OwerServiceDto seachOwerServiceHttp(String rootIp, String oid) throws Exception {
        String url = "http://" + rootIp + ":" + "9999/seachOwerService";
        String params = "";
        Map<String, String> map = new HashMap<>();
        map.put("oid", oid);
        params = new Gson().toJson(map);
        JSONObject jsonObject = BaseRestfulUtil.doPostForJson(url, map);
        if (jsonObject != null) {
            String code = (String) jsonObject.get(ApiResult.RETURNCODE);
            if (ApiResult.SUCCESS.equals(code)) {
                JSONObject data = (JSONObject) jsonObject.get(ApiResult.CONTENT);

                OwerServiceDto deviceVo = (OwerServiceDto) JSONObject.toBean(data, OwerServiceDto.class);
                log.info("++++++++++++++++请求seachOwerService成功:" + data.toString());
                return deviceVo;
            } else {
                log.info("++++++++++++++++请求seachOwerService失败:" + "服务端错误：" + jsonObject.get(ApiResult.MESSAGE));
                throw new Exception("seachOwerService失败：服务端错误：" + jsonObject.get(ApiResult.MESSAGE));
            }
        } else {
            log.info("++++++++++++++++请求seachOwerService失败:" + "连接错误");
            throw new Exception("seachOwerService失败:连接错误");
        }
    }

    /**
     * 我的事件列表
     */
    @Override
    public ApiResult myEventList(String oid) {
        try {

            List<SdsEventListDto> list = sdsEventPersonRecordMapper.findByOid(oid);

            return ApiResult.success(list);
        } catch (Exception e) {
            return ApiResult.error(e.getMessage());
        }
    }

    /**
     * 事件处理详情
     */
    @Override
    public ApiResult events(String eventNo) {
        try {
            List<SdsEventInfoDto> sdsEventInfoDtos = new ArrayList<>();
            List<SdsEventInfo> list = sdsEventInfoMapper.findByEventNo(eventNo);
            for (SdsEventInfo sdsEventInfo : list) {
                SdsEventInfoDto sdsEventInfoDto = new SdsEventInfoDto();
                BeanUtils.copyProperties(sdsEventInfo, sdsEventInfoDto);

                WjuserOwer wjuserOwer = wjuserOwerMapper.findByOid(sdsEventInfo.getOid());
                sdsEventInfoDto.setUserName(wjuserOwer.getUserName());

                sdsEventInfoDtos.add(sdsEventInfoDto);
            }

            return ApiResult.success(sdsEventInfoDtos);
        } catch (Exception e) {
            return ApiResult.error(e.getMessage());
        }
    }

}
