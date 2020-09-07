package com.wujie.common.dto.wj;


import java.io.Serializable;

public class UserListDto implements Serializable {

    //
    private String userName;
    private String relation;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }
}