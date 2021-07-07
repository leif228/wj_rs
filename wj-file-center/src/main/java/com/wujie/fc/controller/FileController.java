/**
 * Copyright (C), 2016-2018, 广州航运电子商务有限公司
 * FileName: FileController
 * Author:   Guoqiang
 * Date:     2018/12/4 下午2:18
 * Description: 文件上传服务
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.wujie.fc.controller;

import com.wujie.common.base.ApiResult;
import com.wujie.common.utils.SpringContextUtil;
import com.wujie.common.utils.StringUtils;
import com.wujie.fc.component.FastDFSClientWrapper;
import com.wujie.fc.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件上传服务
 *
 * @author Guoqiang
 * @since 2018/12/4
 * @version 1.0.0
 */
@RestController
@RequestMapping("/fs")
public class FileController{
    @Autowired
    FileService fileService;

    @RequestMapping(method = RequestMethod.POST, value = "/upload",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResult uploadFile(@RequestPart(value = "file") MultipartFile file) {
        String imgUrl = FastDFSClientWrapper.uploadFile(file);
        String profile = SpringContextUtil.getActiveProfile();
//        if(profile.equals("prod")) {//生产环境文件服务器地址和开发环境不同
//            imgUrl = FastDFSClientWrapper.getHttpAccessUrl(imgUrl);
//        }else{
//            imgUrl = FastDFSClientWrapper.getResAccessUrl(imgUrl);
//        }
        imgUrl = FastDFSClientWrapper.getHttpAccessUrl(profile, imgUrl);
        if(StringUtils.isNotEmpty(imgUrl)){
            return ApiResult.success(imgUrl);
        }else{
            return ApiResult.error("文件上传失败");
        }

    }
}
