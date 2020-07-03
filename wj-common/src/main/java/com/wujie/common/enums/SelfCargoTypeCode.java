package com.wujie.common.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 自卸船可装货类
 * @author Administrator
 *
 */
public enum SelfCargoTypeCode {
	coal("煤炭"),
	metalmineral("矿石"),
	metal("沙子"),
	other("其他");

	private String description;

	public static List<Map<String, Object>> getMaps() {
		List<Map<String, Object>> codes = new ArrayList<Map<String, Object>>();
		for (SelfCargoTypeCode code : SelfCargoTypeCode.values()) {
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

	public String getDescription() {
		return description;
	}

	private SelfCargoTypeCode(String description) {
		this.description = description;
	}
}
