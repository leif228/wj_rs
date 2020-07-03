package com.wujie.common.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum FinCompanyCertTypeCode {
	creditcode("统一社会信用代码");
	//organizations("组织机构代码证");

	private String description;

	private FinCompanyCertTypeCode(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}


	public static List<Map<String, Object>> getMaps() {
		List<Map<String, Object>> codes = new ArrayList<Map<String, Object>>();
		for (FinCompanyCertTypeCode code : FinCompanyCertTypeCode.values()) {
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
	
	public static FinCompanyCertTypeCode getByDescription(String description){
		FinCompanyCertTypeCode[] arr = FinCompanyCertTypeCode.values();
		for(FinCompanyCertTypeCode code:arr){
			if(code.getDescription().equals(description)){
				return code;
			}
		}
		return null;
	}
}
