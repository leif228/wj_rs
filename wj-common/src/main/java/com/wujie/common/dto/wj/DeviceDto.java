package com.wujie.common.dto.wj;

import java.io.Serializable;
import java.util.Date;

public class DeviceDto implements Serializable {
    //
    private Long id;

    // user表id
    private Long userId;

    // 0通用服务器1网关
    private Integer deviceType;

    //
    private String deviceName;

    //
    private String ip;

    //
    private String port;

    private String fzwno;

    //
    private Date creatTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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
        this.deviceName = deviceName;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getFzwno() {
        return fzwno;
    }

    public void setFzwno(String fzwno) {
        this.fzwno = fzwno;
    }

    public Date getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(Date creatTime) {
        this.creatTime = creatTime;
    }
}