package com.wujie.ac.app.business.entity.wjhttp;

import com.alibaba.fastjson.annotation.JSONField;

public class OwerLogin extends BaseTask{

    @JSONField(name="OID")
    String OID;
    @JSONField(name="ServerIP")
    String ServerIP;
    @JSONField(name="ServerPort")
    String ServerPort;
    @JSONField(name="ServerOID")
    String ServerOID;
    @JSONField(name="OwnerServerOID")
    String OwnerServerOID;

    public String getOID() {
        return OID;
    }

    public void setOID(String OID) {
        this.OID = OID;
    }

    public String getServerIP() {
        return ServerIP;
    }

    public void setServerIP(String serverIP) {
        ServerIP = serverIP;
    }

    public String getServerPort() {
        return ServerPort;
    }

    public void setServerPort(String serverPort) {
        ServerPort = serverPort;
    }

    public String getServerOID() {
        return ServerOID;
    }

    public void setServerOID(String serverOID) {
        ServerOID = serverOID;
    }

    public String getOwnerServerOID() {
        return OwnerServerOID;
    }

    public void setOwnerServerOID(String ownerServerOID) {
        OwnerServerOID = ownerServerOID;
    }

    @Override
    public String toString() {
        return "OwerLogin{" +
                "OID='" + OID + '\'' +
                ", ServerIP='" + ServerIP + '\'' +
                ", ServerPort='" + ServerPort + '\'' +
                ", ServerOID='" + ServerOID + '\'' +
                ", OwnerServerOID='" + OwnerServerOID + '\'' +
                '}';
    }
}
