package com.wujie.ac.app.business.entity;

import java.util.Date;

public class SdsEventTypeInfo {
    // id id
    private Long id;

    // 类型 类型1、健康事件...
    private Integer type;

    // 事件名称 健康事件
    private String eventName;

    // 事件关系阀值（0~100） 事件对应关系表权重值（取关系权重值大于该值为该事件相关关系）
    private Integer eventRelationLevel;

    // 事件权重（0~100） 事件的优先级，数字越大，越优先
    private Integer weight;

    // 更新人 事件更新人
    private Long updatedBy;

    // 更新时间 事件更新时间
    private Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName == null ? null : eventName.trim();
    }

    public Integer getEventRelationLevel() {
        return eventRelationLevel;
    }

    public void setEventRelationLevel(Integer eventRelationLevel) {
        this.eventRelationLevel = eventRelationLevel;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}