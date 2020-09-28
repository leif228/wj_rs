package com.wujie.ac.app.business.entity;

import java.util.Date;

public class EventtypeTradecodeMdInfo {
    //
    private Long id;

    //
    private Long eventTypeInfoId;

    //
    private Long tradeCodeInfoId;

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

    public Long getTradeCodeInfoId() {
        return tradeCodeInfoId;
    }

    public void setTradeCodeInfoId(Long tradeCodeInfoId) {
        this.tradeCodeInfoId = tradeCodeInfoId;
    }

    public Date getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(Date creatTime) {
        this.creatTime = creatTime;
    }
}