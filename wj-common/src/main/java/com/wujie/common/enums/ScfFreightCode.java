package com.wujie.common.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum ScfFreightCode {
	edit("编辑", "货方编辑运单", ""),

	published("运单发布", "货方发布运单", ""),

	sended("推送消息", "平台推送消息", "平台推送消息"),

	rushed("船方抢单", "船方已抢单待批", "船方已抢单待批"),

	audited("货方定船", "货方已交成约金并选定你船", "货方已交成约金并选定你船"),

	entered("船方确认", "船方交成约金并确认运单", "船方交成约金并确认运单"),

	loading("装货中", "船舶已到港", "货方已交担保金"),

	transporting("运输中", "船方确认装货完毕", "货方确认装货完毕"),

	close("正常关闭", "船方已评价", "货方已评价"),

	cancel("运单取消", "运单已取消", "运单已取消"),

	sysCancel("系统自动取消","运单已被系统取消", "运单已被系统取消");
	
	private String description;
	private String cargoRemark;
	private String shipRemark;

	//取得运单的状态列表
	public static List<Map<String, Object>> getOrderStatusMaps() {
		List<Map<String, Object>> codes = new ArrayList<Map<String, Object>>();
		for (ScfFreightCode code : ScfFreightCode.values()) {
			if (code.ordinal() >= ScfFreightCode.audited.ordinal()
					&& code.ordinal() != ScfFreightCode.sysCancel.ordinal())
				codes.add(code.getMap());
		}
		return codes;
	}

	public static ScfFreightCode valueOf(int ordinal) {
        if (ordinal < 0 || ordinal >= values().length) {
            throw new IndexOutOfBoundsException("Invalid ordinal");
        }
        return values()[ordinal];
    }
	public static List<Map<String, Object>> getMaps() {
		List<Map<String, Object>> codes = new ArrayList<Map<String, Object>>();
		for (ScfFreightCode code : ScfFreightCode.values()) {
			codes.add(code.getMap());
		}
		return codes;
	}

	public Map<String, Object> getMap() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("no", this.ordinal());
		map.put("name", this.name());
		map.put("description", this.description);
		map.put("cargoRemark", this.cargoRemark);
		map.put("shipRemark", this.shipRemark);
		return map;
	}

	private ScfFreightCode(String description, String cargoRemark, String shipRemark) {
		this.description = description;
		this.cargoRemark = cargoRemark;
		this.shipRemark = shipRemark;
	}

	public String getDescription() {
		return description;
	}

	public String getCargoRemark() {
		return cargoRemark;
	}

	public String getShipRemark() {
		return shipRemark;
	}

}
