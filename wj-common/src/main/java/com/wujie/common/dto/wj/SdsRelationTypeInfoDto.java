package com.wujie.common.dto.wj;

import java.util.Date;

public class SdsRelationTypeInfoDto {
    // id id
    private Long id;

    // 类型 类型：1、夫妻2、父母3、兄弟姐妹4、亲戚5、朋友6、员工...
    private Integer type;

    // 关系名称 如：夫妻
    private String relationName;

    // 关系权重（0~100） 关系的优先级，数字越大，越优先
    private Integer weight;

    // 更新人 关系更新人
    private Long updatedBy;

    // 更新时间 关系更新时间
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

    public String getRelationName() {
        return relationName;
    }

    public void setRelationName(String relationName) {
        this.relationName = relationName == null ? null : relationName.trim();
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