package com.wujie.common.enums;

//账户类型
public enum ScfAccountTypeCode {
	general("普通子账户"), merchant("商户子账户");

	private String description;

	private ScfAccountTypeCode(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public static ScfAccountTypeCode valueOf(int ordinal) {
		if (ordinal < 0 || ordinal >= values().length) {
			throw new IndexOutOfBoundsException("Invalid ordinal");
		}
		return values()[ordinal];
	}
}
