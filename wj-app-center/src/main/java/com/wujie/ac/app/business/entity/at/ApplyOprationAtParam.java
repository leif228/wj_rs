package com.wujie.ac.app.business.entity.at;

import com.alibaba.fastjson.annotation.JSONField;
import com.wujie.ac.app.business.entity.wjhttp.BaseTask;

public class ApplyOprationAtParam extends BaseTask {

    @JSONField(name = "relative_event_no")
    private String relativeEventNo;
    @JSONField(name = "event_no")
    private String eventNo;
    @JSONField(name = "req_content")
    private String reqContent;
    @JSONField(name = "req_at")
    private String reqAt;
    @JSONField(name = "serial_id")
    private String serialId;
    @JSONField(name = "req_oid")
    private String reqOid;
    @JSONField(name = "res")
    private String res;

    public String getRes() {
        return res;
    }

    public void setRes(String res) {
        this.res = res;
    }

    public String getRelativeEventNo() {
        return relativeEventNo;
    }

    public void setRelativeEventNo(String relativeEventNo) {
        this.relativeEventNo = relativeEventNo;
    }

    public String getEventNo() {
        return eventNo;
    }

    public void setEventNo(String eventNo) {
        this.eventNo = eventNo;
    }

    public String getReqContent() {
        return reqContent;
    }

    public void setReqContent(String reqContent) {
        this.reqContent = reqContent;
    }

    public String getReqAt() {
        return reqAt;
    }

    public void setReqAt(String reqAt) {
        this.reqAt = reqAt;
    }

    public String getSerialId() {
        return serialId;
    }

    public void setSerialId(String serialId) {
        this.serialId = serialId;
    }

    public String getReqOid() {
        return reqOid;
    }

    public void setReqOid(String reqOid) {
        this.reqOid = reqOid;
    }
}
