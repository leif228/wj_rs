package com.wujie.ac.app.business.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Table(name="sds_event_type_info")
public class SdsEventTypeInfo implements Serializable,Cloneable{
    /** id;id */
    @Id
    @GeneratedValue
    private Long id ;
    /** 类型;类型1、健康事件... */
    private Integer type ;
    /** 事件名称;健康事件 */
    private String eventName ;
    /** 事件关系阀值（0~100）;事件对应关系表权重值（取关系权重值大于该值为该事件相关关系） */
    private Integer eventRelationLevel ;
    /** 事件权重（0~100）;事件的优先级，数字越大，越优先 */
    private Integer weight ;
    /** 更新人;事件更新人 */
    private Long updatedBy ;
    /** 更新时间;事件更新时间 */
    private Date updateTime ;

    /** id;id */
    public Long getId(){
        return this.id;
    }
    /** id;id */
    public void setId(Long id){
        this.id = id;
    }
    /** 类型;类型1、健康事件... */
    public Integer getType(){
        return this.type;
    }
    /** 类型;类型1、健康事件... */
    public void setType(Integer type){
        this.type = type;
    }
    /** 事件名称;健康事件 */
    public String getEventName(){
        return this.eventName;
    }
    /** 事件名称;健康事件 */
    public void setEventName(String eventName){
        this.eventName = eventName;
    }
    /** 事件关系阀值（0~100）;事件对应关系表权重值（取关系权重值大于该值为该事件相关关系） */
    public Integer getEventRelationLevel(){
        return this.eventRelationLevel;
    }
    /** 事件关系阀值（0~100）;事件对应关系表权重值（取关系权重值大于该值为该事件相关关系） */
    public void setEventRelationLevel(Integer eventRelationLevel){
        this.eventRelationLevel = eventRelationLevel;
    }
    /** 事件权重（0~100）;事件的优先级，数字越大，越优先 */
    public Integer getWeight(){
        return this.weight;
    }
    /** 事件权重（0~100）;事件的优先级，数字越大，越优先 */
    public void setWeight(Integer weight){
        this.weight = weight;
    }
    /** 更新人;事件更新人 */
    public Long getUpdatedBy(){
        return this.updatedBy;
    }
    /** 更新人;事件更新人 */
    public void setUpdatedBy(Long updatedBy){
        this.updatedBy = updatedBy;
    }
    /** 更新时间;事件更新时间 */
    public Date getUpdateTime(){
        return this.updateTime;
    }
    /** 更新时间;事件更新时间 */
    public void setUpdateTime(Date updateTime){
        this.updateTime = updateTime;
    }

}
