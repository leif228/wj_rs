package com.wujie.ac.app.business.app.service.system.impl;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.wujie.ac.app.business.app.service.system.AtService;
import com.wujie.ac.app.business.app.service.system.SdsService;
import com.wujie.ac.app.business.entity.*;
import com.wujie.ac.app.business.repository.*;
import com.wujie.ac.app.business.util.NumConvertUtil;
import com.wujie.ac.app.business.util.date.DateUtil;
import com.wujie.ac.app.framework.util.request.BaseRestfulUtil;
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

    public static final String HEARD = "AT@";
    public static final String END = "#*";

    @Autowired
    public AtServiceImpl(BussInfoMapper bussInfoMapper,FzwnoMapper fzwnoMapper, NodeInfoOwerMapper nodeInfoOwerMapper, SdsEventInfoMapper sdsEventInfoMapper, SdsEventPersonRecordMapper sdsEventPersonRecordMapper, SdsEventRelationMapper sdsEventRelationMapper,
                         SdsEventTypeInfoMapper sdsEventTypeInfoMapper, SdsPercomRelationMapper sdsPercomRelationMapper, SdsRelationTypeInfoMapper sdsRelationTypeInfoMapper,
                         WjuserOwerMapper wjuserOwerMapper) {
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
            log.debug("doAtTask事件:tx=" + tx);
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
                index += oid.length();
                String pri = tx.substring(index, index + 4);
                index += pri.length();
                String buss = tx.substring(index, index + 4);
                index += buss.length();
                String port = tx.substring(index, index + 4);
                index += port.length();
                String cmd = tx.substring(index, index + 4);
                index += cmd.length();
                String paramLen = tx.substring(index, index + 4);
                int len = NumConvertUtil.HexStringToInt(paramLen);
                index += paramLen.length();
                String param = tx.substring(index, index + len);

                log.debug("param=" + param);
                index += param.length();
                String strend = tx.substring(index, index + END.length());//取出帧尾

                return ApiResult.success("成功",doTask(flag,oid,pri,buss,port,cmd,param));
            } else {
                return ApiResult.error("传输的数据格式错误！数据为：" + tx);
            }
        } catch (Exception e) {
            return ApiResult.error("AT数据解析出错了！"+ e.getMessage());
        }
    }

    private String doTask(String flag, String oid, String pri, String buss, String port, String cmd, String param) throws Exception {
        BussInfo bussInfo = bussInfoMapper.findByBussAndCmd(buss,cmd);
        if(bussInfo == null)
            throw new Exception("业务基础表找不到数据！");

        return "";
    }

}
