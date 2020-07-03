package com.wujie.common.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 船舶特殊航线
 * @author lingwei
 *
 */
public enum SpecialRouteCode {
	
	gaolan("高栏线"),
	qingyuan("清远线"),
	longxi("龙溪线"),
	laibinhonghe("来宾红河线"),
	wuxuan("武宣线"),
	liuzhou("柳州线"),
	nanning("南宁线"),
	gangao("港澳线");

	private String description;

	public static List<Map<String, Object>> getMaps() {
		List<Map<String, Object>> codes = new ArrayList<Map<String, Object>>();
		for (SpecialRouteCode code : SpecialRouteCode.values()) {
			codes.add(code.getMap());
		}
		return codes;
	}
	
	public static SpecialRouteCode valueOf(int ordinal) {
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

	private SpecialRouteCode(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
}
