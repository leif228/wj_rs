package com.wujie.ac.app.business.entity;

import java.util.Date;

public class SdsEventInfo {
    //
    private Long id;

    //
    private String eventNo;

    //
    private Long eventTypeInfoId;

    //
    private String oid;

    //
    private String content;

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

    public String getEventNo() {
        return eventNo;
    }

    public void setEventNo(String eventNo) {
        this.eventNo = eventNo == null ? null : eventNo.trim();
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
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