package com.wujie.ac.app.business.app.service.system.impl;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.wujie.ac.app.business.app.service.system.SdsService;
import com.wujie.ac.app.business.entity.*;
import com.wujie.ac.app.business.repository.*;
import com.wujie.ac.app.business.util.date.DateUtil;
import com.wujie.ac.app.framework.util.request.BaseRestfulUtil;
import com.wujie.ac.app.framework.util.spring.SpringContextUtil2;
import com.wujie.common.base.ApiResult;
import com.wujie.common.dto.DeviceVo;
import com.wujie.common.dto.wj.*;
import com.wujie.common.utils.CalendarUtil;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
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
    private FzwnoMapper fzwnoMapper;
    private LoginServerMapper loginServerMapper;
//    private AtServiceImpl atService;

    private static String ststus1 = "产生事件";
    private static String ststus2 = "事件进行中";
    private static String ststus3 = "事件结束";
    private final static int oid_relation_length = 22;

    @Autowired
    public SdsServiceImpl(LoginServerMapper loginServerMapper, FzwnoMapper fzwnoMapper, NodeInfoOwerMapper nodeInfoOwerMapper, SdsEventInfoMapper sdsEventInfoMapper, SdsEventPersonRecordMapper sdsEventPersonRecordMapper, SdsEventRelationMapper sdsEventRelationMapper,
                          SdsEventTypeInfoMapper sdsEventTypeInfoMapper, SdsPercomRelationMapper sdsPercomRelationMapper, SdsRelationTypeInfoMapper sdsRelationTypeInfoMapper,
                          WjuserOwerMapper wjuserOwerMapper) {
//        this.atService = (AtServiceImpl) SpringContextUtil2.getBean("atServiceImpl");
        this.loginServerMapper = loginServerMapper;
        this.fzwnoMapper = fzwnoMapper;
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
    private String genEventNo(String oid, String eventType) {
        Date date = new Date();
        SimpleDateFormat sdformat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String time = sdformat.format(date);
        return oid + "--" + time + "--" + eventType;
    }

    /**
     * 查找事件关系，返回json数组
     */
    private List<SdsPercomRelation> genEventRelation(String oid, String eventType) throws Exception {
        SdsEventTypeInfo sdsEventTypeInfo = sdsEventTypeInfoMapper.selectByPrimaryKey(Long.valueOf(eventType));
        if (sdsEventTypeInfo == null)
            throw new Exception("找不到对应的事件类型信息！");

        //截取oid关系段
        String relation_oid = "";
        if (oid.length() > oid_relation_length)
            relation_oid = oid.substring(0, oid_relation_length);
        else
            relation_oid = oid;

        List<SdsPercomRelation> list = sdsPercomRelationMapper.findBySelfOidAndWeight(relation_oid, sdsEventTypeInfo.getEventRelationLevel());

        return list;

    }

    /**
     * 产生新事件
     */
    @Override
    public ApiResult doGenEvent(String oid, String eventType, String content) {
        try {
            //查找oid归属服务器
            OwerServiceDto owerServiceDto = this.getOwerInfo(oid);

            this.genEventHttp(owerServiceDto.getIp(), oid, eventType, content);

            return ApiResult.success("成功");
        } catch (Exception e) {
            return ApiResult.error(e.getMessage());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ApiResult genEvent(String oid, String eventType, String content) {
        try {
            String eventNo = this.genEventNo(oid, eventType);

            //查找事件关系组织,并保存关系
            SdsEventRelation sdsEventRelation = new SdsEventRelation();
            sdsEventRelation.setEventNo(eventNo);

            List<SdsPercomRelation> list = this.genEventRelation(oid, eventType);
            if (list != null && list.size() > 0) {
//                SdsPercomRelation[] sdsPercomRelations = (SdsPercomRelation[]) list.toArray();
                sdsEventRelation.setEventRelationJson(JSON.toJSONString(list));
                //根据List<SdsPercomRelation>推送事件
                for (SdsPercomRelation sdsPercomRelation : list) {
                    try {
                        OwerServiceDto targetOwer = this.getOwerInfo(sdsPercomRelation.getTargetOid());
                        this.pushEventHttp(targetOwer.getIp(), eventNo, oid, eventType, content, sdsPercomRelation.getTargetOid());
                    } catch (Exception e) {
                        //不处理， continue;
                        log.error("事件推送失败：" + sdsPercomRelation.toString());
                    }
                }
            }

            sdsEventRelation.setStatus(ststus1);
            sdsEventRelation.setUpdateTime(DateUtil.getDate());

            sdsEventRelationMapper.insertSelective(sdsEventRelation);

            //事件发生本地保存事件产生的个人的事件记录
            SdsEventPersonRecord sdsEventPersonRecord = new SdsEventPersonRecord();
            sdsEventPersonRecord.setCreatTime(DateUtil.getDate());
            sdsEventPersonRecord.setEventNo(eventNo);
            sdsEventPersonRecord.setOid(oid);
            sdsEventPersonRecord.setStatus(ststus1);
            sdsEventPersonRecord.setGenOid(oid);
            sdsEventPersonRecord.setEventTypeInfoId(Long.valueOf(eventType));

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

            return ApiResult.success("成功");
        } catch (Exception e) {
            return ApiResult.error(e.getMessage());
        }
    }

    /**
     * 处理事件
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ApiResult doEvent(String oid, String eventType, String content, String eventNo, String genOid) {
        try {
            //判断个人事件是否记录过
            SdsEventPersonRecord sdsEventPersonRecord = sdsEventPersonRecordMapper.findByEventNoAndOid(eventNo, oid);
            if (sdsEventPersonRecord == null) {
                throw new Exception("没有找到事件记录！eventNo=" + eventNo + "（oid=" + oid + ")");
            } else {
                //根据SdsEventPersonRecord的genOid取得产生事件的管理服务器，然后去添加事件流程记录
                OwerServiceDto owerServiceDto = this.getOwerInfo(sdsEventPersonRecord.getGenOid());
                this.pushDoEventWriteHttp(owerServiceDto.getIp(), oid, eventType, content, eventNo);
            }

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
    public ApiResult doEventWrite(String oid, String eventType, String content, String eventNo) {
        try {
            SdsEventInfo sdsEventInfo = new SdsEventInfo();
            sdsEventInfo.setContent(content);
            sdsEventInfo.setCreatTime(DateUtil.getDate());
            sdsEventInfo.setEventNo(eventNo);
            sdsEventInfo.setEventTypeInfoId(Long.valueOf(eventType));
            sdsEventInfo.setOid(oid);
            sdsEventInfo.setStatus(ststus1);

            sdsEventInfoMapper.insertSelective(sdsEventInfo);

            try {
                //在事件产生管理服务器上查找事件产生oid的关系,然后推送到相关管理服务器上
                SdsEventPersonRecord sdsEventPersonRecord = sdsEventPersonRecordMapper.findByEventNo(eventNo);

                List<SdsPercomRelation> list = this.genEventRelation(sdsEventPersonRecord.getGenOid(), eventType);
                if (list != null && list.size() > 0) {
                    for (SdsPercomRelation sdsPercomRelation : list) {
                        try {
                            OwerServiceDto targetOwer = this.getOwerInfo(sdsPercomRelation.getTargetOid());
                            this.pushTaskHttp(targetOwer.getIp(), eventNo, oid, eventType, content, sdsPercomRelation.getTargetOid());
                        } catch (Exception e) {
                            //不处理， continue;
                            log.error("事件推送失败：" + sdsPercomRelation.toString());
                        }
                    }
                }
            } catch (Exception e) {
                //不处理
                log.error("在事件产生管理服务器上查找关系,然后推送到相关管理服务器上，出现错误！");
            }

            return ApiResult.success();
        } catch (Exception e) {
            return ApiResult.error(e.getMessage());
        }
    }

    @Override
//    @Transactional(rollbackFor = Exception.class)
    public ApiResult pushTask(String oid, String eventType, String content, String eventNo, String targetOid) {
        try {
            //根据targetOid查找oidFull
            List<Fzwno> fzwnos = fzwnoMapper.findByRelation(targetOid);
            for (Fzwno fzwno : fzwnos) {
                String oidFull = fzwno.getFzwRelation() + fzwno.getFzwDevice();

                //查找区域服务器然后发送at
                this.searchAreaServiceAndSend(oid, eventType, content, eventNo, oidFull);
            }

            return ApiResult.success();
        } catch (Exception e) {
            return ApiResult.error(e.getMessage());
        }
    }

    //查找区域服务器然后发送at
    private void searchAreaServiceAndSend(String fromOid, String eventType, String content, String eventNo, String toOid) {
        try {
            LoginServer loginServer = loginServerMapper.findByOid(toOid);
            this.searchAreaServiceAndSendHttp(loginServer.getServerIp(), fromOid, eventType, content, eventNo, toOid);
        } catch (Exception e) {
            log.error("查找区域服务器然后发送at,出错了！" + e.getMessage());
        }
    }

    @Override
    public ApiResult areaServiceAndSend(String fromOid, String eventType, String content, String eventNo, String toOid) {
        try {
            //TODO  匹配规则不确定,暂定
            AtServiceImpl atService = (AtServiceImpl) SpringContextUtil2.getBean("atServiceImpl");
            return atService.sendAt("N", fromOid, "0501", "A001", "0001", "0000", fromOid, toOid);

        } catch (Exception e) {
            return ApiResult.error(e.getMessage());
        }
    }

    /**
     * 处理事件
     */
    @Override
//    @Transactional(rollbackFor = Exception.class)
    public ApiResult pushEvent(String oid, String eventType, String content, String eventNo, String targetOid) {
        try {
            //根据targetOid查找oidFull
            List<Fzwno> fzwnos = fzwnoMapper.findByRelation(targetOid);
            for (Fzwno fzwno : fzwnos) {
                String oidFull = fzwno.getFzwRelation() + fzwno.getFzwDevice();
                //判断个人事件是否记录过
                SdsEventPersonRecord sdsEventPersonRecord = sdsEventPersonRecordMapper.findByEventNoAndOid(eventNo, oidFull);
                if (sdsEventPersonRecord == null) {

                    sdsEventPersonRecord = new SdsEventPersonRecord();
                    sdsEventPersonRecord.setCreatTime(DateUtil.getDate());
                    sdsEventPersonRecord.setEventNo(eventNo);
                    sdsEventPersonRecord.setOid(oidFull);
                    sdsEventPersonRecord.setStatus(ststus1);
                    sdsEventPersonRecord.setGenOid(oid);
                    sdsEventPersonRecord.setEventTypeInfoId(Long.valueOf(eventType));

                    sdsEventPersonRecordMapper.insertSelective(sdsEventPersonRecord);

                    //查找区域服务器然后发送at
                    this.searchAreaServiceAndSend(oid, eventType, content, eventNo, oidFull);
                }
            }

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
                BeanUtils.copyProperties(sdsRelationTypeInfo, sdsRelationTypeInfoDto);

                dtos.add(sdsRelationTypeInfoDto);
            }
        } catch (Exception e) {
            return ApiResult.error(e.getMessage());
        }
        return ApiResult.success(dtos);
    }

    //根上查找tooid管理服务器信息
    public OwerServiceDto getOwerInfo(String oid) throws Exception {
        NodeInfoOwer nodeInfoOwer = nodeInfoOwerMapper.selectByPrimaryKey(1l);
        String rootIp = nodeInfoOwer.getRootIp();
        //根上查找tooid管理服务器信息
        OwerServiceDto owerServiceDto = this.seachOwerServiceHttp(rootIp, oid);

        return owerServiceDto;
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
            OwerServiceDto owerServiceDto = this.getOwerInfo(tooid);

            //tooid管理服务器查找tooid用户信息
            WjuserOwerDto wjuserOwerDto = this.seachOwerUserHttp(owerServiceDto.getIp(), tooid);

            //self管理服务器添加关系表
            SdsRelationTypeInfo sdsRelationTypeInfo = sdsRelationTypeInfoMapper.selectByPrimaryKey(Long.valueOf(relationId));
            if (sdsRelationTypeInfo == null)
                return ApiResult.error("关系类型基础数据错误！relationId=" + relationId);

            sdsPercomRelation = new SdsPercomRelation();
            sdsPercomRelation.setRelationTypeInfoId(Long.valueOf(relationId));
            sdsPercomRelation.setSelfOid(oid);
            sdsPercomRelation.setTargetOid(tooid);
            sdsPercomRelation.setTargetOwerIp(owerServiceDto.getIp());
            sdsPercomRelation.setNickname(wjuserOwerDto.getUserName());
            sdsPercomRelation.setWeight(sdsRelationTypeInfo.getWeight());

            sdsPercomRelationMapper.insertSelective(sdsPercomRelation);

            return ApiResult.success();
        } catch (Exception e) {
            return ApiResult.error(e.getMessage());
        }
    }

    private void pushDoEventWriteHttp(String ip, String oid, String eventType, String content, String eventNo) throws Exception {
        String url = "http://" + ip + ":" + "9999/doEventWrite";
        String params = "";
        Map<String, String> map = new HashMap<>();
        map.put("oid", oid);
        map.put("eventType", eventType);
        map.put("content", content);
        map.put("eventNo", eventNo);
        params = new Gson().toJson(map);
        JSONObject jsonObject = BaseRestfulUtil.doPostForJson(url, map);
        if (jsonObject != null) {
            String code = (String) jsonObject.get(ApiResult.RETURNCODE);
            if (ApiResult.SUCCESS.equals(code)) {

                log.info("++++++++++++++++请求doEventWrite成功:");
            } else {
                log.info("++++++++++++++++请求doEventWrite失败:" + "服务端错误：" + jsonObject.get(ApiResult.MESSAGE));
                throw new Exception("doEventWrite失败：服务端错误：" + jsonObject.get(ApiResult.MESSAGE));
            }
        } else {
            log.info("++++++++++++++++请求doEventWrite失败:" + "连接错误");
            throw new Exception("doEventWrite失败:连接错误");
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

    private void searchAreaServiceAndSendHttp(String ip, String fromOid, String eventType, String content, String eventNo, String toOid) throws Exception {
        String url = "http://" + ip + ":" + "9999/areaServiceAndSend";
        String params = "";
        Map<String, String> map = new HashMap<>();
        map.put("fromOid", fromOid);
        map.put("eventType", eventType);
        map.put("content", content);
        map.put("eventNo", eventNo);
        map.put("toOid", toOid);
        params = new Gson().toJson(map);
        JSONObject jsonObject = BaseRestfulUtil.doPostForJson(url, map);
        if (jsonObject != null) {
            String code = (String) jsonObject.get(ApiResult.RETURNCODE);
            if (ApiResult.SUCCESS.equals(code)) {

                log.info("++++++++++++++++请求searchAreaServiceAndSend成功:");
            } else {
                log.info("++++++++++++++++请求searchAreaServiceAndSend失败:" + "服务端错误：" + jsonObject.get(ApiResult.MESSAGE));
                throw new Exception("searchAreaServiceAndSend失败：服务端错误：" + jsonObject.get(ApiResult.MESSAGE));
            }
        } else {
            log.info("++++++++++++++++请求searchAreaServiceAndSend失败:" + "连接错误");
            throw new Exception("searchAreaServiceAndSend失败:连接错误");
        }
    }

    private void pushTaskHttp(String ip, String eventNo, String oid, String eventType, String content, String targetOid) throws Exception {
        String url = "http://" + ip + ":" + "9999/pushTask";
        String params = "";
        Map<String, String> map = new HashMap<>();
        map.put("oid", oid);
        map.put("eventType", eventType);
        map.put("content", content);
        map.put("eventNo", eventNo);
        map.put("targetOid", targetOid);
        params = new Gson().toJson(map);
        JSONObject jsonObject = BaseRestfulUtil.doPostForJson(url, map);
        if (jsonObject != null) {
            String code = (String) jsonObject.get(ApiResult.RETURNCODE);
            if (ApiResult.SUCCESS.equals(code)) {

                log.info("++++++++++++++++请求pushTask成功:");
            } else {
                log.info("++++++++++++++++请求pushTask失败:" + "服务端错误：" + jsonObject.get(ApiResult.MESSAGE));
                throw new Exception("pushTask失败：服务端错误：" + jsonObject.get(ApiResult.MESSAGE));
            }
        } else {
            log.info("++++++++++++++++请求pushTask失败:" + "连接错误");
            throw new Exception("pushTask失败:连接错误");
        }
    }

    private void pushEventHttp(String ip, String eventNo, String oid, String eventType, String content, String targetOid) throws Exception {
        String url = "http://" + ip + ":" + "9999/pushEvent";
        String params = "";
        Map<String, String> map = new HashMap<>();
        map.put("oid", oid);
        map.put("eventType", eventType);
        map.put("content", content);
        map.put("eventNo", eventNo);
        map.put("targetOid", targetOid);
        params = new Gson().toJson(map);
        JSONObject jsonObject = BaseRestfulUtil.doPostForJson(url, map);
        if (jsonObject != null) {
            String code = (String) jsonObject.get(ApiResult.RETURNCODE);
            if (ApiResult.SUCCESS.equals(code)) {

                log.info("++++++++++++++++请求pushDoEvent成功:");
            } else {
                log.info("++++++++++++++++请求pushDoEvent失败:" + "服务端错误：" + jsonObject.get(ApiResult.MESSAGE));
                throw new Exception("pushDoEvent失败：服务端错误：" + jsonObject.get(ApiResult.MESSAGE));
            }
        } else {
            log.info("++++++++++++++++请求pushDoEvent失败:" + "连接错误");
            throw new Exception("pushDoEvent失败:连接错误");
        }
    }

    private List<SdsEventInfoDto> searchEventsHttp(String ip, String eventNo) throws Exception {
        String url = "http://" + ip + ":" + "9999/searchEvents";
        String params = "";
        Map<String, String> map = new HashMap<>();
        map.put("eventNo", eventNo);
        params = new Gson().toJson(map);
        JSONObject jsonObject = BaseRestfulUtil.doPostForJson(url, map);
        if (jsonObject != null) {
            String code = (String) jsonObject.get(ApiResult.RETURNCODE);
            if (ApiResult.SUCCESS.equals(code)) {
                List<SdsEventInfoDto> sdsEventInfoDtos = new ArrayList<>();
                JSONArray datas = (JSONArray) jsonObject.get(ApiResult.CONTENT);
                for (int i = 0; i < datas.size(); i++) {
                    JSONObject jsonObject_i = (JSONObject) datas.get(i);
                    SdsEventInfoDto deviceVo = (SdsEventInfoDto) JSONObject.toBean(jsonObject_i, SdsEventInfoDto.class);
                    deviceVo.setCreatTime(CalendarUtil.parseYYYY_MM_DD_HH_MM_SS((String) jsonObject_i.get("creatTime")).getTime());
                    sdsEventInfoDtos.add(deviceVo);
                }

                log.info("++++++++++++++++请求searchEvents成功:");
                return sdsEventInfoDtos;
            } else {
                log.info("++++++++++++++++请求searchEvents失败:" + "服务端错误：" + jsonObject.get(ApiResult.MESSAGE));
                throw new Exception("searchEvents失败：服务端错误：" + jsonObject.get(ApiResult.MESSAGE));
            }
        } else {
            log.info("++++++++++++++++请求searchEvents失败:" + "连接错误");
            throw new Exception("searchEvents失败:连接错误");
        }
    }

    private WjuserOwerDto searchOwerUserInfoHttp(String ip, String oid) throws Exception {
        String url = "http://" + ip + ":" + "9999/searchOwerUserInfo";
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
                log.info("++++++++++++++++请求searchOwerUserInfo成功:" + data.toString());
                return deviceVo;
            } else {
                log.info("++++++++++++++++请求searchOwerUserInfo失败:" + "服务端错误：" + jsonObject.get(ApiResult.MESSAGE));
                throw new Exception("searchOwerUserInfo失败：服务端错误：" + jsonObject.get(ApiResult.MESSAGE));
            }
        } else {
            log.info("++++++++++++++++请求searchOwerUserInfo失败:" + "连接错误");
            throw new Exception("searchOwerUserInfo失败:连接错误");
        }
    }

    private void genEventHttp(String ip, String oid, String eventType, String content) throws Exception {
        String url = "http://" + ip + ":" + "9999/genEvent";
        String params = "";
        Map<String, String> map = new HashMap<>();
        map.put("oid", oid);
        map.put("eventType", eventType);
        map.put("content", content);
        params = new Gson().toJson(map);
        JSONObject jsonObject = BaseRestfulUtil.doPostForJson(url, map);
        if (jsonObject != null) {
            String code = (String) jsonObject.get(ApiResult.RETURNCODE);
            if (ApiResult.SUCCESS.equals(code)) {
//                JSONObject data = (JSONObject) jsonObject.get(ApiResult.CONTENT);

//                WjuserOwerDto deviceVo = (WjuserOwerDto) JSONObject.toBean(data, WjuserOwerDto.class);
                log.info("++++++++++++++++请求genEvent成功:");
//                return deviceVo;
            } else {
                log.info("++++++++++++++++请求genEvent失败:" + "服务端错误：" + jsonObject.get(ApiResult.MESSAGE));
                throw new Exception("genEvent失败：服务端错误：" + jsonObject.get(ApiResult.MESSAGE));
            }
        } else {
            log.info("++++++++++++++++请求genEvent失败:" + "连接错误");
            throw new Exception("genEvent失败:连接错误");
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
    public ApiResult searchEvents(String eventNo) {
        try {

            List<SdsEventInfoDto> sdsEventInfoDtos = new ArrayList<>();
            List<SdsEventInfo> list = sdsEventInfoMapper.findByEventNo(eventNo);
            for (SdsEventInfo sdsEventInfo : list) {
                SdsEventInfoDto sdsEventInfoDto = new SdsEventInfoDto();
                BeanUtils.copyProperties(sdsEventInfo, sdsEventInfoDto);

                OwerServiceDto owerServiceDto = this.getOwerInfo(sdsEventInfo.getOid());
                WjuserOwerDto wjuserOwerDto = this.searchOwerUserInfoHttp(owerServiceDto.getIp(), sdsEventInfo.getOid());
                if (wjuserOwerDto != null)
                    sdsEventInfoDto.setUserName(wjuserOwerDto.getUserName());

                sdsEventInfoDtos.add(sdsEventInfoDto);
            }

            return ApiResult.success(sdsEventInfoDtos);
        } catch (Exception e) {
            return ApiResult.error(e.getMessage());
        }
    }

    /**
     * 根据oid查找用户信息
     */
    @Override
    public ApiResult searchOwerUserInfo(String oid) {
        try {
            WjuserOwer wjuserOwer = wjuserOwerMapper.findByRelationLikeOid(oid);
            WjuserOwerDto wjuserOwerDto = new WjuserOwerDto();
            BeanUtils.copyProperties(wjuserOwer, wjuserOwerDto);

            return ApiResult.success(wjuserOwerDto);
        } catch (Exception e) {
            return ApiResult.error(e.getMessage());
        }
    }

    /**
     * 事件处理详情
     */
    @Override
    public ApiResult events(String oid, String eventNo) {
        try {
            //根据genoid查找事件发生地服务器
            SdsEventPersonRecord sdsEventPersonRecord = sdsEventPersonRecordMapper.findByEventNoAndOid(eventNo, oid);
            OwerServiceDto owerServiceDto = this.getOwerInfo(sdsEventPersonRecord.getGenOid());
            //取出事件
            List<SdsEventInfoDto> sdsEventInfoDtos = this.searchEventsHttp(owerServiceDto.getIp(), eventNo);

            return ApiResult.success(sdsEventInfoDtos);
        } catch (Exception e) {
            return ApiResult.error(e.getMessage());
        }
    }


}
