package com.wujie.ac.app.business.app.controller;

import com.wujie.ac.app.business.app.service.system.AtService;
import com.wujie.ac.app.business.app.service.system.SdsService;
import com.wujie.common.base.ApiResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName: SdsController
 * @Description: sds控制层
 * @Date: 2020/4/21 14:27
 */
@RestController
public class AtController {

    private AtService atService;

    @Autowired
    public AtController(AtService atService) {
        this.atService = atService;
    }

    @PostMapping("/doAtTask")
    public ApiResult doAtTask(@RequestParam(value = "tx") String tx) {
        return atService.doAtTask(tx);
    }

}