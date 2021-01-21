package com.wujie.mc.app.business.entity;

import java.io.Serializable;

public class AreaChangSeq implements Serializable {
    //
    private Integer id;

    //
    private String ascii10;

    //
    private String fzwStr;

    //
    private String oneTab;

    //
    private String twoTab;

    //
    private String threeTab;

    //
    private String remarks;

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

    public String getOneTab() {
        return oneTab;
    }

    public void setOneTab(String oneTab) {
        this.oneTab = oneTab == null ? null : oneTab.trim();
    }

    public String getTwoTab() {
        return twoTab;
    }

    public void setTwoTab(String twoTab) {
        this.twoTab = twoTab == null ? null : twoTab.trim();
    }

    public String getThreeTab() {
        return threeTab;
    }

    public void setThreeTab(String threeTab) {
        this.threeTab = threeTab == null ? null : threeTab.trim();
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
    }
}