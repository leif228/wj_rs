package com.wujie.common.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 运单支付任务的项目枚举
 * @author wujie
 *
 */
public enum ItemName {
	
	contract("运单"), 
	deposit("成约"),	
	guarantee("担保"),
	freight("运费"),
	comContract("佣金运单"),
	commision("佣金"),
	repayment("还款"),
	upload("上传"),
	evaluate("评价"),
	tips("提示"),
	report("申报运费");

	private String description;

	public static List<Map<String, Object>> getMaps() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for (ItemName item : ItemName.values()) {
			list.add(item.getMap());
		}
		return list;
	}
	
	public static ItemName valueOf(int ordinal) {
		if (ordinal < 0 || ordinal >= values().length) {
			throw new IndexOutOfBoundsException("Invalid ordinal");
		}
		return values()[ordinal];
	}
	
	public static ItemName find(String description) {
		if (StringUtils.isNotEmpty(description)) {
			for (ItemName item : ItemName.values()) {
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

	private ItemName(String description) {
		this.description = description;
	}

	@JsonValue
	public String getDescription() {
		return description;
	}
}
