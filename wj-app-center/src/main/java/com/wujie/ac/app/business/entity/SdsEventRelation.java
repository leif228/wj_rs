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
    private String eventTradeOids;

    private String eventManualOids;

    //
    private String status;

    //
    private Date updateTime;

    public String getEventManualOids() {
        return eventManualOids;
    }

    public void setEventManualOids(String eventManualOids) {
        this.eventManualOids = eventManualOids;
    }

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

    public String getEventTradeOids() {
        return eventTradeOids;
    }

    public void setEventTradeOids(String eventTradeOids) {
        this.eventTradeOids = eventTradeOids == null ? null : eventTradeOids.trim();
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