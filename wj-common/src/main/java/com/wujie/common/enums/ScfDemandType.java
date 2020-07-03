package com.wujie.common.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum ScfDemandType {
	cargoDemand("货方需求"), shipDemand("船方需求"), contact("联系平台");

	private String description;

	public static List<Map<String, Object>> getMaps() {
		List<Map<String, Object>> codes = new ArrayList<Map<String, Object>>();
		for (ScfDemandType code : ScfDemandType.values()) {
			codes.add(code.getMap());
		}
		return codes;
	}

	public static ScfDemandType getByOrdinal(int ord) {
		for (ScfDemandType code : ScfDemandType.values()) {
			if(code.ordinal() == ord)
				return code;
		}
		return null;
	}

	public Map<String, Object> getMap() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("no", this.ordinal());
		map.put("name", this.name());
		map.put("description", this.description);
		return map;
	}

	public static ScfDemandType getByDescription(String description) {
		if (StringUtils.isNotEmpty(description)) {
			for (ScfDemandType code : ScfDemandType.values()) {
				if (code.getDescription().equals(description)) {
					return code;
				}
			}
		}
		return null;
	}

	private ScfDemandType(String description) {
		this.description = description;
	}

	@JsonValue
	public String getDescription() {
		return description;
	}
}
