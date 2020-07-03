package com.wujie.common.enums;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 船方和船舶的关系
 * @author lingwei
 *
 */
public enum ScfUserShipRelationCode {
	
	unconfirmed("未确认"),
	self("所有人本人"),
	contractor("经营人或承包人"), 
	agent("代理或委托"),  
	lease("租赁"),
	captain("此船船长"),
	deleted("不实信息，删除关联");

	private String description;

	public static List<Map<String, Object>> getMaps() {
		List<Map<String, Object>> codes = new ArrayList<Map<String, Object>>();
		for (ScfUserShipRelationCode code : ScfUserShipRelationCode.values()) {
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

	private ScfUserShipRelationCode(String description) {
		this.description = description;
	}

	@JsonValue
	public String getDescription() {
		return description;
	}
}
