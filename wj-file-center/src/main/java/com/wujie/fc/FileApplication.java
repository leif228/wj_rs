/**
 * Copyright (C), 2016-2018, 广州航运电子商务有限公司
 * FileName: FileApplication
 * Author:   Guoqiang
 * Date:     2018/12/3 下午4:27
 * Description: 文件上传服务
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.wujie.fc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 文件上传服务
 *
 * @author Guoqiang
 * @since 2018/12/3
 * @version 1.0.0
 * https://blog.csdn.net/qq_29534483/article/details/81330269
 */
@SpringBootApplication(
        scanBasePackages = {"com.wujie.fc","com.wujie.common","com.wujie.fclient.service"},
        exclude = { DataSourceAutoConfiguration.class })
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.wujie.fclient.service")
public class FileApplication {
    public static void main(String[] args){
        SpringApplication.run(FileApplication.class, args);
    }
}
