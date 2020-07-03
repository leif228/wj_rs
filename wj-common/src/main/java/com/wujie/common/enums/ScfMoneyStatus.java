package com.wujie.common.enums;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum ScfMoneyStatus {
	noPay("未支付"),

	paid("在押"),
	
	payToCargo("支付给货方"),
	
	payToShip("支付给船方"),
	
	back("已退回");

	private String description;

	private ScfMoneyStatus(String description) {
		this.description = description;
	}

	@JsonValue
	public String getDescription() {
		return description;
	}
	
	
	public static List<Map<String, Object>> getMaps() {
		List<Map<String, Object>> codes = new ArrayList<Map<String, Object>>();
		for (ScfMoneyStatus code : ScfMoneyStatus.values()) {
			codes.add(code.getMap());
		}
		return codes;
	}

	public Map<String, Object> getMap() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("no", this.ordinal());
		map.put("name", this.name());
		map.put("description", this.description);

		return map;
	}
}
