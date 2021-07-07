package com.wujie.fc.util;

import com.wujie.common.utils.DateUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

public class MultipartUtil {

	public static String getFileName(String ext) {
		String fileName = DateUtils.getTime("yyyyMMddHHmmss");

		int randomNum = (int) (Math.random() * 9000 + 1000);
		fileName += randomNum;

		if (ext != null && !"".equals(ext)) {
			if (ext.startsWith(".")) {
				ext = ext.substring(1);
			}
			fileName += "." + ext;
		}

		return fileName;
	}

	/** 保存文件返回相对路径 */
	public static String uploadFile(MultipartFile mpf, String realPath, String relativePath, String prefix)
			throws IOException {
		if (mpf == null)
			return null;

		String ext = mpf.getOriginalFilename();
		InputStream is = mpf.getInputStream();

		String pathfile = MultipartUtil.uploadFile(is, realPath, relativePath, prefix, ext);

		return pathfile;
	}

	/** 保存文件返回相对路径 */
	public static String uploadFile(InputStream is, String realPath, String relativePath, String prefix, String ext)
			throws IOException {
/*
		String newFileName = getFileName(FilenameUtils.getExtension(ext));

		String pathfile = "";
		if (prefix == null || "".equals(prefix))
			pathfile = relativePath + ShareDirUtil.FILE_SEPA_SIGN + newFileName;
		else
			pathfile = relativePath + ShareDirUtil.FILE_SEPA_SIGN + prefix + "-" + newFileName;
		File newFile = new File(realPath + pathfile);
		FileUtils.copyInputStreamToFile(is, newFile );
		if(ImageUtil.isImage(newFileName)){
			//读取文件大小，超过3m转换成小文件
			if(newFile.length() > 3000000){
				ImageUtil.resize(realPath + pathfile, 400, 600, false);
			}
		}
		return pathfile;*/return  "";
	}

	/** 保存多个文件返回相对路径数组 */
	public static String[] uploadMultiFile(MultipartFile[] mpfs, String realPath, String relativePath, String prefix)
			throws IOException {
		if (mpfs == null || mpfs.length == 0)
			return null;

		String[] pathArray = new String[mpfs.length];

		for (int i = 0; i < mpfs.length; i++) {
			MultipartFile mpf = mpfs[i];

			pathArray[i] = uploadFile(mpf, realPath, relativePath, prefix);
		}
		return pathArray;
	}
}