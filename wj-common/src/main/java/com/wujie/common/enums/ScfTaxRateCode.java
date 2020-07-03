package com.wujie.common.enums;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum ScfTaxRateCode {
	no(0, "船方不包发票"),
	small(3, "船方包发票，小规模纳税人3%"),
	general(10, "船方包发票，一般纳税人9%");

	private Integer value;
	private String description;

	public static List<Map<String, Object>> getMaps() {
		List<Map<String, Object>> maps = new ArrayList<Map<String, Object>>();
		for (ScfTaxRateCode e : ScfTaxRateCode.values()) {
			maps.add(e.getMap());
		}
		return maps;
	}
	public static ScfTaxRateCode valueOf(int ordinal) {
        if (ordinal < 0 || ordinal >= values().length) {
            throw new IndexOutOfBoundsException("Invalid ordinal");
        }
        return values()[ordinal];
    }
	public Map<String, Object> getMap() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("no", this.ordinal());
		map.put("name", this.name());
		map.put("value", this.value);
		map.put("description", this.description);
		return map;
	}

	private ScfTaxRateCode(Integer value, String description) {
		this.value = value;
		this.description = description;
	}

	public Integer getValue() {
		return value;
	}
	
	@JsonValue
	public String getDescription() {
		return description;
	}
}
