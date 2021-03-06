package com.wujie.mc.app.business.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name="node")
public class Node implements Serializable {
    //
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    //
    @Column(name = "name")
    private String name;

    //
    @Column(name = "lft")
    private Long lft;

    //
    @Column(name = "rgt")
    private Long rgt;

    //
    @Column(name = "creat_time")
    private Date creatTime;

}