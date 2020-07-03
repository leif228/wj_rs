package com.wujie.common.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ScfYesNoCode {
	no("否"), yes("是");

	private String description;

	private ScfYesNoCode(String description) {
		this.description = description;
	}

	public static ScfYesNoCode valueOf(int ordinal) {
        if (ordinal < 0 || ordinal >= values().length) {
            throw new IndexOutOfBoundsException("Invalid ordinal");
        }
        return values()[ordinal];
    }
	
	@JsonValue
	public String getDescription() {
		return description;
	}
}
