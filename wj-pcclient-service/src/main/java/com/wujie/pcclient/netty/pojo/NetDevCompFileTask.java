package com.wujie.pcclient.netty.pojo;

import com.alibaba.fastjson.annotation.JSONField;

public class NetDevCompFileTask extends NetDevCompTask {
    @JSONField(name="File")
    private String File;

    public String getFile() {
        return File;
    }

    public void setFile(String file) {
        File = file;
    }
}
