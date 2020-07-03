package com.wujie.common.enums;

public enum WalletSettleCode {
	fill("充值"),

	fetch("提现"),

	pay("担保支付"),

	refund("退款"),

	backfetch("提现退单"),

	masterfee("运单收款"),

	brokerfee("代理人佣金"),

	platfee("平台服务费"),

	handfee("取现手续费"),

	receivefee("直接收款"),

	bonus("红包"),

	back("返现"),
	
	collect("资金归集");

	private String description;

	private WalletSettleCode(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
}
