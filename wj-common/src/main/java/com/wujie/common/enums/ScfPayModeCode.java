package com.wujie.common.enums;

public enum ScfPayModeCode {
	unknown("未知"), walletpay("钱包支付"), fastpay("快捷支付");

	private String description;

	private ScfPayModeCode(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
}
