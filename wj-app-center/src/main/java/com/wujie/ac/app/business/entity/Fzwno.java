package com.wujie.ac.app.business.entity;

import java.util.Date;

public class Fzwno {
    //
    private Integer id;

    //
    private String fzwRelation;

    //
    private String fzwDevice;

    //
    private Integer devtypeId;

    //
    private Date creatTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFzwRelation() {
        return fzwRelation;
    }

    public void setFzwRelation(String fzwRelation) {
        this.fzwRelation = fzwRelation == null ? null : fzwRelation.trim();
    }

    public String getFzwDevice() {
        return fzwDevice;
    }

    public void setFzwDevice(String fzwDevice) {
        this.fzwDevice = fzwDevice == null ? null : fzwDevice.trim();
    }

    public Integer getDevtypeId() {
        return devtypeId;
    }

    public void setDevtypeId(Integer devtypeId) {
        this.devtypeId = devtypeId;
    }

    public Date getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(Date creatTime) {
        this.creatTime = creatTime;
    }
}