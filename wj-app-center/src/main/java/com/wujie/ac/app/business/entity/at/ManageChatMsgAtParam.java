package com.wujie.ac.app.business.entity.at;

import com.alibaba.fastjson.annotation.JSONField;
import com.wujie.ac.app.business.entity.wjhttp.BaseTask;

public class ManageChatMsgAtParam extends BaseTask {

    @JSONField(name="event_no")
    private String eventNo;
    @JSONField(name="msg_content")
    private String msg;
    @JSONField(name="msg_type")
    private String msgType;

    public String getEventNo() {
        return eventNo;
    }

    public void setEventNo(String eventNo) {
        this.eventNo = eventNo;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }
}
