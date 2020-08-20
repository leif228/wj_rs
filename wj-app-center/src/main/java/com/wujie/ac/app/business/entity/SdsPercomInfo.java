package com.wujie.ac.app.business.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Table(name="sds_percom_info")
public class SdsPercomInfo implements Serializable,Cloneable{
    /** id;id */
    @Id
    @GeneratedValue
    private Long id ;
    /** 类别;1、个人2、组织 */
    private Integer type ;
    /** oid关系段;oid */
    private String selfOid ;
    /** 名称 */
    private String name ;
    /** 性别 */
    private String sex ;
    /** 电话 */
    private String phone ;
    /** 身份唯一标识 */
    private String card ;
    /** 地址 */
    private String address ;
    /** 头像路径 */
    private String headIconUrl ;
    /** 主业 */
    private String major ;

    /** id;id */
    public Long getId(){
        return this.id;
    }
    /** id;id */
    public void setId(Long id){
        this.id = id;
    }
    /** 类别;1、个人2、组织 */
    public Integer getType(){
        return this.type;
    }
    /** 类别;1、个人2、组织 */
    public void setType(Integer type){
        this.type = type;
    }
    /** oid关系段;oid */
    public String getSelfOid(){
        return this.selfOid;
    }
    /** oid关系段;oid */
    public void setSelfOid(String selfOid){
        this.selfOid = selfOid;
    }
    /** 名称 */
    public String getName(){
        return this.name;
    }
    /** 名称 */
    public void setName(String name){
        this.name = name;
    }
    /** 性别 */
    public String getSex(){
        return this.sex;
    }
    /** 性别 */
    public void setSex(String sex){
        this.sex = sex;
    }
    /** 电话 */
    public String getPhone(){
        return this.phone;
    }
    /** 电话 */
    public void setPhone(String phone){
        this.phone = phone;
    }
    /** 身份唯一标识 */
    public String getCard(){
        return this.card;
    }
    /** 身份唯一标识 */
    public void setCard(String card){
        this.card = card;
    }
    /** 地址 */
    public String getAddress(){
        return this.address;
    }
    /** 地址 */
    public void setAddress(String address){
        this.address = address;
    }
    /** 头像路径 */
    public String getHeadIconUrl(){
        return this.headIconUrl;
    }
    /** 头像路径 */
    public void setHeadIconUrl(String headIconUrl){
        this.headIconUrl = headIconUrl;
    }
    /** 主业 */
    public String getMajor(){
        return this.major;
    }
    /** 主业 */
    public void setMajor(String major){
        this.major = major;
    }
}
