package com.wujie.common.enums;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 锁定模式
 * 
 * @author wujie
 */
public enum LockMode {
	unknown("未知"),
	lease("租借"), 
	freight("运单"), 
	fleet("船队"), 
	release("系统释放"), 
	unlock("手动解锁");

	private String description;

	public static List<Map<String, Object>> getMaps() {
		List<Map<String, Object>> maps = new ArrayList<Map<String, Object>>();
		for (LockMode e : LockMode.values()) {
			maps.add(e.getMap());
		}
		return maps;
	}

	public static LockMode valueOf(int ordinal) {
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

	private LockMode(String description) {
		this.description = description;
	}
	
	@JsonValue
	public String getDescription() {
		return description;
	}
}
