/**
 * Copyright (C), 2016-2018, 广州航运电子商务有限公司
 * FileName: FileServiceImpl
 * Author:   Guoqiang
 * Date:     2018/12/4 下午3:18
 * Description: 文件上传服务实现
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.wujie.fc.service.impl;

import com.wujie.common.base.ApiResult;
import com.wujie.fc.component.FastDFSClientWrapper;
import com.wujie.fc.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * 文件上传服务实现
 *
 * @author Guoqiang
 * @since 2018/12/4
 * @version 1.0.0
 */
@Service
public class FileServiceImpl implements FileService {
    @Autowired
    private FastDFSClientWrapper dfsClient;

    @Override
    public String saveUploadFile(MultipartFile file) throws IOException {
        /*
        String realPath = ConfigConstants.SHARE_DIR;
        String relativePath = ShareDirUtil.getUserDir(userId);
        String prefix = "UserLogo";
        String url = MultipartUtil.uploadFile(logoMpf, realPath, relativePath, prefix);*/
        // 省略业务逻辑代码。。。
        String imgUrl = dfsClient.uploadFile(file);
        // 。。。。
        return imgUrl;
    }

    @Override
    public ApiResult saveUploadFiles(MultipartFile[] files, String uid) {

        return ApiResult.success(files.length+"-"+uid);
    }
}
