package com.wujie.common.enums;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum ScfTradeTypeCode {
	inTrade("内贸货"), outTrade("外贸货"), hkmacha("港澳货"), forPass("转关货");

	private String description;

	public static List<Map<String, Object>> getMaps() {
		List<Map<String, Object>> maps = new ArrayList<Map<String, Object>>();
		for (ScfTradeTypeCode e : ScfTradeTypeCode.values()) {
			maps.add(e.getMap());
		}
		return maps;
	}

	public static ScfTradeTypeCode valueOf(int ordinal) {
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

	private ScfTradeTypeCode(String description) {
		this.description = description;
	}

	@JsonValue
	public String getDescription() {
		return description;
	}
}
