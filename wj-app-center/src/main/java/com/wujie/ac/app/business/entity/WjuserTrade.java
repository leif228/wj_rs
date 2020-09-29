package com.wujie.ac.app.business.entity;

import java.util.Date;

public class WjuserTrade {
    //
    private Long id;

    //
    private String oid;

    // trade表ids 如：1,3,34
    private String trades;

    //
    private Date updataTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid == null ? null : oid.trim();
    }

    public String getTrades() {
        return trades;
    }

    public void setTrades(String trades) {
        this.trades = trades == null ? null : trades.trim();
    }

    public Date getUpdataTime() {
        return updataTime;
    }

    public void setUpdataTime(Date updataTime) {
        this.updataTime = updataTime;
    }
}