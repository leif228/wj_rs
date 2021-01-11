package com.wujie.mc.app.business.vo;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

/**
 * @ProjectName: 20200420_zc
 * @Package: com.duoqio.boot.business.vo
 * @ClassName: UserInfoVo
 * @Author: fanYang
 * @Description: jwt 不中的用户信息
 * @Date: 2020/4/21 17:28
 */
public class UserDetailsVo extends User implements Serializable {


    public UserDetailsVo(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    private Long id;

    //
    private String userName;

    //
    private String passWord;

    //
    private String idcard;

    private String owerIp;

    private String oid;

    //
    private String phone;

    //
    private String addr;

    // 0个人1团体
    private Integer userType;

    //
    private Date creatTime;

    public String getOwerIp() {
        return owerIp;
    }

    public void setOwerIp(String owerIp) {
        this.owerIp = owerIp;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

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
