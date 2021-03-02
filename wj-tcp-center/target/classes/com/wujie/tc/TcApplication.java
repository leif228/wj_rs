package com.wujie.tc;


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
 * 	功能描述:  应用程序主入口                  
 * @author Mr.Chen
 * @version 1.0                
 * @date    2018年10月19日 创建文件                                 
 * @see                        
 *************************************************
 */
//@EnableScheduling
//@SpringBootApplication

@SpringBootApplication(
		scanBasePackages = {"com.wujie.tc","com.wujie.common.utils","com.wujie.fclient.service"},
		exclude = { DataSourceAutoConfiguration.class })
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.wujie.fclient.service")
@MapperScan("com.wujie.tc.app.business.repository")
public class TcApplication   {

	public static void main(String[] args) {
		ApplicationContext applicationContext = SpringApplication.run(TcApplication.class, args);
//		applicationContext.getBean(WebsocketDanmuServer.class).run();
	}

}
