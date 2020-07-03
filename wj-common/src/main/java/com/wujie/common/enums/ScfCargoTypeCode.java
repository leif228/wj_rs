package com.wujie.common.enums;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.*;

public enum ScfCargoTypeCode {

	container20e("20’吉", ScfCargoBigTypeCode.container, true, 40),

	container20f("20’重", ScfCargoBigTypeCode.container, true, 40),

	container40e("40’吉", ScfCargoBigTypeCode.container, true, 80),

	container40f("40’重", ScfCargoBigTypeCode.container, true, 80),

	container45e("45’吉", ScfCargoBigTypeCode.container, false, 90),

	container45f("45’重", ScfCargoBigTypeCode.container, false, 90),

	coal("煤炭", ScfCargoBigTypeCode.bulk, true),

	metalmineral("矿石", ScfCargoBigTypeCode.bulk, true),

	steel("钢铁", ScfCargoBigTypeCode.bulk, true),

	metal("沙子", ScfCargoBigTypeCode.bulk, true),

	tile("泥", ScfCargoBigTypeCode.bulk, true),

	wood("木材", ScfCargoBigTypeCode.bulk, true),

	cement("水泥", ScfCargoBigTypeCode.bulk, true),

	stone("石子", ScfCargoBigTypeCode.bulk, true),

	manure("化肥农药", ScfCargoBigTypeCode.bulk, false),

	salt("熟料", ScfCargoBigTypeCode.bulk, true),

	food("粮食", ScfCargoBigTypeCode.bulk, true),

	machinery("机械电器", ScfCargoBigTypeCode.bulk, false),

	chemicals("化工", ScfCargoBigTypeCode.bulk, false),

	industry("轻工医药", ScfCargoBigTypeCode.bulk, false),

	industrial("日用品", ScfCargoBigTypeCode.bulk, false),

	farming("农林牧渔", ScfCargoBigTypeCode.bulk, false),

	cotton("棉花", ScfCargoBigTypeCode.bulk, false),

	otherBulk("其它", ScfCargoBigTypeCode.bulk, true),

	explosives("爆炸品", ScfCargoBigTypeCode.danger, false),

	liquefied("液化气体", ScfCargoBigTypeCode.danger, false),

	flammable("易燃液体", ScfCargoBigTypeCode.danger, false),

	flammablesolids("易燃固体", ScfCargoBigTypeCode.danger, false),

	oxidizing("氧化剂", ScfCargoBigTypeCode.danger, false),

	poisons("毒害品", ScfCargoBigTypeCode.danger, false),

	radioactive("放射性物品", ScfCargoBigTypeCode.danger, false),

	corrosives("腐蚀品", ScfCargoBigTypeCode.danger, false),

	miscellaneous("杂类", ScfCargoBigTypeCode.danger, false),

	i_coal("煤炭", ScfCargoBigTypeCode.itemBulk, true),

	i_metalmineral("矿石", ScfCargoBigTypeCode.itemBulk, true),

	i_steel("钢铁", ScfCargoBigTypeCode.itemBulk, true),

	i_metal("沙子", ScfCargoBigTypeCode.itemBulk, true),

	i_tile("泥", ScfCargoBigTypeCode.itemBulk, true),

	i_wood("木材", ScfCargoBigTypeCode.itemBulk, true),

	i_cement("水泥", ScfCargoBigTypeCode.itemBulk, true),

	i_manure("化肥农药", ScfCargoBigTypeCode.itemBulk, false),

	i_salt("盐", ScfCargoBigTypeCode.itemBulk, false),

	i_food("粮食", ScfCargoBigTypeCode.itemBulk, true),

	i_machinery("机械电器", ScfCargoBigTypeCode.itemBulk, false),

	i_chemicals("化工", ScfCargoBigTypeCode.itemBulk, false),

	i_industry("轻工医药", ScfCargoBigTypeCode.itemBulk, false),

	i_industrial("日用品", ScfCargoBigTypeCode.itemBulk, false),

	i_farming("农林牧渔", ScfCargoBigTypeCode.itemBulk, false),

	i_cotton("棉花", ScfCargoBigTypeCode.itemBulk, false),

	i_otherBulk("其它", ScfCargoBigTypeCode.itemBulk, true);

	private String description;
	private ScfCargoBigTypeCode cargoBigType;
	private boolean isUsed;
	private int number;

	public static List<Map<String, Object>> getMaps() {
		List<Map<String, Object>> maps = new ArrayList<Map<String, Object>>();
		for (ScfCargoTypeCode e : ScfCargoTypeCode.values()) {
			maps.add(e.getMap());
		}
		return maps;
	}

	public Map<String, Object> getMap() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("no", this.ordinal());
		map.put("name", this.name());
		map.put("description", this.description);
		map.put("cargoBigType", this.cargoBigType.name());
		map.put("isUsed", this.isUsed);
		map.put("number", this.number);
		return map;
	}

	public static ScfCargoTypeCode valueOf(int ordinal) {
		if (ordinal < 0 || ordinal >= values().length) {
			throw new IndexOutOfBoundsException("Invalid ordinal");
		}
		return values()[ordinal];
	}

	public static List<ScfCargoTypeCode> getCodesByCargoBigType(ScfCargoBigTypeCode cbtc) {
		Map<Integer, ScfCargoTypeCode> map = new TreeMap<Integer, ScfCargoTypeCode>();
		for (ScfCargoTypeCode e : ScfCargoTypeCode.values()) {
			if (e.getCargoBigType() == cbtc && e.getIsUsed()) {
				map.put(e.ordinal(), e);
			}
		}

		List<ScfCargoTypeCode> results = new ArrayList<ScfCargoTypeCode>();
		for (Integer key : map.keySet()) {
			results.add(map.get(key));
		}

		return results;
	}

	public static List<Map<String, Object>> getMapsByCargoBigType(ScfCargoBigTypeCode cargoBigType) {

		List<Map<String, Object>> results = new ArrayList<Map<String, Object>>();
		
		for (ScfCargoTypeCode e : ScfCargoTypeCode.values()) {
			if (e.getCargoBigType() == cargoBigType && e.getIsUsed()) {
				results.add(e.getMap());
			}
		}

		return results;
	}

	public static ScfCargoTypeCode getByDescription(String description) {
		for (ScfCargoTypeCode e : ScfCargoTypeCode.values()) {
			if (e.getDescription() == description) {
				return e;
			}
		}
		return null;
	}

	@JsonValue
	public String getDescription() {
		return description;
	}

	public ScfCargoBigTypeCode getCargoBigType() {
		return cargoBigType;
	}

	public boolean getIsUsed() {
		return isUsed;
	}

	private ScfCargoTypeCode(String description, ScfCargoBigTypeCode cargoBigType, boolean isUsed) {
		this.description = description;
		this.cargoBigType = cargoBigType;
		this.isUsed = isUsed;
	}

	private ScfCargoTypeCode(String description, ScfCargoBigTypeCode cargoBigType, boolean isUsed, int number) {
		this.description = description;
		this.cargoBigType = cargoBigType;
		this.isUsed = isUsed;
		this.number = number;
	}
}
