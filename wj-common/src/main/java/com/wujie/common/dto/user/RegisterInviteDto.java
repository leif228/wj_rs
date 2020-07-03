package com.wujie.common.dto.user;

public class RegisterInviteDto extends RegisterDto {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String shipName;
	private String aaTons;
	private String mmsi;

	public String getShipName() {
		return shipName;
	}

	public void setShipName(String shipName) {
		this.shipName = shipName;
	}

	public String getAaTons() {
		return aaTons;
	}

	public void setAaTons(String aaTons) {
		this.aaTons = aaTons;
	}

	public String getMmsi() {
		return mmsi;
	}

	public void setMmsi(String mmsi) {
		this.mmsi = mmsi;
	}

}
