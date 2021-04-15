package com.wujie.ac.app.business.entity;

import java.util.Date;

public class SdsEventPersonRecord {
    //
    private Long id;

    //
    private String genOid;

    //
    private String eventNo;

    //
    private String originEventNo;

    //
    private Long eventTypeInfoId;

    //
    private String oid;

    //
    private String status;

    //
    private Date creatTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGenOid() {
        return genOid;
    }

    public void setGenOid(String genOid) {
        this.genOid = genOid == null ? null : genOid.trim();
    }

    public String getEventNo() {
        return eventNo;
    }

    public void setEventNo(String eventNo) {
        this.eventNo = eventNo == null ? null : eventNo.trim();
    }

    public String getOriginEventNo() {
        return originEventNo;
    }

    public void setOriginEventNo(String originEventNo) {
        this.originEventNo = originEventNo == null ? null : originEventNo.trim();
    }

    public Long getEventTypeInfoId() {
        return eventTypeInfoId;
    }

    public void setEventTypeInfoId(Long eventTypeInfoId) {
        this.eventTypeInfoId = eventTypeInfoId;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid == null ? null : oid.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public Date getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(Date creatTime) {
        this.creatTime = creatTime;
    }
}