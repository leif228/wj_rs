package com.wujie.common.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum PayStyleCode {
	alipay("支付宝"),

	pinganpay("平安银行见证宝"),
	
	wxpay("微信"),
	
	cardpay("卡支付");

	private String description;

	private PayStyleCode(String description) {
		this.description = description;
	}

	@JsonValue
	public String getDescription() {
		return description;
	}
}
