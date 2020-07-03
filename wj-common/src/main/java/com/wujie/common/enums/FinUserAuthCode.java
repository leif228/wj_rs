package com.wujie.common.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum FinUserAuthCode {
	
	apply("业务员认证", "A"),
	
	unapply("取消认证", "U");

	private String description;
	private String prefix;

	private FinUserAuthCode(String description, String prefix) {
		this.description = description;
		this.prefix = prefix;
	}

	@JsonValue
	public String getDescription() {
		return description;
	}

	public String getPrefix() {
		return prefix;
	}
}
