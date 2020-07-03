package com.wujie.common.enums;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum CargoType {
	online("线上"), 
	offline("线下");

	private String description;

	public static List<Map<String, Object>> getMaps() {
		List<Map<String, Object>> codes = new ArrayList<Map<String, Object>>();
		for (CargoType code : CargoType.values()) {
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

	private CargoType(String description) {
		this.description = description;
	}

	@JsonValue
	public String getDescription() {
		return description;
	}
}
