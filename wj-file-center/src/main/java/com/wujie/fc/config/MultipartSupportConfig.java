/**
 * Copyright (C), 2016-2018, 广州航运电子商务有限公司
 * FileName: MultipartSupportConfig
 * Author:   Guoqiang
 * Date:     2018/12/6 下午5:46
 * Description: feign请求解析类
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.wujie.fc.config;

import com.wujie.fclient.service.Encoder.SpringMultipartEncoder;
import feign.codec.Encoder;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * feign请求解析类
 *
 * @author Guoqiang
 * @since 2018/12/6
 * @version 1.0.0
 */
@Configuration
public class MultipartSupportConfig {
    @Autowired
    private ObjectFactory<HttpMessageConverters> messageConverters;
    /*
    @Bean
    public Encoder feignFormEncoder() {
        return new SpringFormEncoder(new SpringEncoder(messageConverters));
    }*/

    @Bean
    public Encoder feignEncoder() {
        return new SpringMultipartEncoder(new SpringEncoder(messageConverters));
    }


}