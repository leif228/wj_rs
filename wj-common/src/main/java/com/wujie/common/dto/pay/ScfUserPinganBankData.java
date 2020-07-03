package com.wujie.common.dto.pay;


import com.wujie.common.dto.BaseData;
import com.wujie.common.enums.BankCode2;

import java.util.Map;

/**
 * Created by admin on 2017/9/5.
 */

public class ScfUserPinganBankData extends BaseData {

	private static final long serialVersionUID = -1L;
	
	private String payType = ""; //‘03’表示银联快捷
	private String bindId = ""; 
	private String openId = ""; 
	private String customerId = "";
	private String accNo = ""; //卡号

	private BankCode2 bankCode = null;
	
	//add
	private ScfUserBankPayCardData payCardData = null;  //绑定的银联快捷支付卡

	public ScfUserPinganBankData() {
		super();
	}

	public ScfUserPinganBankData(Map<String, Object> params) {
		super();
		if (params != null && !params.isEmpty()) {
			this.payType = (String) params.get("payType");
			this.bindId = (String) params.get("bindId");
			this.openId = (String) params.get("openId");
			this.customerId = (String) params.get("customerId");
			this.accNo = (String) params.get("accNo");

			String bankCode = (String) params.get("bankCode");
			if ((bankCode != null) && (!bankCode.equals(""))) {
				this.bankCode = BankCode2.valueOf(bankCode);
			}
		}
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getBindId() {
		return bindId;
	}

	public void setBindId(String bindId) {
		this.bindId = bindId;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getAccNo() {
		return accNo;
	}

	public void setAccNo(String accNo) {
		this.accNo = accNo;
	}

	public BankCode2 getBankCode() {
		return bankCode;
	}

	public void setBankCode(BankCode2 bankCode) {
		this.bankCode = bankCode;
	}

	public ScfUserBankPayCardData getPayCardData() {
		return payCardData;
	}

	public void setPayCardData(ScfUserBankPayCardData payCardData) {
		this.payCardData = payCardData;
	}
}
