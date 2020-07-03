package com.wujie.common.enums;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum BrokerageStyle {
	fixed("固定金额"), 
	priceAdd("运价 +"), 
	freightPercent("运费 %");

	private String description;

	public static List<Map<String, Object>> getMaps() {
		List<Map<String, Object>> payStyles = new ArrayList<Map<String, Object>>();
		for (BrokerageStyle payStyle : BrokerageStyle.values()) {
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

	private BrokerageStyle(String description) {
		this.description = description;
	}

	@JsonValue
	public String getDescription() {
		return description;
	}
}
