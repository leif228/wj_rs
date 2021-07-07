/**
 * Copyright (C), 2016-2018,
 * FileName: FastDFSClientWrapper
 * Author:   Guoqiang
 * Date:     2018/12/14 上午9:20
 * Description: FastDFS接口封装
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.wujie.fc.component;

import com.github.tobato.fastdfs.conn.FdfsWebServer;
import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.proto.storage.DownloadByteArray;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.wujie.common.constant.Constants;
import com.wujie.common.utils.StringUtils;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * FastDFS接口封装
 *
 * @author Guoqiang
 * @since 2018/12/14
 * @version 1.0.0
 */
@Component
public class FastDFSClientWrapper {
    private static Logger log = LoggerFactory.getLogger(FastDFSClientWrapper.class);

    private static FastFileStorageClient fastFileStorageClient;

    private static FdfsWebServer fdfsWebServer;

    @Autowired
    public void setFastDFSClient(FastFileStorageClient fastFileStorageClient, FdfsWebServer fdfsWebServer) {
        FastDFSClientWrapper.fastFileStorageClient = fastFileStorageClient;
        FastDFSClientWrapper.fdfsWebServer = fdfsWebServer;
    }

    /**
     * 上传文件
     * @param multipartFile 文件对象
     * @return 返回文件地址
     * @author guoqiang
     */
    public static String uploadFile(MultipartFile multipartFile) {
        try {
            if(null != multipartFile) {
                StorePath storePath = fastFileStorageClient.uploadFile(multipartFile.getInputStream(), multipartFile.getSize(), FilenameUtils.getExtension(multipartFile.getOriginalFilename()), null);
                return storePath.getFullPath();
            }else {
                return "";
            }
        } catch (IOException e) {
            log.error(e.getMessage());
            return "";
        }
    }

    /**
     * @param multipartFile 图片对象
     * @return 返回图片地址
     * @author guoqiang
     * 上传缩略图
     */
    public static String uploadImageAndCrtThumbImage(MultipartFile multipartFile) {
        try {
            if(null != multipartFile) {
                StorePath storePath = fastFileStorageClient.uploadImageAndCrtThumbImage(multipartFile.getInputStream(), multipartFile.getSize(), FilenameUtils.getExtension(multipartFile.getOriginalFilename()), null);
                return storePath.getFullPath();
            }else{
                return "";
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return "";
        }
    }

    /**
     * 上传文件
     * @param file 文件对象
     * @return 返回文件地址
     * @author guoqiang
     */
    public static String uploadFile(File file) {
        try {
            FileInputStream inputStream = new FileInputStream(file);
            StorePath storePath = fastFileStorageClient.uploadFile(inputStream, file.length(), FilenameUtils.getExtension(file.getName()), null);
            return storePath.getFullPath();
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    /**
     * @param file 图片对象
     * @return 返回图片地址
     * @author guoqiang
     * 上传缩略图
     */
    public static String uploadImageAndCrtThumbImage(File file) {
        try {
            FileInputStream inputStream = new FileInputStream(file);
            StorePath storePath = fastFileStorageClient.uploadImageAndCrtThumbImage(inputStream, file.length(), FilenameUtils.getExtension(file.getName()), null);
            return storePath.getFullPath();
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    /**
     * @param bytes         byte数组
     * @param fileExtension 文件扩展名
     * @return 返回文件地址
     * @author guoqiang
     * 将byte数组生成一个文件上传
     */
    public static String uploadFile(byte[] bytes, String fileExtension) {
        ByteArrayInputStream stream = new ByteArrayInputStream(bytes);
        StorePath storePath = fastFileStorageClient.uploadFile(stream, bytes.length, fileExtension, null);
        return storePath.getFullPath();
    }

    /**
     * @param fileUrl 文件访问地址
     * @param file    文件保存路径
     * @author guoqiang
     * 下载文件
     */
    public static boolean downloadFile(String fileUrl, File file) {
        try {
            StorePath storePath = StorePath.praseFromUrl(fileUrl);
            byte[] bytes = fastFileStorageClient.downloadFile(storePath.getGroup(), storePath.getPath(), new DownloadByteArray());
            FileOutputStream stream = new FileOutputStream(file);
            stream.write(bytes);
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * @param fileUrl 文件访问地址
     * @author guoqiang
     * @description 删除文件
     */
    public static boolean deleteFile(String fileUrl) {
        if (StringUtils.isEmpty(fileUrl)) {
            return false;
        }
        try {
            StorePath storePath = StorePath.praseFromUrl(fileUrl);
            fastFileStorageClient.deleteFile(storePath.getGroup(), storePath.getPath());
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
        return true;
    }

    // 封装文件完整URL地址
    public static String getResAccessUrl(String path) {
        String url = fdfsWebServer.getWebServerUrl() + path;
        log.info("上传文件地址为：\n" + url);
        return url;
    }
    public static String getHttpAccessUrl(String path) {
        String url = Constants.FS_HOST +"/"+ path;
        log.info("上传文件地址为：\n" + url);
        return url;
    }
    public static String getHttpAccessUrl(String profile, String path) {
        String url = "";
        if(profile.equals("prod")){
            url = Constants.FS_HOST + ":" + Constants.FS_PORT + "/" + path;
        }else if(profile.equals("dev")) {
            url = fdfsWebServer.getWebServerUrl() + path;
        }else {
            url = Constants.FS_DEV_HOST+ ":" + Constants.FS_DEV_PORT + "/" + path;
        }
        log.info("上传文件地址为：\n" + url);
        return url;
    }

    /**
     * 文件下载方式一
     * @param response
     */
    public static void downloadFileWithResponse(String url, HttpServletResponse response){
        byte[] b = fastFileStorageClient.downloadFile("group1", "M00/83/F7/wKiNgVplw4GAKqNnAANbI4wCqFY733.jpg", new DownloadByteArray());
        ByteArrayInputStream bais = new ByteArrayInputStream(b);
        try {
            String fileName1 = "aaa.jpg";
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/x-download");
            response.setHeader("Content-Disposition", "attachment;filename="+ fileName1);
            ServletOutputStream out = response.getOutputStream();
            byte[] content = new byte[1024];
            int length = -1;
            while ((length = bais.read(content)) != -1) {
                out.write(content, 0, length);
                out.flush();
            }
            out.close();
            bais.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 文件下载方式二：Controller直接返回这个ResponseEntity<byte[]>
     * @return
     */
    public static ResponseEntity<byte[]> downloadFileWithResponseEntity() {
        byte[] content = fastFileStorageClient.downloadFile("group1", "M00/83/F7/wKiNgVplw4GAKqNnAANbI4wCqFY733.jpg", new DownloadByteArray());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentDispositionFormData("attachment",  "aaa.jpg");
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        return new ResponseEntity<byte[]>(content, headers, HttpStatus.CREATED);
    }

}