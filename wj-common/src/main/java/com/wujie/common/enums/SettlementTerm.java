package com.wujie.common.enums;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum SettlementTerm {
	term1("运单签署"),
	term2("到装货码头报到"),
	term3("装货完毕"),
	term4("到卸货码头报到"),
	term5("开始卸货"),
	term6("卸货完毕"),
	term7("收到签署运单"),
	termyd2("货方收到卸货完毕单证后2天内"),
	termyd3("货方收到卸货完毕单证后3天内"),
	termyd4("货方收到卸货完毕单证后4天内"),
	termyd5("货方收到卸货完毕单证后5天内"),
	termyd6("货方收到卸货完毕单证后6天内"),
	termyd7("货方收到卸货完毕单证后7天内"),
	termyd10("货方收到卸货完毕单证后10天内"),
	termyd15("货方收到卸货完毕单证后15天内"),
	termyd30("货方收到卸货完毕单证后30天内");

	private String description;

	public static List<Map<String, Object>> getMaps() {
		List<Map<String, Object>> payStyles = new ArrayList<Map<String, Object>>();
		for (SettlementTerm payStyle : SettlementTerm.values()) {
			payStyles.add(payStyle.getMap());
		}
		return payStyles;
	}

	public Map<String, Object> getMap() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("no", this.ordinal());
		map.put("name", this.name());
		map.put("description", this.description);

		return map;
	}
	
	public static SettlementTerm valueOf(int ordinal) {
		if (ordinal < 0 || ordinal >= values().length) {
			throw new IndexOutOfBoundsException("Invalid ordinal");
		}
		return values()[ordinal];
	}

	private SettlementTerm(String description) {
		this.description = description;
	}

	@JsonValue
	public String getDescription() {
		return description;
	}
}
