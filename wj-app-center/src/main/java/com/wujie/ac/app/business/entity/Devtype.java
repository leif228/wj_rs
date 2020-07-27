package com.wujie.ac.app.business.entity;

public class Devtype {
    //
    private Integer id;

    //
    private String devType;

    //
    private String devTypeNum;

    //
    private String mainNum;

    //
    private String sonNum;

    //
    private String subNum;

    //
    private String endNum;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDevType() {
        return devType;
    }

    public void setDevType(String devType) {
        this.devType = devType == null ? null : devType.trim();
    }

    public String getDevTypeNum() {
        return devTypeNum;
    }

    public void setDevTypeNum(String devTypeNum) {
        this.devTypeNum = devTypeNum == null ? null : devTypeNum.trim();
    }

    public String getMainNum() {
        return mainNum;
    }

    public void setMainNum(String mainNum) {
        this.mainNum = mainNum == null ? null : mainNum.trim();
    }

    public String getSonNum() {
        return sonNum;
    }

    public void setSonNum(String sonNum) {
        this.sonNum = sonNum == null ? null : sonNum.trim();
    }

    public String getSubNum() {
        return subNum;
    }

    public void setSubNum(String subNum) {
        this.subNum = subNum == null ? null : subNum.trim();
    }

    public String getEndNum() {
        return endNum;
    }

    public void setEndNum(String endNum) {
        this.endNum = endNum == null ? null : endNum.trim();
    }
}