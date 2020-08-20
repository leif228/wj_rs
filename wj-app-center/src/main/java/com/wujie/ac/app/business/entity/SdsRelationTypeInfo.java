package com.wujie.ac.app.business.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Table(name="sds_relation_type_info")
public class SdsRelationTypeInfo implements Serializable,Cloneable{
    /** id;id */
    @Id
    @GeneratedValue
    private Long id ;
    /** 类型;类型：1、夫妻2、父母3、兄弟姐妹4、亲戚5、朋友6、员工... */
    private Integer type ;
    /** 关系名称;如：夫妻 */
    private String relationName ;
    /** 关系权重（0~100）;关系的优先级，数字越大，越优先 */
    private Integer weight ;
    /** 更新人;关系更新人 */
    private Long updatedBy ;
    /** 更新时间;关系更新时间 */
    private Date updateTime ;

    /** id;id */
    public Long getId(){
        return this.id;
    }
    /** id;id */
    public void setId(Long id){
        this.id = id;
    }
    /** 类型;类型：1、夫妻2、父母3、兄弟姐妹4、亲戚5、朋友6、员工... */
    public Integer getType(){
        return this.type;
    }
    /** 类型;类型：1、夫妻2、父母3、兄弟姐妹4、亲戚5、朋友6、员工... */
    public void setType(Integer type){
        this.type = type;
    }
    /** 关系名称;如：夫妻 */
    public String getRelationName(){
        return this.relationName;
    }
    /** 关系名称;如：夫妻 */
    public void setRelationName(String relationName){
        this.relationName = relationName;
    }
    /** 关系权重（0~100）;关系的优先级，数字越大，越优先 */
    public Integer getWeight(){
        return this.weight;
    }
    /** 关系权重（0~100）;关系的优先级，数字越大，越优先 */
    public void setWeight(Integer weight){
        this.weight = weight;
    }
    /** 更新人;关系更新人 */
    public Long getUpdatedBy(){
        return this.updatedBy;
    }
    /** 更新人;关系更新人 */
    public void setUpdatedBy(Long updatedBy){
        this.updatedBy = updatedBy;
    }
    /** 更新时间;关系更新时间 */
    public Date getUpdateTime(){
        return this.updateTime;
    }
    /** 更新时间;关系更新时间 */
    public void setUpdateTime(Date updateTime){
        this.updateTime = updateTime;
    }
}
