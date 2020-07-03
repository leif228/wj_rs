package com.wujie.common.dto;

import java.io.Serializable;

public class DeviceVo implements Serializable {
    private String ip;
    private String port;
    private String fzwno;

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
}
