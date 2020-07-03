package com.wujie.common.enums;

public enum PayResultCode {
	success("支付成功"),

	fail("支付失败"),
	
	wait("支付中");

	private String description;

	private PayResultCode(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
}
