package com.wujie.common.enums;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum PayStatusCode {
	WAIT_PAYMENT("未支付", "未提现", "未充值"),

	WAIT_CONFIRM("资金托管", "", ""),

	TRADE_FINISHED("确认付款", "提现完成", "充值完成"),

	TRADE_CLOSED("支付失败", "提现退单", "充值失败"),
	
	TRADE_BACK("退款成功", "提现退单", "充值失败"),

	TRADE_ING("支付中", "提现中", "充值中");
	
	private String description;
	private String remark;
	private String remark2;

	public static List<Map<String, Object>> getMaps() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for (PayStatusCode item : PayStatusCode.values()) {
			list.add(item.getMap());
		}
		return list;
	}

	public Map<String, Object> getMap() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("no", this.ordinal());
		map.put("name", this.name());
		map.put("description", this.description);

		return map;
	}

	private PayStatusCode(String description, String remark, String remark2) {
		this.description = description;
		this.remark = remark;
		this.remark2 = remark2;
	}

	@JsonValue
	public String getDescription() {
		return description;
	}

	public String getRemark() {
		return remark;
	}

	public String getRemark2() {
		return remark2;
	}
}
