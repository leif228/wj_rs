package com.wujie.common.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum AppOrderStatus {
	
	status0("等待确认运单"), 
	status1("已确认运单"), 
	status2("等待确认运单并冻结成约金"), 
	status3("已确认运单并支付成约金"),
	status4("已退还成约金"),
	status5("等待支付担保金"),
	status6("已冻结担保金"),
	status7("已退还担保金"),
	
	status8("等待支付第一期运费"),
	status9("已支付第一期运费"),
	status10("已退还第一期运费"),
	
	status11("等待支付第二期运费"),
	status12("已支付第二期运费"),
	status13("已退还第二期运费"),
	
	status14("等待支付第三期运费"),
	status15("已支付第三期运费"),
	status16("已退还第三期运费"),
	
	status17("等待支付第四期运费"),
	status18("已支付第四期运费"),
	status19("已退还第四期运费"),
	
	status20("等待确认佣金运单"), 
	status21("已确认佣金运单"),	
	status22("等待支付佣金"), 
	status23("已支付佣金"),

	status24("客户取消运单"),
	status25("顾问中止任务"),

	status26("等待货方还款"),
	status27("货方已还款"),

	status28("等待上传磅单"),
	status29("已上传磅单"),

	status30("等待评价"),
	status31("已评价");

	private String description;

	public static List<Map<String, Object>> getMaps() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for (AppOrderStatus item : AppOrderStatus.values()) {
			list.add(item.getMap());
		}
		return list;
	}
	
	public static AppOrderStatus valueOf(int ordinal) {
		if (ordinal < 0 || ordinal >= values().length) {
			throw new IndexOutOfBoundsException("Invalid ordinal");
		}
		return values()[ordinal];
	}
	
	public static AppOrderStatus find(String description) {
		if (StringUtils.isNotEmpty(description)) {
			for (AppOrderStatus item : AppOrderStatus.values()) {
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

	private AppOrderStatus(String description) {
		this.description = description;
	}

	@JsonValue
	public String getDescription() {
		return description;
	}
}
