package com.wujie.common.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum OrderTaskStatus {
	
	status0("初始状态"),
	
	status1("待确认"),
	
	status2("已确认"),
	
	status3("在押"),

	status4("未支付"),

	status5("已支付"),

	status6("已退还"),

	status7("已中止"),

	status8("待上传"),

	status9("已上传"),

	status10("待评价"),

	status11("已评价");

	private String description;

	public static List<Map<String, Object>> getMaps() {
		List<Map<String, Object>> payStyles = new ArrayList<Map<String, Object>>();
		for (OrderTaskStatus payStyle : OrderTaskStatus.values()) {
			payStyles.add(payStyle.getMap());
		}
		return payStyles;
	}
	public static OrderTaskStatus valueOf(int ordinal) {
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

	private OrderTaskStatus(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
}
