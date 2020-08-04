package com.wujie.ac.app.business.entity;

import java.util.Date;

public class LoginServer {
    //
    private Long id;

    //
    private String oid;

    //
    private String serverIp;

    //
    private String serverPort;

    //
    private String serverOid;

    //
    private String owerServerOid;

    //
    private Date creatTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid == null ? null : oid.trim();
    }

    public String getServerIp() {
        return serverIp;
    }

    public void setServerIp(String serverIp) {
        this.serverIp = serverIp == null ? null : serverIp.trim();
    }

    public String getServerPort() {
        return serverPort;
    }

    public void setServerPort(String serverPort) {
        this.serverPort = serverPort == null ? null : serverPort.trim();
    }

    public String getServerOid() {
        return serverOid;
    }

    public void setServerOid(String serverOid) {
        this.serverOid = serverOid == null ? null : serverOid.trim();
    }

    public String getOwerServerOid() {
        return owerServerOid;
    }

    public void setOwerServerOid(String owerServerOid) {
        this.owerServerOid = owerServerOid == null ? null : owerServerOid.trim();
    }

    public Date getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(Date creatTime) {
        this.creatTime = creatTime;
    }
}