package com.wujie.ac.app.business.entity;

import java.io.Serializable;

public class AreaChangSeq implements Serializable {
    //
    private Integer id;

    //
    private String ascii10;

    //
    private String fzwStr;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAscii10() {
        return ascii10;
    }

    public void setAscii10(String ascii10) {
        this.ascii10 = ascii10 == null ? null : ascii10.trim();
    }

    public String getFzwStr() {
        return fzwStr;
    }

    public void setFzwStr(String fzwStr) {
        this.fzwStr = fzwStr == null ? null : fzwStr.trim();
    }
}