package com.wujie.common.dto.pay;



import com.wujie.common.dto.BaseData;

import java.util.List;

/**
 * 会员绑定信息Data封装类
 * @author Administrator
 *
 */
public class ZjjzCustInfoData extends BaseData {

	private static final long serialVersionUID = -1L;

	private String supAcctId;	//资金汇总账号  必输
	private String custAcctId;	//子账户账号	 必输
	private String thirdCustId;	//交易网会员代码  必输
	private String custName;	//会员名称  必输
	private String idType;	//会员证件类型  必输	见文档附录的“接口证件类型说明”, 例如身份证，送1
	private String idCode;	//会员证件号码   必输
	private String acctId;	//会员账号   必输	提现的银行卡
	private String bankType;	//银行类型  必输	1：本行 2：他行
	private String bankName;	//开户行名称  必输	 若大小额行号不填则送超级网银号对应的银行名称，若填大小额行号则送大小额行号对应的银行名称
	private String bankCode;	//大小额行号  可选	BankCode和SBankCode两者二选一必填。见附录。
	private String sBankCode;	//超级网银号 可选	BankCode和SBankCode两者二选一必填。见附录。
	private String mobilePhone;	//手机号 必输

	public ZjjzCustInfoData() {
		super();
	}

	public ZjjzCustInfoData(List<String> lst) {
		super();
		if (lst.size() > 0)
			this.supAcctId = lst.get(0);
		if (lst.size() > 1)
			this.custAcctId = lst.get(1);
		if (lst.size() > 2)
			this.thirdCustId = lst.get(2);
		if (lst.size() > 3)
			this.custName = lst.get(3);
		if (lst.size() > 4)
			this.idType = lst.get(4);
		if (lst.size() > 5)
			this.idCode = lst.get(5);
		if (lst.size() > 6)
			this.acctId = lst.get(6);
		if (lst.size() > 7)
			this.bankType = lst.get(7);
		if (lst.size() > 8)
			this.bankName = lst.get(8);
		if (lst.size() > 9)
			this.bankCode = lst.get(9);
		if (lst.size() > 10)
			this.sBankCode = lst.get(10);
		if (lst.size() > 11)
			this.mobilePhone = lst.get(11);
	}
	

	public String getSupAcctId() {
		return supAcctId;
	}

	public void setSupAcctId(String supAcctId) {
		this.supAcctId = supAcctId;
	}

	public String getCustAcctId() {
		return custAcctId;
	}

	public void setCustAcctId(String custAcctId) {
		this.custAcctId = custAcctId;
	}

	public String getThirdCustId() {
		return thirdCustId;
	}

	public void setThirdCustId(String thirdCustId) {
		this.thirdCustId = thirdCustId;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getIdType() {
		return idType;
	}

	public void setIdType(String idType) {
		this.idType = idType;
	}

	public String getIdCode() {
		return idCode;
	}

	public void setIdCode(String idCode) {
		this.idCode = idCode;
	}

	public String getAcctId() {
		return acctId;
	}

	public void setAcctId(String acctId) {
		this.acctId = acctId;
	}

	public String getBankType() {
		return bankType;
	}

	public void setBankType(String bankType) {
		this.bankType = bankType;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getsBankCode() {
		return sBankCode;
	}

	public void setsBankCode(String sBankCode) {
		this.sBankCode = sBankCode;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	
	public String toString() {
		return "资金汇总账号:" + supAcctId + ",子账户账号:" + custAcctId + ",交易网会员代码:" + thirdCustId + ",会员名称:" + custName + ",会员证件类型:"
				+ idType + ",会员证件号码:" + idCode + ",会员账号:" + acctId + ",银行类型:" + bankType + ",开户行名称:"
				+ bankName + ",大小额行号:" + bankCode + ",超级网银号:" + sBankCode + ",手机号:" + mobilePhone;
	}
}
