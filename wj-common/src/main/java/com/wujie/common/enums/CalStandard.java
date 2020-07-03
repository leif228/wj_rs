package com.wujie.common.enums;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum CalStandard {
	unload("以卸货为准"), load("以装货为准");

	private String description;

	public static List<Map<String, Object>> getMaps() {
		List<Map<String, Object>> payStyles = new ArrayList<Map<String, Object>>();
		for (CalStandard payStyle : CalStandard.values()) {
			payStyles.add(payStyle.getMap());
		}
		return payStyles;
	}

	public Map<String, Object> getMap() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("no", this.ordinal());
		map.put("name", this.name());
		map.put("description", this.description);

		return map;
	}

	private CalStandard(String description) {
		this.description = description;
	}

	@JsonValue
	public String getDescription() {
		return description;
	}
}
