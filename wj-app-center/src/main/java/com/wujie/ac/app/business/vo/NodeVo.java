package com.wujie.ac.app.business.vo;

import com.wujie.ac.app.business.entity.Node;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class NodeVo extends Node implements Serializable {
    private String ip;
    private String port;
    private int layer;
    /** 子级分类 */
    private List<NodeVo> children = new ArrayList<>();

 }
