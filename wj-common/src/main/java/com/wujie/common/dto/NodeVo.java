package com.wujie.common.dto;

import com.wujie.common.dto.wj.NodeDto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class NodeVo extends NodeDto implements Serializable {
    private String ip;
    private String port;
    private int layer;
    /** 子级分类 */
    private List<NodeVo> children = new ArrayList<>();

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public int getLayer() {
        return layer;
    }

    public void setLayer(int layer) {
        this.layer = layer;
    }

    public List<NodeVo> getChildren() {
        return children;
    }

    public void setChildren(List<NodeVo> children) {
        this.children = children;
    }
}
