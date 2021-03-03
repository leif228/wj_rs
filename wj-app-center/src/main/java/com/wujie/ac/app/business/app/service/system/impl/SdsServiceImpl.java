package com.wujie.ac.app.business.app.service.system.impl;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.wujie.ac.app.async.AsyncManager;
import com.wujie.ac.app.async.factory.AsyncFactory;
import com.wujie.ac.app.business.app.service.system.SdsService;
import com.wujie.ac.app.business.entity.*;
import com.wujie.ac.app.business.entity.at.ClubUserManageAtParam;
import com.wujie.ac.app.business.entity.at.ManageChatMsgAtParam;
import com.wujie.ac.app.business.enums.ClubUserManageTypeEnum;
import com.wujie.ac.app.business.repository.*;
import com.wujie.ac.app.business.util.date.DateUtil;
import com.wujie.ac.app.framework.util.request.BaseRestfulUtil;
import com.wujie.ac.app.framework.util.spring.SpringContextUtil2;
import com.wujie.common.base.ApiResult;
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
    private BussInfoMapper bussInfoMapper;
    private EventtypeBussMdInfoMapper eventtypeBussMdInfoMapper;
    private EventtypeTradecodeMdInfoMapper eventtypeTradecodeMdInfoMapper;
    private WjuserTradeMapper wjuserTradeMapper;
