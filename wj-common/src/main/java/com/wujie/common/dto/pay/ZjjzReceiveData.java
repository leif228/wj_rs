package com.wujie.common.dto.pay;


import com.wujie.common.dto.BaseData;

/**
 * 充值明细（见证+收单）Data封装类
 * 
 * @author Administrator
 *
 */
public class ZjjzReceiveData extends BaseData {

	private static final long serialVersionUID = -1L;

	private String tranStatus = ""; // 交易状态 必输（0：成功，1：失败，2：异常,3:冲正，5：待处理）
	private String tranAmount = ""; // 交易金额 必输
	private String tranFee = ""; // 佣金费 必输
	private String payModel = ""; // 支付模式 必输 0-冻结支付 1-普通支付
	private String tranDate = ""; // 交易日期 必输
	private String tranTime = ""; // 交易时间 必输
	private String inCustAcctId = ""; // 订单转入子账户 可选
	private String inCustName = ""; // 订单转入子帐户名称 可选
	private String realInCustAcctId = ""; // 订单实际转入子账户 可选
	private String realInCustName = ""; // 订单实际子帐户名称 可选
	private String frontLogNo = ""; // 前置流水号 必输
	private String reserve = ""; // 交易描述 可选 当充值失败时，返回交易失败原因

	public String getTranStatus() {
		return tranStatus;
	}

	public void setTranStatus(String tranStatus) {
		this.tranStatus = tranStatus;
	}

	public String getTranAmount() {
		return tranAmount;
	}

	public void setTranAmount(String tranAmount) {
		this.tranAmount = tranAmount;
	}

	public String getTranFee() {
		return tranFee;
	}

	public void setTranFee(String tranFee) {
		this.tranFee = tranFee;
	}

	public String getPayModel() {
		return payModel;
	}

	public void setPayModel(String payModel) {
		this.payModel = payModel;
	}

	public String getTranDate() {
		return tranDate;
	}

	public void setTranDate(String tranDate) {
		this.tranDate = tranDate;
	}

	public String getTranTime() {
		return tranTime;
	}

	public void setTranTime(String tranTime) {
		this.tranTime = tranTime;
	}

	public String getInCustAcctId() {
		return inCustAcctId;
	}

	public void setInCustAcctId(String inCustAcctId) {
		this.inCustAcctId = inCustAcctId;
	}

	public String getInCustName() {
		return inCustName;
	}

	public void setInCustName(String inCustName) {
		this.inCustName = inCustName;
	}

	public String getRealInCustAcctId() {
		return realInCustAcctId;
	}

	public void setRealInCustAcctId(String realInCustAcctId) {
		this.realInCustAcctId = realInCustAcctId;
	}

	public String getRealInCustName() {
		return realInCustName;
	}

	public void setRealInCustName(String realInCustName) {
		this.realInCustName = realInCustName;
	}

	public String getFrontLogNo() {
		return frontLogNo;
	}

	public void setFrontLogNo(String frontLogNo) {
		this.frontLogNo = frontLogNo;
	}

	public String getReserve() {
		return reserve;
	}

	public void setReserve(String reserve) {
		this.reserve = reserve;
	}

}
