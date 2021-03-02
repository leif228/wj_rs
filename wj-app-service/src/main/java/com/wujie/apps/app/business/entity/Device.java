package com.wujie.apps.app.business.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name="device")
public class Device implements Serializable {
    //
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    // user表id
    @Column(name = "user_id")
    private Long userId;

    // 0通用服务器1网关
    @Column(name = "device_type")
    private Integer deviceType;

    //
    @Column(name = "device_name")
    private String deviceName;

    //
    @Column(name = "ip")
    private String ip;

    //
    @Column(name = "port")
    private String port;

    @Column(name = "fzwno")
    private String fzwno;

    //
    @Column(name = "creat_time")
    private Date creatTime;
}