package com.wujie.common.dto.wj;

import java.io.Serializable;
import java.util.Date;

public class SdsEventListDto implements Serializable {
    private String eventNo;
    private String status;

    //
    private String eventName;

    //
    private Date creatTime;

    public String getEventNo() {
        return eventNo;
    }

    public void setEventNo(String eventNo) {
        this.eventNo = eventNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public Date getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(Date creatTime) {
        this.creatTime = creatTime;
    }
}