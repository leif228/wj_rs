package com.wujie.ac.app.async.factory;

import com.wujie.ac.app.business.app.service.system.impl.SdsServiceImpl;
import com.wujie.ac.app.framework.util.spring.SpringContextUtil2;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.TimerTask;


/**
 * 异步工厂（产生任务用）
 *
 * @author liuhulu
 */
@Slf4j
public class AsyncFactory {
    private static final Logger logger = LoggerFactory.getLogger(AsyncFactory.class);

    /**
     * 更新货盘阅读数
     *
     * @return
     */
    public static TimerTask pushTaskHttp(final String ip, final String eventNo, final String oid, final String eventType, final String content, final String targetOid, final String bussInfoId) {
        return new TimerTask() {
            @Override
            public void run() {
                try {
                    log.info("异步开始了");
                    SdsServiceImpl atService = (SdsServiceImpl) SpringContextUtil2.getBean("sdsServiceImpl");
                    atService.pushTaskHttp(ip, eventNo, oid, eventType, content, targetOid, bussInfoId);
                    log.info("异步结束");
                } catch (Exception e) {
                    log.error("AsyncFactory报错了" + e.getMessage());
                }
            }
        };
    }
}
