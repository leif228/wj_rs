package com.wujie.common.dto.pay;


import com.wujie.common.dto.BaseData;

/**
 * Created by admin on 2017/9/5.
 */

public class ScfUserBankPayCardData extends BaseData {

	private static final long serialVersionUID = -1L;
	
	private Long userId = 0L; // 用户ID
	private String orderId = ""; // 订单号
	private String openId = ""; // 银行开通的ID
	private String plantBankId = ""; // 支付平台银行ID
	private String plantBankName = ""; // 支付行名称
	private String accNo = ""; // 银行卡末尾四位
	private String bankType = ""; // 银行卡类型；01是借记卡，02是信用卡
	private String telephone = ""; // 银行预留手机号（中间四位为星号）
	private String bindTime = ""; // 绑定时间

	public ScfUserBankPayCardData() {
		super();
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getPlantBankId() {
		return plantBankId;
	}

	public void setPlantBankId(String plantBankId) {
		this.plantBankId = plantBankId;
	}

	public String getPlantBankName() {
		return plantBankName;
	}

	public void setPlantBankName(String plantBankName) {
		this.plantBankName = plantBankName;
	}

	public String getAccNo() {
		return accNo;
	}

	public void setAccNo(String accNo) {
		this.accNo = accNo;
	}

	public String getBankType() {
		return bankType;
	}

	public void setBankType(String bankType) {
		this.bankType = bankType;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getBindTime() {
		return bindTime;
	}

	public void setBindTime(String bindTime) {
		this.bindTime = bindTime;
	}
	
}
