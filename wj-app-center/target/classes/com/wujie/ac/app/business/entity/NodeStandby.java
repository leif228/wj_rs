package com.wujie.ac.app.business.entity;

import java.util.Date;

public class NodeStandby {
    //
    private Long id;

    // 关联node表id
    private Long nodeId;

    // 关联device表id(无用)
    private Long deviceId;

    // 0主服务1备份服务
    private Integer type;

    //
    private String ip;

    //
    private String port;

    //
    private Integer devtypeId;

    //
    private Date creatTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNodeId() {
        return nodeId;
    }

    public void setNodeId(Long nodeId) {
        this.nodeId = nodeId;
    }

    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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