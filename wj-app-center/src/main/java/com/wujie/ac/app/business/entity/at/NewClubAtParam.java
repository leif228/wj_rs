package com.wujie.ac.app.business.entity.at;

import com.alibaba.fastjson.annotation.JSONField;
import com.wujie.ac.app.business.entity.wjhttp.BaseTask;

public class NewClubAtParam extends BaseTask {

    @JSONField(name = "relative_event_no")
    private String relativeEventNo;

    public String getRelativeEventNo() {
        return relativeEventNo;
    }

    public void setRelativeEventNo(String relativeEventNo) {
        this.relativeEventNo = relativeEventNo;
    }
}
