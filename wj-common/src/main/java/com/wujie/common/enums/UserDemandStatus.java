package com.wujie.common.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 货方需求状态
 * @author wujie
 *
 */
public enum UserDemandStatus {
	
	status0("顾问已联系"), 
	status1("已代发详盘"),	
	status2("已代发简盘"),
	status3("暂无适合船舶"),
	status4("运价过低"),
	status5("货盘已取消");

	private String description;

	public static List<Map<String, Object>> getMaps() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for (UserDemandStatus item : UserDemandStatus.values()) {
			list.add(item.getMap());
		}
		return list;
	}
	
	public static UserDemandStatus valueOf(int ordinal) {
		if (ordinal < 0 || ordinal >= values().length) {
			throw new IndexOutOfBoundsException("Invalid ordinal");
		}
		return values()[ordinal];
	}
	
	public static UserDemandStatus find(String description) {
		if (StringUtils.isNotEmpty(description)) {
			for (UserDemandStatus item : UserDemandStatus.values()) {
				if (item.getDescription().equals(description)) {
					return item;
				}
			}
		}
		return null;
	}

	public Map<String, Object> getMap() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("no", this.ordinal());
		map.put("name", this.name());
		map.put("description", this.description);

		return map;
	}

	private UserDemandStatus(String description) {
		this.description = description;
	}

	@JsonValue
	public String getDescription() {
		return description;
	}
}
