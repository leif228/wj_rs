package com.wujie.common.dto.pay;


import com.wujie.common.dto.BaseData;

import java.util.List;

/**
 * 历史余额及待转可提现状态信息Data封装类
 * @author Administrator
 *
 */
public class ZjjzHistoryFetchData extends BaseData {

	private static final long serialVersionUID = -1L;

	private String hisDate = "";// 日期 必输	
	private String lastBalance = "";// 日终可用余额 必输
	private String lastAmount = "";// 日终可提现余额 必输	
	private String lastFreezeAmount = "";// 日终冻结余额 必输	
	private String lastNewWaitAmount = "";// 当日待转可提现发生额 必输	
	private String lastWaitAmount = "";// 日终待转可提现余额 必输	
	private String status = "";// 待转可提现状态 必输	 0：待转 1：已转 2：无需转  3：异常

	public ZjjzHistoryFetchData() {
		super();
	}

	public ZjjzHistoryFetchData(List<String> lst) {
		super();
		if (lst.size() > 0)
			this.hisDate = lst.get(0);
		if (lst.size() > 1)
			this.lastBalance = lst.get(1);
		if (lst.size() > 2)
			this.lastAmount = lst.get(2);
		if (lst.size() > 3)
			this.lastFreezeAmount = lst.get(3);
		if (lst.size() > 4)
			this.lastNewWaitAmount = lst.get(4);
		if (lst.size() > 5)
			this.lastWaitAmount = lst.get(5);
		if (lst.size() > 6)
			this.status = lst.get(6);
	}

	public String getHisDate() {
		return hisDate;
	}

	public void setHisDate(String hisDate) {
		this.hisDate = hisDate;
	}

	public String getLastBalance() {
		return lastBalance;
	}

	public void setLastBalance(String lastBalance) {
		this.lastBalance = lastBalance;
	}

	public String getLastAmount() {
		return lastAmount;
	}

	public void setLastAmount(String lastAmount) {
		this.lastAmount = lastAmount;
	}

	public String getLastFreezeAmount() {
		return lastFreezeAmount;
	}

	public void setLastFreezeAmount(String lastFreezeAmount) {
		this.lastFreezeAmount = lastFreezeAmount;
	}

	public String getLastNewWaitAmount() {
		return lastNewWaitAmount;
	}

	public void setLastNewWaitAmount(String lastNewWaitAmount) {
		this.lastNewWaitAmount = lastNewWaitAmount;
	}

	public String getLastWaitAmount() {
		return lastWaitAmount;
	}

	public void setLastWaitAmount(String lastWaitAmount) {
		this.lastWaitAmount = lastWaitAmount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String toString() {
		return "日期:" + hisDate + ",日终可用余额:" + lastBalance + ",日终可提现余额:" + lastAmount + ",日终冻结余额:" + lastFreezeAmount 
				+ ",当日待转可提现发生额:" + lastNewWaitAmount + ",日终待转可提现余额:" + lastWaitAmount + ",待转可提现状态:" + status;
	}
}
