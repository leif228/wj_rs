package com.wujie.hc.app.business.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name="wjuser")
public class Wjuser implements Serializable {
    //
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    //
    @Column(name = "user_name")
    private String userName;

    //
    @Column(name = "pass_word")
    private String passWord;

    //
    @Column(name = "idcard")
    private String idcard;

    //
    @Column(name = "phone")
    private String phone;

    //
    @Column(name = "addr")
    private String addr;

    // 0个人1团体
    @Column(name = "user_type")
    private Integer userType;

    @Column(name = "p_sort")
    private Integer pSort;

    @Column(name = "c_sort")
    private Integer cSort;

    @Column(name = "a_sort")
    private Integer aSort;

    @Column(name = "s_sort")
    private Integer sSort;

    //
    @Column(name = "creat_time")
    private Date creatTime;

  }