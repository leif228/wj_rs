/**
 * Copyright (C), 2016-2018, 广州航运电子商务有限公司
 * FileName: FileService
 * Author:   Guoqiang
 * Date:     2018/12/4 下午3:16
 * Description: 文件上传下载服务
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.wujie.fc.service;

import com.wujie.common.base.ApiResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * 文件上传下载服务
 *
 * @author Guoqiang
 * @since 2018/12/4
 * @version 1.0.0
 */
public interface FileService {
    String saveUploadFile(MultipartFile file)  throws IOException;

    ApiResult saveUploadFiles(MultipartFile[] files, String uid)  throws IOException;
}
