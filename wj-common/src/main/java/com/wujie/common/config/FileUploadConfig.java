/**
 * Copyright (C), 2016-2019, 广州航运电子商务有限公司
 * FileName: FileConfig
 * Author:   Guoqiang
 * Date:     2019/2/28 下午4:38
 * Description: 文件上传配置
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.wujie.common.config;

//import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;

import javax.servlet.MultipartConfigElement;

/**
 * 文件上传配置
 *
 * @author Guoqiang
 * @since 2019/2/28
 * @version 1.0.0
 *
 */
@Configuration
public class FileUploadConfig {
    @Bean
    public MultipartConfigElement multipartConfigElement(){
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize(DataSize.ofKilobytes(10240));
        factory.setMaxRequestSize(DataSize.ofKilobytes(102400));
        return factory.createMultipartConfig();
    }
}
