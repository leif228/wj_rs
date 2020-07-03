package com.wujie.common.enums;

public enum SettleTypeCode {
	income("收入"),
	output("支出"),
	recharge("充值"),
	withdraw("提现");

	private String description;

	private SettleTypeCode(String description) {
		this.description = description;
	}
	public static SettleTypeCode valueOf(int ordinal) {
		if (ordinal < 0 || ordinal >= values().length) {
			throw new IndexOutOfBoundsException("Invalid ordinal");
		}
		return values()[ordinal];
	}
	public String getDescription() {
		return description;
	}

}
