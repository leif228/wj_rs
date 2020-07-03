package com.wujie.common.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum ScfRoleCode {
	cargo("货方"), ship("船方"), company("经纪公司"), adCompany("垫资公司"), finance("财务");

	private String description;

	public static List<Map<String, Object>> getMaps() {
		List<Map<String, Object>> codes = new ArrayList<Map<String, Object>>();
		for (ScfRoleCode code : ScfRoleCode.values()) {
			codes.add(code.getMap());
		}
		return codes;
	}
	
	public static ScfRoleCode getByOrdinal(int ord) {
		for (ScfRoleCode code : ScfRoleCode.values()) {
			if(code.ordinal() == ord)
				return code;
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
	
	public static ScfRoleCode getByDescription(String description) {
		if (StringUtils.isNotEmpty(description)) {
			for (ScfRoleCode code : ScfRoleCode.values()) {
				if (code.getDescription().equals(description)) {
					return code;
				}
			}
		}
		return null;
	}

	private ScfRoleCode(String description) {
		this.description = description;
	}

	@JsonValue
	public String getDescription() {
		return description;
	}
}
