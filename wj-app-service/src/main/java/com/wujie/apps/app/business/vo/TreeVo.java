package com.wujie.apps.app.business.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class TreeVo implements Serializable {
    private String name;
    private List<com.wujie.apps.app.business.vo.TreeVo> children = new ArrayList<>();
 }
