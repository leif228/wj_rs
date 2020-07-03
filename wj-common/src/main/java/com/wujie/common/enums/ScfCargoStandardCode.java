package com.wujie.common.enums;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 件杂货包装规格
 * @author lingwei
 *
 */
public enum ScfCargoStandardCode {
	
	box("箱"),
	bundle("扎"),
	barrel("桶"),
	piece("件"),
	bag("袋");

	private String description;

	public static List<Map<String, Object>> getMaps() {
		List<Map<String, Object>> codes = new ArrayList<Map<String, Object>>();
		for (ScfCargoStandardCode code : ScfCargoStandardCode.values()) {
			codes.add(code.getMap());
		}
		return codes;
	}
	
	public static ScfCargoStandardCode valueOf(int ordinal) {
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

	private ScfCargoStandardCode(String description) {
		this.description = description;
	}

	@JsonValue
	public String getDescription() {
		return description;
	}
}
