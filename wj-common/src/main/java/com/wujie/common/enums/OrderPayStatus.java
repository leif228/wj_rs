package com.wujie.common.enums;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum OrderPayStatus {
	
	WAIT_PAYMENT("未支付"),
	
	PAY_FREEZE("已冻结"),

	PAY_FINISHED("已支付"),
	
	BACK_FINISHED("已退回"),

	UPLOAD_IMG("上传支付截图"),

	WAIT_INPUT("待录入");

	private String description;

	public static List<Map<String, Object>> getMaps() {
		List<Map<String, Object>> payStyles = new ArrayList<Map<String, Object>>();
		for (OrderPayStatus payStyle : OrderPayStatus.values()) {
			payStyles.add(payStyle.getMap());
		}
		return payStyles;
	}
	public static OrderPayStatus valueOf(int ordinal) {
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

	private OrderPayStatus(String description) {
		this.description = description;
	}

	@JsonValue
	public String getDescription() {
		return description;
	}
}
