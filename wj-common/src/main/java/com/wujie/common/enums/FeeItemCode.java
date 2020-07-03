package com.wujie.common.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum FeeItemCode {
	inaccount("充值", "I"),

	outaccount("提现", "O"),

	face("当面付", "F"),

	prefee("运单支付", "P"),

	bonus("红包", "B"),

	bona("成约金", "N"),
	
	used("平台使用费", "U"),

	guarantee("担保金", "G"),

	duesfee("月租", "D"),

	addoil("加油", "A"),

	insurance("保险", "S"),

	charges("佣金", "R"),
	
	cashback("活动返现", "C"),
	
	cashsweep("资金归集", "Z"),
	
	refundfee("退回手续费", "E"),
	
	directPay("直接支付", "H"),

	comPay("分佣支付", "Y"),

	taskPay("任务支付", "T"),

	pubPay("公司支付", "K");

	private String description;
	private String prefix;

	private FeeItemCode(String description, String prefix) {
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
