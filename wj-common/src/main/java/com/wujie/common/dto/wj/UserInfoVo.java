package com.wujie.common.dto.wj;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UserInfoVo implements Serializable {
    String msg = "";
    List<UserDeviceDto> deviceDtoList = new ArrayList<>();

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<UserDeviceDto> getDeviceDtoList() {
        return deviceDtoList;
    }

    public void setDeviceDtoList(List<UserDeviceDto> deviceDtoList) {
        this.deviceDtoList = deviceDtoList;
    }
}
