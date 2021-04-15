package com.wujie.common.dto.wj;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

public class SdsEventListDto implements Serializable {
    private String eventNo;
    private Long eventType;
    private String originEventNo;
    private String genOid;

    //
    private String eventName;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date creatTime;

    public String getGenOid() {
        return genOid;
    }

    public void setGenOid(String genOid) {
        this.genOid = genOid;
    }

    public Long getEventType() {
        return eventType;
    }

    public void setEventType(Long eventType) {
        this.eventType = eventType;
    }

    public String getEventNo() {
        return eventNo;
    }

    public void setEventNo(String eventNo) {
        this.eventNo = eventNo;
    }

    public String getOriginEventNo() {
        return originEventNo;
    }

    public void setOriginEventNo(String originEventNo) {
        this.originEventNo = originEventNo;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public Date getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(Date creatTime) {
        this.creatTime = creatTime;
    }
}