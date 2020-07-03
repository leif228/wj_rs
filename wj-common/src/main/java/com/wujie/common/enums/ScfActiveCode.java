package com.wujie.common.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ScfActiveCode {
	inactivity("未激活"),
	activity("激活");

	private String description;

	private ScfActiveCode(String description) {
		this.description = description;
	}

	@JsonValue
	public String getDescription() {
		return description;
	}
}
