package com.wujie.common.dto.wj;

import java.io.Serializable;
import java.util.Date;

public class UserDeviceDto implements Serializable {

    private String lastIp;

    private String fzwnoDev;

    public String getLastIp() {
        return lastIp;
    }

    public void setLastIp(String lastIp) {
        this.lastIp = lastIp;
    }

    public String getFzwnoDev() {
        return fzwnoDev;
    }

    public void setFzwnoDev(String fzwnoDev) {
        this.fzwnoDev = fzwnoDev;
    }
}