package com.wujie.common.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * @ProjectName: 20200420_zc
 * @Package: com.duoqio.boot.business.vo
 * @ClassName: UserInfoVo
 * @Author: fanYang
 * @Description: jwt 不中的用户信息
 * @Date: 2020/4/21 17:28
 */
public class ComUserDetailsVo implements Serializable {


    private Long id;

    //
    private String userName;

    //
    private String passWord;

    //
    private String idcard;

    //
    private String phone;

    //
    private String addr;

    // 0个人1团体
    private Integer userType;

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
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public Date getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(Date creatTime) {
        this.creatTime = creatTime;
    }
}
