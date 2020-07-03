package com.wujie.common.dto.pay;


import com.wujie.common.dto.BaseData;

import java.util.List;

/**
 * 银行费用扣收结果Data封装类
 * @author Administrator
 *
 */
public class ZjjzFeeData extends BaseData {

	private static final long serialVersionUID = -1L;

	private String feeFlag = "";// 费用类型 必输  1：提现手续费 2：会员验证费 3：服务费 
	private String feeFlagDesc = "";
	private String startDate = "";// 费用起始日期 必输
	private String endDate = "";// 费用结束日期 必输
	private String feeDate = "";// 费用扣收日期 必输
	private String totalAmount = "";// 收费金额 必输	
	private String tranStatus = "";// 交易状态 必输 0 成功 1失败 2超时 3未处理

	public ZjjzFeeData() {
		super();
	}

	public ZjjzFeeData(List<String> lst) {
		super();
		if (lst.size() > 0)
			this.setFeeFlag(lst.get(0));
		if (lst.size() > 1)
			this.startDate = lst.get(1);
		if (lst.size() > 2)
			this.endDate = lst.get(2);
		if (lst.size() > 3)
			this.feeDate = lst.get(3);
		if (lst.size() > 4)
			this.totalAmount = lst.get(4);
		if (lst.size() > 5)
			this.tranStatus = lst.get(5);
	}

	public String getFeeFlag() {
		return feeFlag;
	}

	public void setFeeFlag(String feeFlag) {
		this.feeFlag = feeFlag;
		this.feeFlagDesc = "1".equals(feeFlag) ? "提现手续费" : "2".equals(feeFlag) ? "会员验证费" : "3".equals(feeFlag) ? "服务费" : "";
	}

	public String getFeeFlagDesc() {
		return feeFlagDesc;
	}

	public void setFeeFlagDesc(String feeFlagDesc) {
		this.feeFlagDesc = feeFlagDesc;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getFeeDate() {
		return feeDate;
	}

	public void setFeeDate(String feeDate) {
		this.feeDate = feeDate;
	}

	public String getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getTranStatus() {
		return tranStatus;
	}

	public void setTranStatus(String tranStatus) {
		this.tranStatus = tranStatus;
	}

	public String toString() {
		return "费用类型:" + feeFlag + ",费用起始日期:" + startDate + ",费用结束日期:" + endDate + ",费用扣收日期:" + feeDate + ",收费金额:"
				+ totalAmount + ",交易状态:" + tranStatus;
	}
}
