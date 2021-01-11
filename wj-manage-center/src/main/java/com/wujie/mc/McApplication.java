package com.wujie.mc;

import com.wujie.mc.app.framework.netty.WebsocketDanmuServer;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ApplicationContext;

/**
 *
 *************************************************
 * 	功能描述:  后台管理入口
 * @author leif
 * @version 1.0
 * @date    2021年1月11日 创建文件
 * @see
 *************************************************
 */
//@EnableScheduling
//@SpringBootApplication

@SpringBootApplication(
        scanBasePackages = {"com.wujie.mc","com.wujie.common.utils","com.wujie.fclient.service"},
        exclude = { DataSourceAutoConfiguration.class })
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.wujie.fclient.service")
@MapperScan("com.wujie.mc.app.business.repository")
public class McApplication   {

    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(McApplication.class, args);
        applicationContext.getBean(WebsocketDanmuServer.class).run();
    }

}
