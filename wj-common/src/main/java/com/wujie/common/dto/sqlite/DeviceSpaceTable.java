package com.wujie.common.dto.sqlite;

public class DeviceSpaceTable {
    private String data_id;
    private String devtype;
    private String dev_company;
    private String dev_company_SN;
    private String dev_room;
    private String dev_inROOMID;
    private String dev_company_devtype;

    public String getDev_company_devtype() {
        return dev_company_devtype;
    }

    public void setDev_company_devtype(String dev_company_devtype) {
        this.dev_company_devtype = dev_company_devtype;
    }

    public String getData_id() {
        return data_id;
    }

    public void setData_id(String data_id) {
        this.data_id = data_id;
    }

    public String getDevtype() {
        return devtype;
    }

    public void setDevtype(String devtype) {
        this.devtype = devtype;
    }

    public String getDev_company() {
        return dev_company;
    }

    public void setDev_company(String dev_company) {
        this.dev_company = dev_company;
    }

    public String getDev_company_SN() {
        return dev_company_SN;
    }

    public void setDev_company_SN(String dev_company_SN) {
        this.dev_company_SN = dev_company_SN;
    }

    public String getDev_room() {
        return dev_room;
    }

    public void setDev_room(String dev_room) {
        this.dev_room = dev_room;
    }

    public String getDev_inROOMID() {
        return dev_inROOMID;
    }

    public void setDev_inROOMID(String dev_inROOMID) {
        this.dev_inROOMID = dev_inROOMID;
    }
}
