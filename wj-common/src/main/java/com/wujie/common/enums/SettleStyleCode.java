package com.wujie.common.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum SettleStyleCode {
	fill("充值", "将绑定银行卡的钱转入钱包"),
	fetch("提现", "将钱包的钱转入绑定的银行卡"),
	turn("转帐", "将钱包的钱转入别人的银行卡"),
	pay("交易", "一个会员支付交易金额给另一个会员,可设资金托管期,分资金托管、成交、退款三种状态"),
	back("返现", "将资金汇总账号的钱转入钱包"),
	collect("资金归集", "将资金从支付账户转账到收款账户"),
	refund("退回手续费", "平台将手续费退还给用户");

	private String description;
	private String remark;

	private SettleStyleCode(String description, String remark) {
		this.description = description;
		this.remark = remark;
	}

	@JsonValue
	public String getDescription() {
		return description;
	}

	public String getRemark() {
		return remark;
	}
}
