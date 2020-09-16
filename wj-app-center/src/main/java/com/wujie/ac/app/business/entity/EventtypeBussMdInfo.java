package com.wujie.ac.app.business.entity;

import java.util.Date;

public class EventtypeBussMdInfo {
    //
    private Long id;

    //
    private Long eventTypeInfoId;

    //
    private Long bussId;

    //
    private Date creatTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEventTypeInfoId() {
        return eventTypeInfoId;
    }

    public void setEventTypeInfoId(Long eventTypeInfoId) {
        this.eventTypeInfoId = eventTypeInfoId;
    }

    public Long getBussId() {
        return bussId;
    }

    public void setBussId(Long bussId) {
        this.bussId = bussId;
    }

    public Date getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(Date creatTime) {
        this.creatTime = creatTime;
    }
}