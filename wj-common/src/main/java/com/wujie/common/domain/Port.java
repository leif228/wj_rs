package com.wujie.common.domain;

import com.wujie.common.base.BaseEntity;

public class Port extends BaseEntity {

    private String fullPortName;

    private Double latitude;

    private Double longitude;

    private String portName;

    private String portNo;

    private String engPortName;

    private static final long serialVersionUID = 1L;

    public String getFullPortName() {
        return fullPortName;
    }

    public void setFullPortName(String fullPortName) {
        this.fullPortName = fullPortName == null ? null : fullPortName.trim();
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getPortName() {
        return portName;
    }

    public void setPortName(String portName) {
        this.portName = portName == null ? null : portName.trim();
    }

    public String getPortNo() {
        return portNo;
    }

    public void setPortNo(String portNo) {
        this.portNo = portNo == null ? null : portNo.trim();
    }

    public String getEngPortName() {
        return engPortName;
    }

    public void setEngPortName(String engPortName) {
        this.engPortName = engPortName == null ? null : engPortName.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(getId());
        sb.append(", fullPortName=").append(fullPortName);
        sb.append(", latitude=").append(latitude);
        sb.append(", longitude=").append(longitude);
        sb.append(", portName=").append(portName);
        sb.append(", portNo=").append(portNo);
        sb.append(", engPortName=").append(engPortName);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}