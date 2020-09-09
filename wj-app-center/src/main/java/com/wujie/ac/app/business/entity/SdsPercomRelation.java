package com.wujie.ac.app.business.entity;

public class SdsPercomRelation {
    // id
    private Long id;

    // 自己oid
    private String selfOid;

    // 目标oid
    private String targetOid;

    // 目标oid管理服务器ip
    private String targetOwerIp;

    // 关系类型表id
    private Long relationTypeInfoId;

    // 联系度（0~10000动态变化）
    private Integer weight;

    // 昵称
    private String nickname;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSelfOid() {
        return selfOid;
    }

    public void setSelfOid(String selfOid) {
        this.selfOid = selfOid == null ? null : selfOid.trim();
    }

    public String getTargetOid() {
        return targetOid;
    }

    public void setTargetOid(String targetOid) {
        this.targetOid = targetOid == null ? null : targetOid.trim();
    }

    public String getTargetOwerIp() {
        return targetOwerIp;
    }

    public void setTargetOwerIp(String targetOwerIp) {
        this.targetOwerIp = targetOwerIp == null ? null : targetOwerIp.trim();
    }

    public Long getRelationTypeInfoId() {
        return relationTypeInfoId;
    }

    public void setRelationTypeInfoId(Long relationTypeInfoId) {
        this.relationTypeInfoId = relationTypeInfoId;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname == null ? null : nickname.trim();
    }

    @Override
    public String toString() {
        return "SdsPercomRelation{" +
                "id=" + id +
                ", selfOid='" + selfOid + '\'' +
                ", targetOid='" + targetOid + '\'' +
                ", targetOwerIp='" + targetOwerIp + '\'' +
                ", relationTypeInfoId=" + relationTypeInfoId +
                ", weight=" + weight +
                ", nickname='" + nickname + '\'' +
                '}';
    }
}