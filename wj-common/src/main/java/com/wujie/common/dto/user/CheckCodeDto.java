package com.wujie.common.dto.user;

import java.io.Serializable;

public class CheckCodeDto implements Serializable {

	private static final long serialVersionUID = 1L;

	String mobile;
	String checkType;

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getCheckType() {
		return checkType;
	}

	public void setCheckType(String checkType) {
		this.checkType = checkType;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