//    private AtServiceImpl atService;

    private static String ststus1 = "产生事件";
    private static String ststus2 = "事件进行中";
    private static String ststus3 = "事件结束";
    private final static int oid_relation_length = 22;

    @Autowired
    public SdsServiceImpl(WjuserTradeMapper wjuserTradeMapper, EventtypeTradecodeMdInfoMapper eventtypeTradecodeMdInfoMapper, EventtypeBussMdInfoMapper eventtypeBussMdInfoMapper, BussInfoMapper bussInfoMapper, LoginServerMapper loginServerMapper, FzwnoMapper fzwnoMapper, NodeInfoOwerMapper nodeInfoOwerMapper, SdsEventInfoMapper sdsEventInfoMapper, SdsEventPersonRecordMapper sdsEventPersonRecordMapper, SdsEventRelationMapper sdsEventRelationMapper,
                          SdsEventTypeInfoMapper sdsEventTypeInfoMapper, SdsPercomRelationMapper sdsPercomRelationMapper, SdsRelationTypeInfoMapper sdsRelationTypeInfoMapper,
                          WjuserOwerMapper wjuserOwerMapper) {
        this.wjuserTradeMapper = wjuserTradeMapper;
        this.eventtypeTradecodeMdInfoMapper = eventtypeTradecodeMdInfoMapper;
        this.eventtypeBussMdInfoMapper = eventtypeBussMdInfoMapper;
        this.bussInfoMapper = bussInfoMapper;
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
    public ApiResult doGenEvent(String oid, String eventType, String content, String bussInfoId) {
        try {
            //查找oid归属服务器
//            OwerServiceDto owerServiceDto = this.getOwerInfo(oid);
//            this.genEventHttp(owerServiceDto.getIp(), oid, eventType, content, bussInfoId);

//            return ApiResult.success("成功");

            return this.genEvent(oid, eventType, content, bussInfoId);

        } catch (Exception e) {
            return ApiResult.error(e.getMessage());
        }
    }

    @Override
//    @Transactional(rollbackFor = Exception.class)
    public ApiResult genEvent(String oid, String eventType, String content, String bussInfoId) {
        try {
            List<String> relations = new ArrayList<>();
            String eventNo = this.genEventNo(oid, eventType);

            //事件发生本地保存事件产生的个人的事件记录
//            SdsEventPersonRecord sdsEventPersonRecord = new SdsEventPersonRecord();
//            sdsEventPersonRecord.setCreatTime(DateUtil.getDate());
//            sdsEventPersonRecord.setEventNo(eventNo);
//            sdsEventPersonRecord.setOid(oid);
//            sdsEventPersonRecord.setStatus(ststus1);
//            sdsEventPersonRecord.setGenOid(oid);
//            sdsEventPersonRecord.setEventTypeInfoId(Long.valueOf(eventType));
//
//            sdsEventPersonRecordMapper.insertSelective(sdsEventPersonRecord);

            //推送给同一用户下其它设备
            String oid_relation = oid.substring(0, oid_relation_length);
            if (!relations.contains(oid_relation)) {
                relations.add(oid_relation);
                this.pushEvent(oid, eventType, content, eventNo, oid_relation, bussInfoId);
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

            //查找事件关系组织,并保存关系
            SdsEventRelation sdsEventRelation = new SdsEventRelation();
            sdsEventRelation.setEventNo(eventNo);

            List<SdsPercomRelation> list = this.genEventRelation(oid, eventType);
            if (list != null && list.size() > 0) {
                sdsEventRelation.setEventRelationJson(JSON.toJSONString(list));
            }

            sdsEventRelation.setStatus(ststus1);
            sdsEventRelation.setUpdateTime(DateUtil.getDate());

//            //在事件产生的管理服务器上，处理与管理服务器上的行业相关的任务推送
//            String targetOids_m = this.doTradeTaskAtManageSev(eventNo, oid, eventType, content, bussInfoId);
//            sdsEventRelation.setEventTradeOids(targetOids_m);

//            处理与行业相关的事件
            String targetOids = this.doTradeTask(eventNo, oid, eventType, content, bussInfoId);
            sdsEventRelation.setEventTradeOids(targetOids);

            sdsEventRelationMapper.insertSelective(sdsEventRelation);

            //处理行业的推送
            if (!targetOids.equals("")) {
                try {
                    if (targetOids.contains(",")) {
                        String[] arr = targetOids.split(",");
                        for (int i = 0; i < arr.length; i++) {
                            String target_relation = arr[i].substring(0, oid_relation_length);
                            if (!relations.contains(target_relation)) {
                                relations.add(target_relation);
                                OwerServiceDto targetOwer = this.getOwerInfo(arr[i]);
                                AsyncManager.me().execute(AsyncFactory.pushEventHttp(targetOwer.getIp(), eventNo, oid, eventType, content, target_relation, bussInfoId));
                            } else {
                                continue;
                            }
                        }
                    } else {
                        String target_relation = targetOids.substring(0, oid_relation_length);
                        if (!relations.contains(target_relation)) {
                            relations.add(target_relation);
                            OwerServiceDto targetOwer = this.getOwerInfo(targetOids);
                            AsyncManager.me().execute(AsyncFactory.pushEventHttp(targetOwer.getIp(), eventNo, oid, eventType, content, target_relation, bussInfoId));
                        }
                    }
                } catch (Exception e) {
                    //不处理， continue;
                    log.error("处理行业的推送事件推送失败：" + targetOids);
                }
            }

            //根据关系推送事件
            if (list != null && list.size() > 0) {
                for (SdsPercomRelation sdsPercomRelation : list) {
                    try {
                        if (!relations.contains(sdsPercomRelation.getTargetOid())) {
                            relations.add(sdsPercomRelation.getTargetOid());
                            OwerServiceDto targetOwer = this.getOwerInfo(sdsPercomRelation.getTargetOid());
//                        this.pushEventHttp(targetOwer.getIp(), eventNo, oid, eventType, content, sdsPercomRelation.getTargetOid(), bussInfoId);
                            //异步
                            AsyncManager.me().execute(AsyncFactory.pushEventHttp(targetOwer.getIp(), eventNo, oid, eventType, content, sdsPercomRelation.getTargetOid(), bussInfoId));
                        } else {
                            continue;
                        }


                    } catch (Exception e) {
                        //不处理， continue;
                        log.error("事件推送失败：" + sdsPercomRelation.toString());
                    }
                }
            }

            return ApiResult.success(eventNo);
        } catch (Exception e) {
            return ApiResult.error(e.getMessage());
        }
    }

    @Override
    public ApiResult updataSdsEventRelation(String eventNo, String targetOids) {
        try {
            //处理管理服务器与区域服务器是同一台时，oid重复问题
            SdsEventRelation sdsEventRelation = sdsEventRelationMapper.findByEventNo(eventNo);
            if (sdsEventRelation != null) {
                Set<String> set = new HashSet();

                String targetOids_m = sdsEventRelation.getEventTradeOids();
                if (targetOids_m != null && !targetOids_m.equals("")) {
                    if (targetOids_m.contains(",")) {
                        String[] arr = targetOids_m.split(",");
                        for (int i = 0; i < arr.length; i++) {
                            set.add(arr[i]);
                        }
                    } else {
                        set.add(targetOids_m);
                    }
                }

                if (targetOids != null && !targetOids.equals("")) {
                    if (targetOids.contains(",")) {
                        String[] arr = targetOids.split(",");
                        for (int i = 0; i < arr.length; i++) {
                            set.add(arr[i]);
                        }
                    } else {
                        set.add(targetOids);
                    }
                }
                String oids = "";

                for (String str : set) {
                    oids += str + ",";
                }
                if (!oids.equals("")) {
                    oids = oids.substring(0, oids.length() - 1);
                }
                sdsEventRelation.setEventTradeOids(oids);

                sdsEventRelationMapper.updateByPrimaryKeySelective(sdsEventRelation);
            }

            return ApiResult.success();
        } catch (Exception e) {
            return ApiResult.error(e.getMessage());
        }
    }

    //处理与行业相关的事件
    private String doTradeTask(String eventNo, String oid, String eventType, String content, String bussInfoId) {
        String targetOids = "";
        try {

            //在事件产生的管理服务器上，处理与管理服务器上的行业相关的任务推送
            String targetOids_m = this.doTradeTaskAtManageSev(eventNo, oid, eventType, content, bussInfoId);
            //在事件产生的管理服务器上，查找事件产生者所在的区域服务器，处理与区域服务器上的行业相关的任务推送
            String targetOids_a = this.doTradeTaskAtAreaSev(eventNo, oid, eventType, content, bussInfoId);

//        SdsEventRelation sdsEventRelation = sdsEventRelationMapper.findByEventNo(eventNo);
//        if (sdsEventRelation != null) {

            //处理管理服务器与区域服务器是同一台时，oid重复问题
            Set<String> set = new HashSet();
            if (!targetOids_m.equals("")) {
                if (targetOids_m.contains(",")) {
                    String[] arr = targetOids_m.split(",");
                    for (int i = 0; i < arr.length; i++) {
                        set.add(arr[i]);
                    }
                } else {
                    set.add(targetOids_m);
                }
            }
            if (!targetOids_a.equals("")) {
                if (targetOids_a.contains(",")) {
                    String[] arr = targetOids_a.split(",");
                    for (int i = 0; i < arr.length; i++) {
                        set.add(arr[i]);
                    }
                } else {
                    set.add(targetOids_a);
                }
            }
            for (String str : set) {
                targetOids += str + ",";
            }
            if (!targetOids.equals("")) {
                targetOids = targetOids.substring(0, targetOids.length() - 1);
            }
//            sdsEventRelation.setEventTradeOids(targetOids);
//
//            sdsEventRelationMapper.updateByPrimaryKeySelective(sdsEventRelation);
//        }
            return targetOids;
        } catch (Exception e) {
            log.error("sdsService.doTradeTask_报错了：" + e.getMessage());
            return targetOids;
        }

    }

    //在事件产生的管理服务器上，处理与行业相关的任务推送
    private String doTradeTaskAtManageSev(String eventNo, String oid, String eventType, String content, String bussInfoId) {
        try {
            List<EventtypeBussMdInfo> eventtypeBussMdInfos = eventtypeBussMdInfoMapper.findbyBussId(Long.valueOf(bussInfoId));
            if (eventtypeBussMdInfos.size() == 0)
                throw new Exception("业务基础表找不到数据！");

            //TODO　后续根据算法实现匹配
            EventtypeBussMdInfo targInfo = eventtypeBussMdInfos.get(0);

            String targetOids = "";

            List<EventtypeTradecodeMdInfo> eventtypeTradecodeMdInfos = eventtypeTradecodeMdInfoMapper.findByEventId(targInfo.getEventTypeInfoId());
            //去除一个行业用户下有两个设备，互相推送的bug
//            List<String> relations = new ArrayList<>();
            for (EventtypeTradecodeMdInfo eventtypeTradecodeMdInfo : eventtypeTradecodeMdInfos) {
                List<WjuserTrade> wjuserTrades = wjuserTradeMapper.findByLikeTrades(eventtypeTradecodeMdInfo.getTradeCodeInfoId() + "");

                log.error("3333333：" + wjuserTrades.size());
                for (WjuserTrade wjuserTrade : wjuserTrades) {
//                    try {
                    //去除事件产生者又是行业相关的oid
//                        if (oid.equals(wjuserTrade.getOid()))
//                            continue;

//                        OwerServiceDto targetOwer = this.getOwerInfo(wjuserTrade.getOid());
                    String relation = wjuserTrade.getOid().substring(0, oid_relation_length);
                    targetOids += wjuserTrade.getOid() + ",";

                    //去除一个行业用户下有两个设备，互相推送的bug
//                        if (relations.contains(relation)) {
//                            continue;
//                        } else {
//                            relations.add(relation);

                    log.error("44444444：" + relation);
//                        this.pushEventHttp(targetOwer.getIp(), eventNo, oid, eventType, content, relation, bussInfoId);
                    //异步
//                            AsyncManager.me().execute(AsyncFactory.pushEventHttp(targetOwer.getIp(), eventNo, oid, eventType, content, relation, bussInfoId));
//                        }

//                    } catch (Exception e) {
//                        //不处理， continue;
//
//                        log.error("事件推送失败：" + wjuserTrade.getOid().toString());
//                    }
                }
            }

            if (!targetOids.equals("")) {
                targetOids = targetOids.substring(0, targetOids.length() - 1);
            }

            return targetOids;

        } catch (Exception e) {
            log.error("sdsService.doTradeTaskAtManageSev_报错了：" + e.getMessage());
            return "";
        }
    }

    //在事件产生的管理服务器上，查找事件产生者所在的区域服务器，处理与区域服务器上的行业相关的任务推送
    private String doTradeTaskAtAreaSev(String eventNo, String oid, String eventType, String content, String bussInfoId) {
        try {

            LoginServer loginServer = loginServerMapper.findLastByOid(oid);

            log.error("11111111：" + loginServer.getServerIp());

            String targetOids = this.tradeTaskAtAreaSevHttp(loginServer.getServerIp(), eventNo, oid, eventType, content, bussInfoId);

            return targetOids;
        } catch (Exception e) {
            log.error("sdsService.doTradeTaskAtAreaSev_在管理服务器上_报错了：" + e.getMessage());
            return "";
        }
    }

    @Override
    public ApiResult tradeTaskAtAreaSev(String eventNo, String oid, String eventType, String content, String bussInfoId) {
        try {

            log.error("22222222：" + eventNo);

            String targetOids = this.doTradeTaskAtManageSev(eventNo, oid, eventType, content, bussInfoId);

            return ApiResult.success(targetOids);
        } catch (Exception e) {
            return ApiResult.error(e.getMessage());
        }
    }

    @Override
    public ApiResult tradeTaskAtAreaSevAtArea(String eventNo, String oid, String eventType, String content, String bussInfoId) {
        try {

//            String targetOids = this.doTradeTaskAtManageSev(eventNo, oid, eventType, content, bussInfoId);
//
//            return ApiResult.success(targetOids);
            return ApiResult.success();
        } catch (Exception e) {
            return ApiResult.error(e.getMessage());
        }
    }

    /**
     * 处理事件
     */
    @Override
//    @Transactional(rollbackFor = Exception.class)
    public ApiResult doEvent(String oid, String eventType, String content, String eventNo, String bussInfoId) {
        try {
            //判断个人事件是否记录过
            SdsEventPersonRecord sdsEventPersonRecord = sdsEventPersonRecordMapper.findByEventNoAndOid(eventNo, oid);
            if (sdsEventPersonRecord == null) {
                throw new Exception("没有找到事件记录！eventNo=" + eventNo + "（oid=" + oid + ")");
            } else {
                //根据SdsEventPersonRecord的genOid取得产生事件的管理服务器，然后去添加事件流程记录
                OwerServiceDto owerServiceDto = this.getOwerInfo(sdsEventPersonRecord.getGenOid());
//                this.pushDoEventWriteHttp(owerServiceDto.getIp(), oid, eventType, content, eventNo, bussInfoId);
                //异步
                AsyncManager.me().execute(AsyncFactory.pushDoEventWriteHttp(owerServiceDto.getIp(), oid, eventType, content, eventNo, bussInfoId));

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
    public ApiResult doEventWrite(String oid, String eventType, String content, String eventNo, String bussInfoId) {
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
                List<String> relations = new ArrayList<>();
                //在事件产生管理服务器上查找事件产生oid的关系,然后推送到相关管理服务器上
                SdsEventPersonRecord sdsEventPersonRecord = sdsEventPersonRecordMapper.findOneByEventNo(eventNo);

                List<SdsPercomRelation> list = this.genEventRelation(sdsEventPersonRecord.getGenOid(), eventType);
                if (list != null && list.size() > 0) {
                    for (SdsPercomRelation sdsPercomRelation : list) {
                        try {
                            if (!relations.contains(sdsPercomRelation.getTargetOid())) {
                                relations.add(sdsPercomRelation.getTargetOid());
                                OwerServiceDto targetOwer = this.getOwerInfo(sdsPercomRelation.getTargetOid());
//                            this.pushTaskHttp(targetOwer.getIp(), eventNo, oid, eventType, content, sdsPercomRelation.getTargetOid(), bussInfoId);
                                //异步
                                AsyncManager.me().execute(AsyncFactory.pushTaskHttp(targetOwer.getIp(), eventNo, oid, eventType, content, sdsPercomRelation.getTargetOid(), bussInfoId));
                            } else {
                                continue;
                            }
                        } catch (Exception e) {
                            //不处理， continue;
                            log.error("事件推送失败：" + sdsPercomRelation.toString());
                        }
                    }
                }
                //推送到产生事件的设备,刷新事件产生设备chat页面
                if (true) {
                    String relation = "";
                    if (sdsEventPersonRecord.getGenOid().length() > oid_relation_length) {
                        relation = sdsEventPersonRecord.getGenOid().substring(0, oid_relation_length);
                    } else {
                        relation = sdsEventPersonRecord.getGenOid();
                    }
                    //TODO 可以优化，因为已经是在事件产生的服务器了，不用在查找ip
//                    OwerServiceDto targetOwer = this.getOwerInfo(relation);
//                    this.pushTaskHttp(targetOwer.getIp(), eventNo, oid, eventType, content, relation, bussInfoId);
                    if (!relations.contains(relation)) {
                        relations.add(relation);
                        pushTask(oid, eventType, content, eventNo, relation, bussInfoId);
                    }
                }

                //根据行业推送
                if (true) {
                    SdsEventRelation sdsEventRelation = sdsEventRelationMapper.findByEventNo(eventNo);
                    if (sdsEventRelation != null) {
                        String targetOids = sdsEventRelation.getEventTradeOids();
                        if (targetOids != null && !targetOids.equals("")) {
                            String[] arr = null;
                            if (targetOids.contains(",")) {
                                arr = targetOids.split(",");
                            } else {
                                arr = new String[]{targetOids};
                            }
                            //去除一个行业用户下有两个设备，互相推送的bug

                            for (int i = 0; i < arr.length; i++) {
                                String relation = "";
                                try {
                                    if (arr[i].length() > oid_relation_length) {
                                        relation = arr[i].substring(0, oid_relation_length);
                                    } else {
                                        relation = arr[i];
                                    }
                                    //去除一个行业用户下有两个设备，互相推送的bug
                                    if (relations.contains(relation)) {
                                        continue;
                                    } else {
                                        relations.add(relation);

                                        OwerServiceDto targetOwer = SdsServiceImpl.this.getOwerInfo(arr[i]);
//                                    SdsServiceImpl.this.pushTaskHttp(targetOwer.getIp(), eventNo, oid, eventType, content, relation, bussInfoId);
                                        //异步
                                        AsyncManager.me().execute(AsyncFactory.pushTaskHttp(targetOwer.getIp(), eventNo, oid, eventType, content, relation, bussInfoId));
                                    }

                                } catch (Exception e) {
                                    //不处理， continue;
                                    log.error("事件推送失败：" + relation);
                                }
                            }
                        }
                    }
                }

                //根据手动添加用户推送
                if (true) {
                    SdsEventRelation sdsEventRelation = sdsEventRelationMapper.findByEventNo(eventNo);
                    if (sdsEventRelation != null) {
                        String targetOids = sdsEventRelation.getEventManualOids();
                        if (targetOids != null && !targetOids.equals("")) {
                            String[] arr = null;
                            if (targetOids.contains("--")) {
                                arr = targetOids.split("--");
                            } else {
                                arr = new String[]{targetOids};
                            }
                            //去除一个行业用户下有两个设备，互相推送的bug

                            for (int i = 0; i < arr.length; i++) {
                                String relation = "";
                                try {
                                    if (arr[i].length() > oid_relation_length) {
                                        relation = arr[i].substring(0, oid_relation_length);
                                    } else {
                                        relation = arr[i];
                                    }
                                    //去除一个行业用户下有两个设备，互相推送的bug
                                    if (relations.contains(relation)) {
                                        continue;
                                    } else {
                                        relations.add(relation);

                                        OwerServiceDto targetOwer = SdsServiceImpl.this.getOwerInfo(arr[i]);
//                                    SdsServiceImpl.this.pushTaskHttp(targetOwer.getIp(), eventNo, oid, eventType, content, relation, bussInfoId);
                                        //异步
                                        AsyncManager.me().execute(AsyncFactory.pushTaskHttp(targetOwer.getIp(), eventNo, oid, eventType, content, relation, bussInfoId));
                                    }

                                } catch (Exception e) {
                                    //不处理， continue;
                                    log.error("事件推送失败：" + relation);
                                }
                            }
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
    public ApiResult pushTask(String oid, String eventType, String content, String eventNo, String targetOid, String bussInfoId) {
        try {
            //根据targetOid查找oidFull
            log.info("++++++++++++++++++1 pushTask:targetOid=" + targetOid);
            List<Fzwno> fzwnos = fzwnoMapper.findByRelation(targetOid);
            for (Fzwno fzwno : fzwnos) {
                String oidFull = fzwno.getFzwRelation() + fzwno.getFzwDevice();

                //查找区域服务器然后发送at
                this.searchAreaServiceAndSend(oid, eventType, content, eventNo, oidFull, bussInfoId);
            }

            return ApiResult.success();
        } catch (Exception e) {
            return ApiResult.error(e.getMessage());
        }
    }

    //查找区域服务器然后发送at
    private void searchAreaServiceAndSend(String fromOid, String eventType, String content, String eventNo, String toOid, String bussInfoId) {
        try {

            log.info("++++++++++++++++++2 searchAreaServiceAndSendHttp:toOid=" + toOid);
            LoginServer loginServer = loginServerMapper.findLastByOid(toOid);
            log.info("++++++++++++++++++3 searchAreaServiceAndSendHttp:ip=" + loginServer.getServerIp());
//            this.searchAreaServiceAndSendHttp(loginServer.getServerIp(), fromOid, eventType, content, eventNo, toOid, bussInfoId);
            //异步
            AsyncManager.me().execute(AsyncFactory.searchAreaServiceAndSendHttp(loginServer.getServerIp(), fromOid, eventType, content, eventNo, toOid, bussInfoId));

        } catch (Exception e) {
            log.error("查找区域服务器然后发送at,出错了！" + e.getMessage());
        }
    }

    @Override
    public ApiResult areaServiceAndSend(String fromOid, String eventType, String content, String eventNo, String toOid, String bussInfoId) {
        try {

            log.info("++++++++++++++++++5 areaServiceAndSend:toOid=" + toOid);
            AtServiceImpl atService = (AtServiceImpl) SpringContextUtil2.getBean("atServiceImpl");
            BussInfo bussInfo = bussInfoMapper.selectByPrimaryKey(Long.valueOf(bussInfoId));
            if (bussInfo == null)
                return ApiResult.error("业务基础表找不到数据！");

            if ("A001".equals(bussInfo.getBusinessNum()) && "0001".equals(bussInfo.getCommand())) {

                return atService.sendAt("N", fromOid, bussInfo.getPriority(), bussInfo.getBusinessNum(), bussInfo.getPort(), bussInfo.getCommand(), fromOid, toOid);
            } else if ("E001".equals(bussInfo.getBusinessNum()) && "0001".equals(bussInfo.getCommand())) {
                ManageChatMsgAtParam manageChatMsgAtParam = new ManageChatMsgAtParam();
                manageChatMsgAtParam.setMsg(content);
                manageChatMsgAtParam.setMsgType("txt");
                manageChatMsgAtParam.setEventNo(eventNo);

                String param = com.alibaba.fastjson.JSONObject.toJSONString(manageChatMsgAtParam);
                return atService.sendAt("N", fromOid, bussInfo.getPriority(), bussInfo.getBusinessNum(), bussInfo.getPort(), bussInfo.getCommand(), param, toOid);
            } else if ("5010".equals(bussInfo.getBusinessNum()) && "FFFF".equals(bussInfo.getCommand())) {

                return atService.sendAt("N", fromOid, bussInfo.getPriority(), bussInfo.getBusinessNum(), bussInfo.getPort(), bussInfo.getCommand(), content, toOid);
            } else {
                return ApiResult.error("该业务暂时还不能处理！");
            }


        } catch (Exception e) {
            return ApiResult.error(e.getMessage());
        }
    }

    /**
     * 处理事件
     */
    @Override
//    @Transactional(rollbackFor = Exception.class)
    public ApiResult pushEvent(String genOid, String eventType, String content, String eventNo, String targetOid, String bussInfoId) {
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
                    sdsEventPersonRecord.setGenOid(genOid);
                    sdsEventPersonRecord.setEventTypeInfoId(Long.valueOf(eventType));

                    sdsEventPersonRecordMapper.insertSelective(sdsEventPersonRecord);

                }
                //查找区域服务器然后发送at
                this.searchAreaServiceAndSend(genOid, eventType, content, eventNo, oidFull, bussInfoId);
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
    public ApiResult doClubUserManage(String flag, String oid, String pri, String buss, String port, String cmd, String param) {
        try {
            BussInfo bussInfo = bussInfoMapper.findByBussAndCmd(buss, cmd);
            if (bussInfo == null)
                throw new Exception("业务基础表找不到数据！");

            com.alibaba.fastjson.JSONObject objParamAt = com.alibaba.fastjson.JSONObject.parseObject(param);
            ClubUserManageAtParam clubUserManageAtParam = (ClubUserManageAtParam) com.alibaba.fastjson.JSONObject.toJavaObject(objParamAt, ClubUserManageAtParam.class);

            String eventNo = clubUserManageAtParam.getEventNo();
            String[] arr = eventNo.split("--");
            String genOid = arr[0];
            String eventType = arr[2];
            String content = "";
            if (ClubUserManageTypeEnum.add.name().equals(clubUserManageAtParam.getMsgType())) {
                content = "群增加oid:" + clubUserManageAtParam.getOid();
            } else if (ClubUserManageTypeEnum.dec.name().equals(clubUserManageAtParam.getMsgType())) {
                content = "群删除oid:" + clubUserManageAtParam.getOid();
            }

            //保存事件记录
            ApiResult apiResult = this.pushEvent(genOid, eventType, content, eventNo, clubUserManageAtParam.getOid(), bussInfo.getId() + "");
            if (ApiResult.SUCCESS.equals(apiResult.get(ApiResult.RETURNCODE))) {
                //在事件产生服务器上保存事件相关用户
                OwerServiceDto owerServiceDto = this.getOwerInfo(genOid);
                this.clubUserManageHttp(owerServiceDto.getIp(), clubUserManageAtParam.getMsgType(), eventNo, clubUserManageAtParam.getOid());

                return ApiResult.success();
            } else {
                return apiResult;
            }

        } catch (Exception e) {
            return ApiResult.error(e.getMessage());
        }
    }

    @Override
    public ApiResult clubUserManage(String oid, String eventNo, String msgType) {
        try {
            SdsEventRelation sdsEventRelation = sdsEventRelationMapper.findByEventNo(eventNo);
            if (sdsEventRelation == null)
                throw new Exception("事件关系找不到记录！");

            String oids = sdsEventRelation.getEventManualOids();
            if (oids.contains(oid)) {
                if (ClubUserManageTypeEnum.add.name().equals(msgType)) {
                    //
                } else if (ClubUserManageTypeEnum.dec.name().equals(msgType)) {
                    if (oids.contains("--")) {
                        String[] arr = oids.split("--");
                        List<String> list = new ArrayList();
                        for (int i = 0; i < arr.length; i++) {
                            if (!arr[i].equals(oid)) {
                                list.add(arr[i]);
                            }
                        }
                        String newOids = "";
                        for(String ss:list){
                            newOids += ss + "--";
                        }
                        if(newOids.endsWith("--")){
                            newOids = newOids.substring(0,newOids.length()-2);
                        }

                        sdsEventRelation.setEventManualOids(newOids);
                        sdsEventRelationMapper.updateByPrimaryKeySelective(sdsEventRelation);
                    } else {
                        sdsEventRelation.setEventManualOids("");
                        sdsEventRelationMapper.updateByPrimaryKeySelective(sdsEventRelation);
                    }
                }
            } else {
                if (ClubUserManageTypeEnum.add.name().equals(msgType)) {
                    oids += "--" + oid;

                    sdsEventRelation.setEventManualOids(oids);
                    sdsEventRelationMapper.updateByPrimaryKeySelective(sdsEventRelation);
                } else if (ClubUserManageTypeEnum.dec.name().equals(msgType)) {
                    //
                }
            }

            return ApiResult.success();
        } catch (Exception e) {
            return ApiResult.error(e.getMessage());
        }
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

    private void clubUserManageHttp(String ip, String msgType, String eventNo, String oid) throws Exception {
        String url = "http://" + ip + ":" + "9999/clubUserManage";
        String params = "";
        Map<String, String> map = new HashMap<>();
        map.put("eventNo", eventNo);
        map.put("oid", oid);
        map.put("msgType", msgType);
        params = new Gson().toJson(map);
        JSONObject jsonObject = BaseRestfulUtil.doPostForJson(url, map);
        if (jsonObject != null) {
            String code = (String) jsonObject.get(ApiResult.RETURNCODE);
            if (ApiResult.SUCCESS.equals(code)) {
                log.info("++++++++++++++++请求clubUserManage成功");
            } else {
                log.info("++++++++++++++++请求clubUserManage失败:" + "服务端错误：" + jsonObject.get(ApiResult.MESSAGE));
                throw new Exception("clubUserManage失败：服务端错误：" + jsonObject.get(ApiResult.MESSAGE));
            }
        } else {
            log.info("++++++++++++++++请求clubUserManage失败:" + "连接错误");
            throw new Exception("clubUserManage失败:连接错误");
        }
    }

    private String tradeTaskAtAreaSevHttp(String ip, String eventNo, String oid, String eventType, String content, String bussInfoId) throws Exception {
        String url = "http://" + ip + ":" + "9999/tradeTaskAtAreaSev";
        String params = "";
        Map<String, String> map = new HashMap<>();
        map.put("eventNo", eventNo);
        map.put("oid", oid);
        map.put("eventType", eventType);
        map.put("content", content);
        map.put("bussInfoId", bussInfoId);
        params = new Gson().toJson(map);
        JSONObject jsonObject = BaseRestfulUtil.doPostForJson(url, map);
        if (jsonObject != null) {
            String code = (String) jsonObject.get(ApiResult.RETURNCODE);
            if (ApiResult.SUCCESS.equals(code)) {
                String targetOids = (String) jsonObject.get(ApiResult.MESSAGE);
                log.info("++++++++++++++++请求tradeTaskAtAreaSev成功:targetOids=" + targetOids);
                return targetOids;
            } else {
                log.info("++++++++++++++++请求tradeTaskAtAreaSev失败:" + "服务端错误：" + jsonObject.get(ApiResult.MESSAGE));
                throw new Exception("tradeTaskAtAreaSev失败：服务端错误：" + jsonObject.get(ApiResult.MESSAGE));
            }
        } else {
            log.info("++++++++++++++++请求tradeTaskAtAreaSev失败:" + "连接错误");
            throw new Exception("tradeTaskAtAreaSev失败:连接错误");
        }
    }

    public void pushDoEventWriteHttp(String ip, String oid, String eventType, String content, String eventNo, String bussInfoId) throws Exception {
        String url = "http://" + ip + ":" + "9999/doEventWrite";
        String params = "";
        Map<String, String> map = new HashMap<>();
        map.put("oid", oid);
        map.put("eventType", eventType);
        map.put("content", content);
        map.put("eventNo", eventNo);
        map.put("bussInfoId", bussInfoId);
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

    public void searchAreaServiceAndSendHttp(String ip, String fromOid, String eventType, String content, String eventNo, String toOid, String bussInfoId) throws Exception {
        String url = "http://" + ip + ":" + "9999/areaServiceAndSend";
        String params = "";
        Map<String, String> map = new HashMap<>();
        map.put("fromOid", fromOid);
        map.put("eventType", eventType);
        map.put("content", content);
        map.put("eventNo", eventNo);
        map.put("toOid", toOid);
        map.put("bussInfoId", bussInfoId);

        log.info("++++++++++++++++4 searchAreaServiceAndSendHttp:toOid" + toOid);
        params = new Gson().toJson(map);
        JSONObject jsonObject = BaseRestfulUtil.doPostForJson(url, map);
        if (jsonObject != null) {
            String code = (String) jsonObject.get(ApiResult.RETURNCODE);
            if (ApiResult.SUCCESS.equals(code)) {

                log.info("++++++++++++++++请求searchAreaServiceAndSend成功:" + jsonObject.get(ApiResult.MESSAGE));
            } else {
                log.info("++++++++++++++++请求searchAreaServiceAndSend失败:" + "服务端错误：" + jsonObject.get(ApiResult.MESSAGE));
                throw new Exception("searchAreaServiceAndSend失败：服务端错误：" + jsonObject.get(ApiResult.MESSAGE));
            }
        } else {
            log.info("++++++++++++++++请求searchAreaServiceAndSend失败:" + "连接错误");
            throw new Exception("searchAreaServiceAndSend失败:连接错误");
        }
    }

    public void pushTaskHttp(String ip, String eventNo, String oid, String eventType, String content, String targetOid, String bussInfoId) throws Exception {
        String url = "http://" + ip + ":" + "9999/pushTask";
        String params = "";
        Map<String, String> map = new HashMap<>();
        map.put("oid", oid);
        map.put("eventType", eventType);
        map.put("content", content);
        map.put("eventNo", eventNo);
        map.put("targetOid", targetOid);
        map.put("bussInfoId", bussInfoId);
        params = new Gson().toJson(map);
        JSONObject jsonObject = BaseRestfulUtil.doPostForJson(url, map);
        if (jsonObject != null) {
            String code = (String) jsonObject.get(ApiResult.RETURNCODE);
            if (ApiResult.SUCCESS.equals(code)) {

                log.info("++++++++++++++++请求pushTask成功:oid=" + oid);
            } else {
                log.info("++++++++++++++++请求pushTask失败:" + "服务端错误：" + jsonObject.get(ApiResult.MESSAGE));
                throw new Exception("pushTask失败：服务端错误：" + jsonObject.get(ApiResult.MESSAGE));
            }
        } else {
            log.info("++++++++++++++++请求pushTask失败:" + "连接错误");
            throw new Exception("pushTask失败:连接错误");
        }
    }

    public void pushEventHttp(String ip, String eventNo, String oid, String eventType, String content, String targetOid, String bussInfoId) throws Exception {
        String url = "http://" + ip + ":" + "9999/pushEvent";
        String params = "";
        Map<String, String> map = new HashMap<>();
        map.put("oid", oid);
        map.put("eventType", eventType);
        map.put("content", content);
        map.put("eventNo", eventNo);
        map.put("targetOid", targetOid);
        map.put("bussInfoId", bussInfoId);
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

    private void genEventHttp(String ip, String oid, String eventType, String content, String bussInfoId) throws Exception {
        String url = "http://" + ip + ":" + "9999/genEvent";
        String params = "";
        Map<String, String> map = new HashMap<>();
        map.put("oid", oid);
        map.put("eventType", eventType);
        map.put("content", content);
        map.put("bussInfoId", bussInfoId);
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
                if (wjuserOwerDto != null) {
                    sdsEventInfoDto.setUserName(wjuserOwerDto.getUserName());
                    sdsEventInfoDto.setHeadIconUrl(wjuserOwerDto.getHeadIconUrl());
                    sdsEventInfoDto.setMajor(wjuserOwerDto.getMajor());
                }

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


    public ApiResult doLightOn(String flag, String oid, String pri, String buss, String port, String cmd, String param) {
        try {
            BussInfo bussInfo = bussInfoMapper.findByBussAndCmd(buss, cmd);
            if (bussInfo == null)
                throw new Exception("业务基础表找不到数据！");

            String relation = oid.substring(0, oid_relation_length);
            List<Fzwno> fzwnos = fzwnoMapper.findByRelation(relation);
            for (Fzwno fzwno : fzwnos) {
                if (fzwno.getDevtypeId() == 6) {

                    String oidFull = fzwno.getFzwRelation() + fzwno.getFzwDevice();

                    //查找区域服务器然后发送at
                    this.searchAreaServiceAndSend(oid, "", param, "", oidFull, bussInfo.getId() + "");
                } else {
                    continue;
                }
            }

            return ApiResult.success();
        } catch (Exception e) {
            return ApiResult.error(e.getMessage());
        }
    }
}
