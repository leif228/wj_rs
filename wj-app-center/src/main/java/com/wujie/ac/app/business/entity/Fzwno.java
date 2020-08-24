package com.wujie.ac.app.business.entity;

import java.util.Date;

public class Fzwno {
    //
    private Long id;

    //
    private String fzwRelation;

    //
    private String fzwDevice;

    //
    private Integer devtypeId;

    private String deviceName;

    //
    private Date creatTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
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