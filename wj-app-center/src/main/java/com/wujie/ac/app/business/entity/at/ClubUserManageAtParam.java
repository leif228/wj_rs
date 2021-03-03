package com.wujie.ac.app.business.entity.at;

import com.alibaba.fastjson.annotation.JSONField;
import com.wujie.ac.app.business.entity.wjhttp.BaseTask;

public class ClubUserManageAtParam extends BaseTask {

    @JSONField(name = "event_no")
    private String eventNo;
    @JSONField(name = "oid")
    private String oid;
    @JSONField(name = "msg_type")
    private String msgType;   //消息类型:add;dec;加、减

    public String getEventNo() {
        return eventNo;
    }

    public void setEventNo(String eventNo) {
        this.eventNo = eventNo;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }
}
