package com.wujie.common.dto.wj;


import java.io.Serializable;
import java.util.Date;

public class DeviceTypeDto implements Serializable {
    //
    private Integer id;

    //
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}