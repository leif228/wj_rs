package com.wujie.common.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum AuditStatusCode {
	unaudit("未认证"), audit("已认证"), cancelaudit("已取消认证");

	private String description;

	private AuditStatusCode(String description) {
		this.description = description;
	}

	@JsonValue
	public String getDescription() {
		return description;
	}
}
