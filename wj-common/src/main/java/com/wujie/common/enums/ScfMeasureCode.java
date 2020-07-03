package com.wujie.common.enums;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum ScfMeasureCode {
	teu("个"),
	ton("吨"),
	stere("方"),
	piece("件");

	private String description;

	public static List<Map<String, Object>> getMaps() {
		List<Map<String, Object>> maps = new ArrayList<Map<String, Object>>();
		for (ScfMeasureCode e : ScfMeasureCode.values()) {
			maps.add(e.getMap());
		}
		return maps;
	}
	public static ScfMeasureCode valueOf(int ordinal) {
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

	private ScfMeasureCode(String description) {
		this.description = description;
	}

	@JsonValue
	public String getDescription() {
		return description;
	}
}
