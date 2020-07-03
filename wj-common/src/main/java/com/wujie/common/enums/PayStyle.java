package com.wujie.common.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum PayStyle {
	app("请船易钱包"), 
	bank("银行转账"), 
	weixin("微信"),
	money("现金"), 
	alipay("支付宝");

	private String description;

	public static List<Map<String, Object>> getMaps() {
		List<Map<String, Object>> payStyles = new ArrayList<Map<String, Object>>();
		for (PayStyle payStyle : PayStyle.values()) {
			payStyles.add(payStyle.getMap());
		}
		return payStyles;
	}
	public static PayStyle valueOf(int ordinal) {
		if (ordinal < 0 || ordinal >= values().length) {
			throw new IndexOutOfBoundsException("Invalid ordinal");
		}
		return values()[ordinal];
	}

	public Map<String, Object> getMap() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("no", this.ordinal());
		map.put("name", this.name());
		map.put("description", this.description);

		return map;
	}
	
	public static PayStyle getByDescription(String description) {
		if (StringUtils.isNotEmpty(description)) {
			for (PayStyle code : PayStyle.values()) {
				if (code.getDescription().equals(description)) {
					return code;
				}
			}
		}
		return null;
	}

	private PayStyle(String description) {
		this.description = description;
	}

	@JsonValue
	public String getDescription() {
		return description;
	}
}
