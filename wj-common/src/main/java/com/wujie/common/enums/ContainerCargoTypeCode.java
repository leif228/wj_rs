package com.wujie.common.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 集装箱可装货类
 * @author Administrator
 *
 */
public enum ContainerCargoTypeCode {
	tradeContainer("内贸集装箱"),

	hkContainer("港澳集装箱"),

	dangerContainer("危险品集装箱"),

	foreignContainer("外贸集装箱");

	private String description;

	public static List<Map<String, Object>> getMaps() {
		List<Map<String, Object>> codes = new ArrayList<Map<String, Object>>();
		for (ContainerCargoTypeCode code : ContainerCargoTypeCode.values()) {
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

	private ContainerCargoTypeCode(String description) {
		this.description = description;
	}
}
