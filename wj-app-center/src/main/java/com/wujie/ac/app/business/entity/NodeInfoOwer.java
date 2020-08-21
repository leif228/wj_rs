package com.wujie.ac.app.business.entity;

import java.util.Date;

public class NodeInfoOwer {
    //
    private Long id;

    //
    private String ip;

    //
    private String port;

    //
    private String fzwno;

    //
    private Integer deviceType;

    //
    private String deviceName;

    //
    private Date creatTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port == null ? null : port.trim();
    }

    public String getFzwno() {
        return fzwno;
    }

    public void setFzwno(String fzwno) {
        this.fzwno = fzwno == null ? null : fzwno.trim();
    }

    public Integer getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(Integer deviceType) {
        this.deviceType = deviceType;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName == null ? null : deviceName.trim();
    }

    public Date getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(Date creatTime) {
        this.creatTime = creatTime;
    }
}