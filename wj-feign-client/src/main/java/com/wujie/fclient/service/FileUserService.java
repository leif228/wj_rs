
package com.wujie.fclient.service;

import com.wujie.common.base.ApiResult;
import com.wujie.common.enums.ErrorEnum;
import com.wujie.fclient.service.Encoder.MultipartSupportConfig;
import feign.codec.Encoder;
import feign.form.spring.SpringFormEncoder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * 用户服务接口
 *
 * @author leif
 * @version 1.0.0
 * @since 2020/7/2
 */
@FeignClient(
        value = "wj-fc-center",
        fallback = FileUserService.FileUserServiceFallBack.class, configuration = MultipartSupportConfig.class
)
public interface FileUserService {


    @PostMapping(value = "/upload",consumes = MediaType.MULTIPART_FORM_DATA_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResult uploadFile(@RequestPart(value = "file") MultipartFile file);

    @PostMapping("/test")
    public ApiResult test(@RequestParam(value = "testp") String testp);
    @Component
    class FileUserServiceFallBack implements FileUserService {



        @Override
        public ApiResult uploadFile(MultipartFile file) {
            return ApiResult.error(ErrorEnum.ERR_FSERVICE_NOT);
        }

        @Override
        public ApiResult test(String testp) {
            return ApiResult.error(ErrorEnum.ERR_FSERVICE_NOT);
        }


    }


}