package com.wujie.common.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum FinMessageOptCode {

	pay("支付", "P"),
	
	read("查看", "R");

	private String description;
	private String prefix;

	private FinMessageOptCode(String description, String prefix) {
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
