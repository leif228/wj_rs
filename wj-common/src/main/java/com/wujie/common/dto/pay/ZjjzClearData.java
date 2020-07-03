package com.wujie.common.dto.pay;


import com.wujie.common.dto.BaseData;

import java.util.List;

/**
 * 银行在途清算结果Data封装类
 * @author Administrator
 *
 */
public class ZjjzClearData extends BaseData {

	private static final long serialVersionUID = -1L;

	private String hisDate = "";// 日期
	private String custType = ""; //子帐号类型 1: 普通会员子账号 2: 挂账子账号  3: 手续费子账号 4: 利息子账号 5: 平台担保子账号 7:在途 8:理财购买子帐号 9:理财赎回子帐号 10:平台子拥有结算子帐号
	private String checkStatus = "";// 对账状态 必输  0；成功 1：失败
	private String checkMsg = "";// 对账返回信息 可选	
	private String totalAmount = "";// 待清算总金额 可选	
	private String clearStatus = "";// 清算状态 必输  0：成功，1：失败，2：异常 3:待处理	
	private String clearMsg = "";// 清算返回信息  可选

	public ZjjzClearData() {
		super();
	}

	public ZjjzClearData(List<String> lst) {
		super();
		if (lst.size() > 0)
			this.hisDate = lst.get(0);
		if (lst.size() > 1)
			this.custType = lst.get(1);
		if (lst.size() > 2)
			this.checkStatus = lst.get(2);
		if (lst.size() > 3)
			this.checkMsg = lst.get(3);
		if (lst.size() > 4)
			this.totalAmount = lst.get(4);
		if (lst.size() > 5)
			this.clearStatus = lst.get(5);
		if (lst.size() > 6)
			this.clearMsg = lst.get(6);
	}

	public String getHisDate() {
		return hisDate;
	}

	public void setHisDate(String hisDate) {
		this.hisDate = hisDate;
	}

	public String getCustType() {
		return custType;
	}

	public void setCustType(String custType) {
		this.custType = custType;
	}

	public String getCheckStatus() {
		return checkStatus;
	}

	public void setCheckStatus(String checkStatus) {
		this.checkStatus = checkStatus;
	}

	public String getCheckMsg() {
		return checkMsg;
	}

	public void setCheckMsg(String checkMsg) {
		this.checkMsg = checkMsg;
	}

	public String getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getClearStatus() {
		return clearStatus;
	}

	public void setClearStatus(String clearStatus) {
		this.clearStatus = clearStatus;
	}

	public String getClearMsg() {
		return clearMsg;
	}

	public void setClearMsg(String clearMsg) {
		this.clearMsg = clearMsg;
	}

	public String toString() {
		return "日期:" + hisDate + ",子帐号类型:" + custType + ",对账状态:" + checkStatus + ",对账返回信息:" + checkMsg + ",待清算总金额:"
				+ totalAmount + ",清算状态:" + clearStatus + ",清算返回信息:" + clearMsg;
	}
}
