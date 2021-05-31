package com.wujie.ac.app.business.entity;

public class ComDevOpt {
    //
    private Long id;

    // devtype设备类型
    private String devtype;

    // dev_company设备厂商
    private String devCompany;

    // dev_company_name设备厂商名
    private String devCompanyName;

    // dev_company_devtype设备厂商型号
    private String devCompanyDevtype;

    // dev_company_devtype_name设备名
    private String devCompanyDevtypeName;

    // buss业务
    private String buss;

    // ability_name功能名
    private String abilityName;

    // way方式
    private String way;

    // port_max端口数
    private String portMax;

    // at_para AT命令中的参数
    private String atPara;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDevtype() {
        return devtype;
    }

    public void setDevtype(String devtype) {
        this.devtype = devtype == null ? null : devtype.trim();
    }

    public String getDevCompany() {
        return devCompany;
    }

    public void setDevCompany(String devCompany) {
        this.devCompany = devCompany == null ? null : devCompany.trim();
    }

    public String getDevCompanyName() {
        return devCompanyName;
    }

    public void setDevCompanyName(String devCompanyName) {
        this.devCompanyName = devCompanyName == null ? null : devCompanyName.trim();
    }

    public String getDevCompanyDevtype() {
        return devCompanyDevtype;
    }

    public void setDevCompanyDevtype(String devCompanyDevtype) {
        this.devCompanyDevtype = devCompanyDevtype == null ? null : devCompanyDevtype.trim();
    }

    public String getDevCompanyDevtypeName() {
        return devCompanyDevtypeName;
    }

    public void setDevCompanyDevtypeName(String devCompanyDevtypeName) {
        this.devCompanyDevtypeName = devCompanyDevtypeName == null ? null : devCompanyDevtypeName.trim();
    }

    public String getBuss() {
        return buss;
    }

    public void setBuss(String buss) {
        this.buss = buss == null ? null : buss.trim();
    }

    public String getAbilityName() {
        return abilityName;
    }

    public void setAbilityName(String abilityName) {
        this.abilityName = abilityName == null ? null : abilityName.trim();
    }

    public String getWay() {
        return way;
    }

    public void setWay(String way) {
        this.way = way == null ? null : way.trim();
    }

    public String getPortMax() {
        return portMax;
    }

    public void setPortMax(String portMax) {
        this.portMax = portMax == null ? null : portMax.trim();
    }

    public String getAtPara() {
        return atPara;
    }

    public void setAtPara(String atPara) {
        this.atPara = atPara == null ? null : atPara.trim();
    }
}