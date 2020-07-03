package com.wujie.common.enums;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum ScfEscortCode {
	monitor("航易货舱监控系统"), none("无特别要求"), sealup("封舱运输"), escortting("押运");

	private String description;

	public static List<Map<String, Object>> getMaps() {
		List<Map<String, Object>> maps = new ArrayList<Map<String, Object>>();
		for (ScfEscortCode e : ScfEscortCode.values()) {
			maps.add(e.getMap());
		}
		return maps;
	}
	public static ScfEscortCode valueOf(int ordinal) {
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

	private ScfEscortCode(String description) {
		this.description = description;
	}

	@JsonValue
	public String getDescription() {
		return description;
	}
}
