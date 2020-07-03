package com.wujie.common.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum VerifyStatusCode {

	no_verify("未审核"),
	pass("审核通过"), 
	no_pass("审核未通过"),
	re_verify("等待重新审核");


	private String description;

	private VerifyStatusCode(String description) {
		this.description = description;
	}

	@JsonValue
	public String getDescription() {
		return description;
	}

	public static boolean contains(String ext) {
		VerifyStatusCode[] imgCodes = VerifyStatusCode.values();
		for(VerifyStatusCode imgCode : imgCodes){
			if(imgCode.getDescription().equals(ext.toLowerCase())){
				return true;
			}
		}
		return false;
	}
}
