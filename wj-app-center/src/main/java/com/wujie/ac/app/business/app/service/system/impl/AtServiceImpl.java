package com.wujie.ac.app.business.app.service.system.impl;

import com.google.gson.Gson;
import com.wujie.ac.app.business.app.service.system.AtService;
import com.wujie.ac.app.business.entity.*;
import com.wujie.ac.app.business.entity.at.ApplyOprationAtParam;
import com.wujie.ac.app.business.entity.at.ClubUserManageAtParam;
import com.wujie.ac.app.business.entity.at.ManageChatMsgAtParam;
import com.wujie.ac.app.business.repository.*;
import com.wujie.ac.app.business.util.NumConvertUtil;
import com.wujie.ac.app.framework.util.request.BaseRestfulUtil;
import com.wujie.common.base.ApiResult;
import com.wujie.common.dto.wj.OwerServiceDto;
import com.wujie.fclient.service.TcpUserService;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class AtServiceImpl implements AtService {

    private SdsEventInfoMapper sdsEventInfoMapper;
    private SdsEventPersonRecordMapper sdsEventPersonRecordMapper;
    private SdsEventRelationMapper sdsEventRelationMapper;
    private SdsEventTypeInfoMapper sdsEventTypeInfoMapper;
    private SdsPercomRelationMapper sdsPercomRelationMapper;
    private SdsRelationTypeInfoMapper sdsRelationTypeInfoMapper;
    private WjuserOwerMapper wjuserOwerMapper;
    private NodeInfoOwerMapper nodeInfoOwerMapper;
    private FzwnoMapper fzwnoMapper;
    private BussInfoMapper bussInfoMapper;
    private EventtypeBussMdInfoMapper eventtypeBussMdInfoMapper;

    private SdsServiceImpl sdsService;
    private TcpUserService tcpUserService;
    private EventtypeTradecodeMdInfoMapper eventtypeTradecodeMdInfoMapper;
    private WjuserTradeMapper wjuserTradeMapper;

    public static final String HEARD = "AT@";
    public static final String END = "#*";

    @Autowired
    public AtServiceImpl(WjuserTradeMapper wjuserTradeMapper, EventtypeTradecodeMdInfoMapper eventtypeTradecodeMdInfoMapper, TcpUserService tcpUserService, SdsServiceImpl sdsService, EventtypeBussMdInfoMapper eventtypeBussMdInfoMapper, BussInfoMapper bussInfoMapper, FzwnoMapper fzwnoMapper, NodeInfoOwerMapper nodeInfoOwerMapper, SdsEventInfoMapper sdsEventInfoMapper, SdsEventPersonRecordMapper sdsEventPersonRecordMapper, SdsEventRelationMapper sdsEventRelationMapper,
                         SdsEventTypeInfoMapper sdsEventTypeInfoMapper, SdsPercomRelationMapper sdsPercomRelationMapper, SdsRelationTypeInfoMapper sdsRelationTypeInfoMapper,
                         WjuserOwerMapper wjuserOwerMapper) {
        this.wjuserTradeMapper = wjuserTradeMapper;
        this.eventtypeTradecodeMdInfoMapper = eventtypeTradecodeMdInfoMapper;
        this.tcpUserService = tcpUserService;
        this.sdsService = sdsService;
        this.eventtypeBussMdInfoMapper = eventtypeBussMdInfoMapper;
        this.bussInfoMapper = bussInfoMapper;
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

    @Override
    public ApiResult doAtTask(String tx) {
        try {
            log.error("接收到的at为:" + tx);
            if (tx == null || "".equals(tx))
                return ApiResult.error("传输的数据错误！数据为：" + tx);

            int headpoint = tx.indexOf(HEARD);//找出帧头
            int endpoint = tx.indexOf(END);//找出帧尾

            if (headpoint != -1 && endpoint != -1) {
                //AT@Nchn0L0a002020091100011000000000E00200010500A001000100010027chn0L0a00202009110001100000000011110102#*
                int index = 0;
                String strhead = tx.substring(index, HEARD.length());//取出帧头
                index += strhead.length();
                String flag = tx.substring(index, index + 1);
                index += flag.length();
                String oid = tx.substring(index, index + 39);
                log.debug("解码后的oid为：" + oid);
                index += oid.length();
                String pri = tx.substring(index, index + 4);
                index += pri.length();
                String buss = tx.substring(index, index + 4);
                log.debug("解码后的buss为：" + buss);
                index += buss.length();
                String port = tx.substring(index, index + 4);
                log.debug("解码后的port为：" + port);
                index += port.length();
                String cmd = tx.substring(index, index + 4);
                log.debug("解码后的cmd为：" + cmd);
                index += cmd.length();

                String tempEnd = tx.substring(index);
                if (END.equals(tempEnd)) {
                    String strend = tx.substring(index, index + END.length());//取出帧尾

                    doAtTask(flag, oid, pri, buss, port, cmd, "");
                } else {

                    String paramLen = tx.substring(index, index + 4);
                    log.debug("解码后的paramLen为：" + paramLen);
                    int len = NumConvertUtil.HexStringToInt(paramLen);
                    log.debug("len：" + len);
                    index += paramLen.length();
//                    String param = tx.substring(index, index + len);
//
//                    log.debug("解码后的param为：" + param);
//                    index += param.length();
//                    String strend = tx.substring(index, index + END.length());//取出帧尾

                    String param = tx.substring(index, tx.length()-END.length());

                    doAtTask(flag, oid, pri, buss, port, cmd, param);
                }

                return ApiResult.success("成功");
            } else {
                return ApiResult.error("传输的数据格式错误！数据为：" + tx);
            }
        } catch (Exception e) {
            return ApiResult.error("AT数据处理出错了！" + e.getMessage());
        }
    }

    @Override
    public ApiResult atTask(String flag, String oid, String pri, String buss, String port, String cmd, String param) {
        try {
            if (true) {
                //开灯业务处理
                if ("5010".equals(buss) && "FFFF".equals(cmd)) {
                    return sdsService.doLightOn(flag, oid, pri, buss, port, cmd, param);

                } else if ("E011".equals(buss) && "0001".equals(cmd)) {  //群成员管理业务处理
                    return sdsService.doClubUserManage(flag, oid, pri, buss, port, cmd, param);
                } else if ("E012".equals(buss) && "0001".equals(cmd)) {  //新建群管理业务处理
                    return sdsService.doNewClub(flag, oid, pri, buss, port, cmd, param);
                }else if ("E021".equals(buss) && "0001".equals(cmd)) {  //请求执行业务处理
                    return sdsService.doApplyOpration(flag, oid, pri, buss, port, cmd, param);
                }
            }

            if ("A001".equals(buss) && "0001".equals(cmd)) {

                BussInfo bussInfo = bussInfoMapper.findByBussAndCmd(buss, cmd);
                if (bussInfo == null)
                    throw new Exception("BussInfo业务基础表找不到数据！");

                List<EventtypeBussMdInfo> eventtypeBussMdInfos = eventtypeBussMdInfoMapper.findbyBussId(bussInfo.getId());
                if (eventtypeBussMdInfos.size() == 0)
                    throw new Exception("EventtypeBussMdInfo业务基础表找不到数据！");

                //TODO　后续根据算法实现匹配
                EventtypeBussMdInfo targInfo = eventtypeBussMdInfos.get(0);
                //查找是否已经有事件记录
//                SdsEventPersonRecord sdsEventPersonRecord = sdsEventPersonRecordMapper.findMaxByGenOidAndEventTypeId(param, targInfo.getEventTypeInfoId());
//                if (sdsEventPersonRecord == null) {
                //新产生事件
                return sdsService.doGenEvent(oid, "" + targInfo.getEventTypeInfoId(), param, bussInfo.getId() + "", flag, pri, port);
//                } else {
//                    //处理事件
//                    return sdsService.doEvent(oid, targInfo.getEventTypeInfoId() + "", bussInfo.getTxt(), sdsEventPersonRecord.getEventNo(), bussInfo.getId()+"");
//                }
            } else if ("E001".equals(buss) && "0001".equals(cmd)) {

                BussInfo bussInfo = bussInfoMapper.findByBussAndCmd(buss, cmd);
                if (bussInfo == null)
                    throw new Exception("BussInfo业务基础表找不到数据！");

                List<EventtypeBussMdInfo> eventtypeBussMdInfos = eventtypeBussMdInfoMapper.findbyBussId(bussInfo.getId());
                if (eventtypeBussMdInfos.size() == 0)
                    throw new Exception("EventtypeBussMdInfo业务基础表找不到数据！");

                //TODO　后续根据算法实现匹配
                EventtypeBussMdInfo targInfo = eventtypeBussMdInfos.get(0);

                com.alibaba.fastjson.JSONObject objParamAt = com.alibaba.fastjson.JSONObject.parseObject(param);
                ManageChatMsgAtParam manageChatMsgAtParam = (ManageChatMsgAtParam) com.alibaba.fastjson.JSONObject.toJavaObject(objParamAt, ManageChatMsgAtParam.class);

                //处理事件
                return sdsService.doEvent(oid, targInfo.getEventTypeInfoId() + "", param, manageChatMsgAtParam.getEventNo(), bussInfo.getId() + "", flag, pri, port);

            } else {
                return sdsService.doLightOn(flag, oid, pri, buss, port, cmd, param);
            }

        } catch (Exception e) {
            log.error("atTask报错了！" + e.getMessage());
            return ApiResult.error("atTask报错了！" + e.getMessage());
        }
    }

    private void doAtTask(String flag, String oid, String pri, String buss, String port, String cmd, String param) throws Exception {
        try {
            //群成员管理业务处理
            if ("E011".equals(buss) && "0001".equals(cmd)) {

                com.alibaba.fastjson.JSONObject objParamAt = com.alibaba.fastjson.JSONObject.parseObject(param);
                ClubUserManageAtParam clubUserManageAtParam = (ClubUserManageAtParam) com.alibaba.fastjson.JSONObject.toJavaObject(objParamAt, ClubUserManageAtParam.class);
                //查找oid归属服务器
                OwerServiceDto owerServiceDto = sdsService.getOwerInfo(clubUserManageAtParam.getOid());

                String result = this.atTaskHttp(owerServiceDto.getIp(), flag, oid, pri, buss, port, cmd, param);

            }
            //请求执行业务处理
            else if ("E021".equals(buss) && "0001".equals(cmd)) {

                com.alibaba.fastjson.JSONObject objParamAt = com.alibaba.fastjson.JSONObject.parseObject(param);
                ApplyOprationAtParam applyOprationAtParam = (ApplyOprationAtParam) com.alibaba.fastjson.JSONObject.toJavaObject(objParamAt, ApplyOprationAtParam.class);
                String eventNo = applyOprationAtParam.getEventNo();
                String[] arr = eventNo.split("--");
                String genOid = arr[0];
                String eventType = arr[2];
                //查找oid归属服务器
                OwerServiceDto owerServiceDto = sdsService.getOwerInfo(genOid);

                String result = this.atTaskHttp(owerServiceDto.getIp(), flag, oid, pri, buss, port, cmd, param);

            } else {

                //查找oid归属服务器,处理用户关系相关的任务推送
                OwerServiceDto owerServiceDto = sdsService.getOwerInfo(oid);

                String result = this.atTaskHttp(owerServiceDto.getIp(), flag, oid, pri, buss, port, cmd, param);
            }
            //在区域服务器，处理与行业相关的任务推送
//        this.doTradeTask(result,owerServiceDto.getIp(),flag,oid,pri,buss,port,cmd,param);
            //异步
//        AsyncManager.me().execute(AsyncFactory.doTradeTask(result,owerServiceDto.getIp(),flag,oid,pri,buss,port,cmd,param));
        } catch (Exception e) {
            log.error("doAtTask报错了！" + e.getMessage());
            throw new Exception("doAtTask报错了！" + e.getMessage());
        }
    }

    public static void main(String[] args) {
        String s = "{\"event_no\":\"chn0L0C3020210127000110!!!!!!000005000b--20210305160120382--1\",\"msg_type\":\"add\",\"oid\":\"chn0L0RC020210305000110!!!!!!0000050001\"}";

        com.alibaba.fastjson.JSONObject objParamAt = com.alibaba.fastjson.JSONObject.parseObject(s);
        ClubUserManageAtParam clubUserManageAtParam = (ClubUserManageAtParam) com.alibaba.fastjson.JSONObject.toJavaObject(objParamAt, ClubUserManageAtParam.class);

        System.out.println(clubUserManageAtParam.getOid());
    }

//    public void doTradeTask(String eventNo,String ip, String flag, String oid, String pri, String buss, String port, String cmd, String param) {
//        try{
//            if ("A001".equals(buss) && "0001".equals(cmd)) {
//                BussInfo bussInfo = bussInfoMapper.findByBussAndCmd(buss, cmd);
//                if (bussInfo == null)
//                    throw new Exception("业务基础表找不到数据！");
//
//                List<EventtypeBussMdInfo> eventtypeBussMdInfos = eventtypeBussMdInfoMapper.findbyBussId(Long.valueOf(bussInfo.getId()));
//                if (eventtypeBussMdInfos.size() == 0)
//                    throw new Exception("业务基础表找不到数据！");
//
//                //TODO　后续根据算法实现匹配
//                EventtypeBussMdInfo targInfo = eventtypeBussMdInfos.get(0);
//
//                String targetOids = "";
//
//                List<EventtypeTradecodeMdInfo> eventtypeTradecodeMdInfos = eventtypeTradecodeMdInfoMapper.findByEventId(targInfo.getEventTypeInfoId());
//                //去除一个行业用户下有两个设备，互相推送的bug
//                List<String> relations = new ArrayList<>();
//                for (EventtypeTradecodeMdInfo eventtypeTradecodeMdInfo : eventtypeTradecodeMdInfos) {
//                    List<WjuserTrade> wjuserTrades = wjuserTradeMapper.findByLikeTrades(eventtypeTradecodeMdInfo.getTradeCodeInfoId() + "");
//
//                    log.error("3333333：" + wjuserTrades.size());
//                    for (WjuserTrade wjuserTrade : wjuserTrades) {
//                        try {
//                            //去除事件产生者又是行业相关的oid
//                            if(oid.equals(wjuserTrade.getOid()))
//                                continue;
//
//                            OwerServiceDto targetOwer = sdsService.getOwerInfo(wjuserTrade.getOid());
//                            String relation = wjuserTrade.getOid().substring(0, 22);
//                            targetOids += wjuserTrade.getOid() + ",";
//
//                            //去除一个行业用户下有两个设备，互相推送的bug
//                            if(relations.contains(relation)){
//                                continue;
//                            }else {
//                                relations.add(relation);
//
//                                log.error("44444444：" + relation);
////                            sdsService.pushEventHttp(targetOwer.getIp(), eventNo, oid, targInfo.getEventTypeInfoId()+"", "新事件产生", relation, bussInfo.getId()+"");
//                                //异步
//                                AsyncManager.me().execute(AsyncFactory.pushEventHttp(targetOwer.getIp(), eventNo, oid, targInfo.getEventTypeInfoId()+"", "新事件产生", relation, bussInfo.getId()+""));
//                            }
//
//                        } catch (Exception e) {
//                            //不处理， continue;
//
//                            log.error("事件推送失败：" + wjuserTrade.getOid().toString());
//                        }
//                    }
//                }
//
//                if (!targetOids.equals("")) {
//                    targetOids = targetOids.substring(0, targetOids.length() - 1);
//                }
//
//                this.updataSdsEventRelationHttp(ip,eventNo,targetOids);
//            }
//
//        }catch (Exception e){
//            log.error("atService.doTradeTask_报错了："+ e.getMessage());
//        }
//    }

    private String genAt(String flag, String oid, String pri, String buss, String port, String cmd, String param) throws Exception {
        //AT@Nchn0L0a002020091100011000000000E00200010500A001000100010027chn0L0a00202009110001100000000011110102#*
        byte[] objectBytes = param.getBytes("UTF-8");

        String paramLen = NumConvertUtil.IntToHexStringLimit4(objectBytes.length);
        String at = HEARD + flag + oid + pri + buss + port + cmd + paramLen + param + END;//取出帧头
        log.debug("genAt编码后的at为：" + at);

        return at;
    }

    public ApiResult sendAt(String flag, String senOid, String pri, String buss, String port, String cmd, String param, String recOid) {
        try {
            String at = this.genAt(flag, senOid, pri, buss, port, cmd, param);

            log.info("++++++++++++++++++6 sendAt:at=" + at);
            log.info("++++++++++++++++++sendAt:senOid=" + senOid);
            log.info("++++++++++++++++++sendAt:recOid=" + recOid);
            return tcpUserService.sendAtTask(recOid, at);
        } catch (Exception e) {
            return ApiResult.error("at发送失败！原因：" + e.getMessage());
        }
    }

    private String atTaskHttp(String ip, String flag, String oid, String pri, String buss, String port, String cmd, String param) throws Exception {
        String url = "http://" + ip + ":" + "9999/atTask";
        String params = "";
        Map<String, String> map = new HashMap<>();
        map.put("flag", flag);
        map.put("oid", oid);
        map.put("pri", pri);
        map.put("buss", buss);
        map.put("port", port);
        map.put("cmd", cmd);
        map.put("param", param);
        params = new Gson().toJson(map);
        JSONObject jsonObject = BaseRestfulUtil.doPostForJson(url, map);
        if (jsonObject != null) {
            String code = (String) jsonObject.get(ApiResult.RETURNCODE);
            if (ApiResult.SUCCESS.equals(code)) {
                String eventNo = (String) jsonObject.get(ApiResult.MESSAGE);
//                JSONObject data = (JSONObject) jsonObject.get(ApiResult.CONTENT);

//                OwerServiceDto deviceVo = (OwerServiceDto) JSONObject.toBean(data, OwerServiceDto.class);
                log.info("++++++++++++++++请求atTask成功:eventNo=" + eventNo);
//                return data.toString();
                return eventNo;
            } else {
                log.info("++++++++++++++++请求atTask失败:" + "服务端错误：" + jsonObject.get(ApiResult.MESSAGE));
                throw new Exception("atTask失败：服务端错误：" + jsonObject.get(ApiResult.MESSAGE));
            }
        } else {
            log.info("++++++++++++++++请求atTask失败:" + "连接错误");
            throw new Exception("atTask失败:连接错误");
        }
    }

    private void updataSdsEventRelationHttp(String ip, String eventNo, String targetOids) throws Exception {
        String url = "http://" + ip + ":" + "9999/updataSdsEventRelation";
        String params = "";
        Map<String, String> map = new HashMap<>();
        map.put("eventNo", eventNo);
        map.put("targetOids", targetOids);
        params = new Gson().toJson(map);
        JSONObject jsonObject = BaseRestfulUtil.doPostForJson(url, map);
        if (jsonObject != null) {
            String code = (String) jsonObject.get(ApiResult.RETURNCODE);
            if (ApiResult.SUCCESS.equals(code)) {
//                JSONObject data = (JSONObject) jsonObject.get(ApiResult.CONTENT);

//                OwerServiceDto deviceVo = (OwerServiceDto) JSONObject.toBean(data, OwerServiceDto.class);
                log.info("++++++++++++++++请求updataSdsEventRelation成功:");
//                return data.toString();
            } else {
                log.info("++++++++++++++++请求updataSdsEventRelation失败:" + "服务端错误：" + jsonObject.get(ApiResult.MESSAGE));
                throw new Exception("updataSdsEventRelation失败：服务端错误：" + jsonObject.get(ApiResult.MESSAGE));
            }
        } else {
            log.info("++++++++++++++++请求updataSdsEventRelation失败:" + "连接错误");
            throw new Exception("updataSdsEventRelation失败:连接错误");
        }
    }

}
