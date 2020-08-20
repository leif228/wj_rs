package com.wujie.ac.app.business.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Table(name="sds_percom_relation")
public class SdsPercomRelation implements Serializable,Cloneable{
    /** id */
    @Id
    @GeneratedValue
    private Long id ;
    /** 自己oid */
    private String selfOid ;
    /** 目标oid */
    private String targetOid ;
    /** 关系类型表id */
    private Long relationTypeInfoId ;
    /** 联系度（0~10000动态变化） */
    private Integer weight ;
    /** 昵称 */
    private String nickname ;

    /** id */
    public Long getId(){
        return this.id;
    }
    /** id */
    public void setId(Long id){
        this.id = id;
    }
    /** 自己oid */
    public String getSelfOid(){
        return this.selfOid;
    }
    /** 自己oid */
    public void setSelfOid(String selfOid){
        this.selfOid = selfOid;
    }
    /** 目标oid */
    public String getTargetOid(){
        return this.targetOid;
    }
    /** 目标oid */
    public void setTargetOid(String targetOid){
        this.targetOid = targetOid;
    }
    /** 关系类型表id */
    public Long getRelationTypeInfoId(){
        return this.relationTypeInfoId;
    }
    /** 关系类型表id */
    public void setRelationTypeInfoId(Long relationTypeInfoId){
        this.relationTypeInfoId = relationTypeInfoId;
    }
    /** 联系度（0~10000动态变化） */
    public Integer getWeight(){
        return this.weight;
    }
    /** 联系度（0~10000动态变化） */
    public void setWeight(Integer weight){
        this.weight = weight;
    }
    /** 昵称 */
    public String getNickname(){
        return this.nickname;
    }
    /** 昵称 */
    public void setNickname(String nickname){
        this.nickname = nickname;
    }
}
