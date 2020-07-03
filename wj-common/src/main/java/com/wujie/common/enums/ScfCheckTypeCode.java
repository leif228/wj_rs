package com.wujie.common.enums;

public enum ScfCheckTypeCode {
	register("注册"), findPassword("找回密码"), login("登录");

	private String description;

	private ScfCheckTypeCode(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
}
