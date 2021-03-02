package com.wujie.tc.netty.pojo;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

public class LoginTask implements Serializable {
    @JSONField(name="OID")
    private String OID;
    @JSONField(name="PassNum")
    private String PassNum;

    public String getOID() {
        return OID;
    }

    public void setOID(String OID) {
        this.OID = OID;
    }

    public String getPassNum() {
        return PassNum;
    }

    public void setPassNum(String passNum) {
        PassNum = passNum;
    }
}
