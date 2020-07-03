package com.wujie.common.enums;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum ScfCargoBigTypeCode {
	container("集装箱"),

	bulk("散货"),

	danger("危险品"),

	itemBulk("件杂货");

	private String description;
	private List<ScfCargoTypeCode> cargoTypes;

	public static List<Map<String, Object>> getMaps() {
		List<Map<String, Object>> bigCargoTypes = new ArrayList<Map<String, Object>>();
		for (ScfCargoBigTypeCode cargoBigType : ScfCargoBigTypeCode.values()) {
			bigCargoTypes.add(cargoBigType.getMap());
		}
		return bigCargoTypes;
	}

	public Map<String, Object> getMap() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("no", this.ordinal());
		map.put("name", this.name());
		map.put("description", this.description);

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		if (this.cargoTypes == null || this.cargoTypes.isEmpty())
			this.cargoTypes = ScfCargoTypeCode.getCodesByCargoBigType(this);

		for (ScfCargoTypeCode ct : this.cargoTypes) {
			list.add(ct.getMap());
		}
		map.put("cargoTypes", list);

		return map;
	}

	@JsonValue
	public String getDescription() {
		return description;
	}

	public List<ScfCargoTypeCode> getCargoTypes() {
		if (this.cargoTypes == null)
			this.cargoTypes = ScfCargoTypeCode.getCodesByCargoBigType(this);
		return cargoTypes;
	}

	private ScfCargoBigTypeCode(String description) {
		this.description = description;
	}
	public static ScfCargoBigTypeCode valueOf(int ordinal) {
        if (ordinal < 0 || ordinal >= values().length) {
            throw new IndexOutOfBoundsException("Invalid ordinal");
        }
        return values()[ordinal];
    }
	public static void main(String[] args) {
		System.out.println(ScfCargoBigTypeCode.getMaps());
	}

}
