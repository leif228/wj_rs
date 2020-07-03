package com.wujie.common.enums;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 锁定动作
 * 
 * @author wujie
 */
public enum LockAction {
	lock("锁定"), release("释放");

	private String description;

	public static List<Map<String, Object>> getMaps() {
		List<Map<String, Object>> maps = new ArrayList<Map<String, Object>>();
		for (LockAction e : LockAction.values()) {
			maps.add(e.getMap());
		}
		return maps;
	}

	public static LockAction valueOf(int ordinal) {
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

	private LockAction(String description) {
		this.description = description;
	}

	@JsonValue
	public String getDescription() {
		return description;
	}
}
