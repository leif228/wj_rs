package com.wujie.ac.app.business.app.controller;

import com.wujie.ac.app.business.app.service.system.SdsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName: SdsController
 * @Description: sds控制层
 * @Date: 2020/4/21 14:27
 */
@RestController
public class SdsController {

    private SdsService sdsService;

    @Autowired
    public SdsController(SdsService sdsService) {
        this.sdsService = sdsService;
    }
}