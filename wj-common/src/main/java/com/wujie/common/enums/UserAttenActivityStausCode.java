package com.wujie.common.enums;

public enum UserAttenActivityStausCode {
	canjoin("可参与"),
	notjoin("不可参与该活动"),
	finshed("活动已结束"),
	notstart("活动未开始"),
	joined("已参与过该活动"),
	joinedtomuch("已超过参与次数"),
	notexist("活动不存在");

	private String description;

	private UserAttenActivityStausCode(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
}
