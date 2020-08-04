package com.wujie.common.dto;

import java.io.Serializable;

public class DeviceVo implements Serializable {
    private Long parentNodeId;
    private String ip;
    private String port;
    private String loginFzwno;
    private String fzwno;
    private String owerIp;
    private String owerPort;
    private String owerFzwno;

    public Long getParentNodeId() {
        return parentNodeId;
    }

    public void setParentNodeId(Long parentNodeId) {
        this.parentNodeId = parentNodeId;
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

    public String getLoginFzwno() {
        return loginFzwno;
    }

    public void setLoginFzwno(String loginFzwno) {
        this.loginFzwno = loginFzwno;
    }

    public String getOwerIp() {
        return owerIp;
    }

    public void setOwerIp(String owerIp) {
        this.owerIp = owerIp;
    }

    public String getOwerPort() {
        return owerPort;
    }

    public void setOwerPort(String owerPort) {
        this.owerPort = owerPort;
    }

    public String getOwerFzwno() {
        return owerFzwno;
    }

    public void setOwerFzwno(String owerFzwno) {
        this.owerFzwno = owerFzwno;
    }
}
