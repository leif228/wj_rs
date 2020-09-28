package com.wujie.ac.app.business.entity;

import java.io.Serializable;
import java.util.Date;

public class WjuserOwer implements Serializable {
    //
    private Long id;

    //
    private String userName;

    //
    private String passWord;

    //
    private String idcard;

    //
    private String oid;

    //
    private String owerIp;

    //
    private String phone;

    //
    private String addr;

    //
    private String headIconUrl;

    //
    private String major;

    //
    private String sex;

    // 0个人1团体
    private Integer userType;

    //
    private Integer pSort;

    //
    private Integer cSort;

    //
    private Integer aSort;

    //
    private Integer sSort;

    // trade表ids 如：1,3,34
    private String trades;

    //
    private Date creatTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord == null ? null : passWord.trim();
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard == null ? null : idcard.trim();
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid == null ? null : oid.trim();
    }

    public String getOwerIp() {
        return owerIp;
    }

    public void setOwerIp(String owerIp) {
        this.owerIp = owerIp == null ? null : owerIp.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr == null ? null : addr.trim();
    }

    public String getHeadIconUrl() {
        return headIconUrl;
    }

    public void setHeadIconUrl(String headIconUrl) {
        this.headIconUrl = headIconUrl == null ? null : headIconUrl.trim();
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major == null ? null : major.trim();
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex == null ? null : sex.trim();
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public Integer getpSort() {
        return pSort;
    }

    public void setpSort(Integer pSort) {
        this.pSort = pSort;
    }

    public Integer getcSort() {
        return cSort;
    }

    public void setcSort(Integer cSort) {
        this.cSort = cSort;
    }

    public Integer getaSort() {
        return aSort;
    }

    public void setaSort(Integer aSort) {
        this.aSort = aSort;
    }

    public Integer getsSort() {
        return sSort;
    }

    public void setsSort(Integer sSort) {
        this.sSort = sSort;
    }

    public String getTrades() {
        return trades;
    }

    public void setTrades(String trades) {
        this.trades = trades == null ? null : trades.trim();
    }

    public Date getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(Date creatTime) {
        this.creatTime = creatTime;
    }
}