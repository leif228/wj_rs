package com.wujie.ac.app.business.app.service.system.impl;

import com.google.gson.Gson;
import com.wujie.ac.app.business.app.service.system.SdsService;
import com.wujie.ac.app.business.entity.*;
import com.wujie.ac.app.business.repository.*;
import com.wujie.ac.app.business.util.date.DateUtil;
import com.wujie.common.base.ApiResult;
import com.wujie.common.dto.wj.SdsEventInfoDto;
import com.wujie.common.dto.wj.SdsEventListDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

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

    private static String ststus1 = "产生事件";
    private static String ststus2 = "事件进行中";
    private static String ststus3 = "事件结束";

    @Autowired
    public SdsServiceImpl(SdsEventInfoMapper sdsEventInfoMapper, SdsEventPersonRecordMapper sdsEventPersonRecordMapper, SdsEventRelationMapper sdsEventRelationMapper,
                          SdsEventTypeInfoMapper sdsEventTypeInfoMapper, SdsPercomRelationMapper sdsPercomRelationMapper, SdsRelationTypeInfoMapper sdsRelationTypeInfoMapper,
                          WjuserOwerMapper wjuserOwerMapper) {
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
