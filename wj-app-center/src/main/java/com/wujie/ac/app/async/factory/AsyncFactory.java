package com.wujie.ac.app.async.factory;

import com.wujie.ac.app.business.app.service.system.impl.SdsServiceImpl;
import com.wujie.ac.app.framework.util.spring.SpringContextUtil2;
import lombok.extern.slf4j.Slf4j;

import java.util.TimerTask;


/**
 * 异步工厂（产生任务用）
 *
 * @author liuhulu
 */
@Slf4j
public class AsyncFactory {

    public static TimerTask pushTaskHttp(final String ip, final String eventNo, final String oid, final String eventType, final String content, final String targetOid, final String bussInfoId, String flag, String pri, String port) {
        return new TimerTask() {
            @Override
            public void run() {
                try {
                    log.info("pushTaskHttp异步开始了");
                    SdsServiceImpl atService = (SdsServiceImpl) SpringContextUtil2.getBean("sdsServiceImpl");
                    atService.pushTaskHttp(ip, eventNo, oid, eventType, content, targetOid, bussInfoId,flag,pri,port);
                    log.info("pushTaskHttp异步结束");
                } catch (Exception e) {
                    log.info("AsyncFactory.pushTaskHttp报错了" + e.getMessage());
                }
            }
        };
    }

    public static TimerTask pushDoEventWriteHttp(final String ip, final String oid, final String eventType, final String content, final String eventNo, final String bussInfoId, String flag, String pri, String port) {
        return new TimerTask() {
            @Override
            public void run() {
                try {
                    log.info("pushDoEventWriteHttp异步开始了");
                    SdsServiceImpl atService = (SdsServiceImpl) SpringContextUtil2.getBean("sdsServiceImpl");
                    atService.pushDoEventWriteHttp(ip, oid, eventType, content, eventNo, bussInfoId, flag, pri, port);
                    log.info("pushDoEventWriteHttp异步结束");
                } catch (Exception e) {
                    log.info("AsyncFactory.pushDoEventWriteHttp报错了" + e.getMessage());
                }
            }
        };
    }

    public static TimerTask searchAreaServiceAndSendHttp(final String ip, final String fromOid, final String eventType, final String content, final String eventNo, final String toOid, final String bussInfoId, String flag, String pri, String port) {
        return new TimerTask() {
            @Override
            public void run() {
                try {
                    log.info("searchAreaServiceAndSendHttp异步开始了");
                    SdsServiceImpl atService = (SdsServiceImpl) SpringContextUtil2.getBean("sdsServiceImpl");
                    atService.searchAreaServiceAndSendHttp(ip, fromOid, eventType, content, eventNo, toOid, bussInfoId,flag,pri,port);
                    log.info("searchAreaServiceAndSendHttp异步结束");
                } catch (Exception e) {
                    log.info("AsyncFactory.searchAreaServiceAndSendHttp报错了" + e.getMessage());
                }
            }
        };
    }

    public static TimerTask pushEventHttp(final String ip, final String eventNo, final String oid, final String eventType, final String content, final String targetOid, final String bussInfoId, String flag, String pri, String port) {
        return new TimerTask() {
            @Override
            public void run() {
                try {
                    log.info("pushEventHttp异步开始了");
                    SdsServiceImpl atService = (SdsServiceImpl) SpringContextUtil2.getBean("sdsServiceImpl");
                    atService.pushEventHttp(ip, eventNo, oid, eventType, content, targetOid, bussInfoId,flag,pri,port);
                    log.info("pushEventHttp异步结束");
                } catch (Exception e) {
                    log.info("AsyncFactory.pushEventHttp报错了" + e.getMessage());
                }
            }
        };
    }

    /*
     * 在区域服务器，处理与行业相关的任务推送
     */
//    public static TimerTask doTradeTask(final String eventNo, final String ip, final String flag, final String oid, final String pri, final String buss, final String port, final String cmd, final String param) {
//        return new TimerTask() {
//            @Override
//            public void run() {
//                try {
//                    log.info("doTradeTask异步开始了");
//                    AtServiceImpl atService = (AtServiceImpl) SpringContextUtil2.getBean("atServiceImpl");
//                    atService.doTradeTask(eventNo, ip, flag, oid, pri, buss, port, cmd, param);
//                    log.info("doTradeTask异步结束");
//                } catch (Exception e) {
//                    log.info("AsyncFactory.doTradeTask报错了" + e.getMessage());
//                }
//            }
//        };
//    }
}
