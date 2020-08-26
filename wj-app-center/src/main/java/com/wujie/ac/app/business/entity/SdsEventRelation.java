package com.wujie.ac.app.business.entity;

import java.util.Date;

public class SdsEventRelation {
    //
    private Long id;

    //
    private String eventNo;

    //
    private String eventRelationJson;

    //
    private String status;

    //
    private Date updateTime;

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

    public String getEventRelationJson() {
        return eventRelationJson;
    }

    public void setEventRelationJson(String eventRelationJson) {
        this.eventRelationJson = eventRelationJson == null ? null : eventRelationJson.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}