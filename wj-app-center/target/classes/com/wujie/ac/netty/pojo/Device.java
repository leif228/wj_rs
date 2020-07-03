package com.wujie.ac.netty.pojo;

import lombok.Data;

@Data
public class Device {
    //
    private String uniqueNo;

    //
    private String name;

    public Device(String uniqueNo, String name) {
        this.uniqueNo = uniqueNo;
        this.name = name;
    }
}
