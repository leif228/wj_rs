package com.wujie.common.dto.pay;


import com.wujie.common.dto.BaseData;

import java.util.List;

/**
 * 对账文件信息Data封装类
 * @author Administrator
 *
 */
public class ZjjzFileData extends BaseData {

	private static final long serialVersionUID = -1L;

	private String fileName = "";	// 文件名称
	private String key = "";	// 随机密码

	public ZjjzFileData() {
		super();
	}

	public ZjjzFileData(List<String> lst) {
		super();
		if (lst.size() > 0)
			this.fileName = lst.get(0);
		if (lst.size() > 1)
			this.key = lst.get(1);
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String toString() {
		return "文件名称:" + fileName + ",随机密码:" + key;
	}
}
