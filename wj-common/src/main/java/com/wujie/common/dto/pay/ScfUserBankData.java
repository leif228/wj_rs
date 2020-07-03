package com.wujie.common.dto.pay;


import com.wujie.common.dto.BaseData;
import com.wujie.common.enums.BankCode;
import com.wujie.common.enums.ScfAccountTypeCode;
import com.wujie.common.enums.ScfYesNoCode;

import java.util.Map;

/**
 * Created by admin on 2017/9/5.
 */

public class ScfUserBankData extends BaseData {

	private static final long serialVersionUID = -1L;

	private String id = "";
	private String userId;
	private String accountName = "";
	private String cardNo = "";
	private BankCode bankCode = BankCode.EYUNDA;
	private String bindTime = ""; // 绑定时间
	private ScfYesNoCode isRcvCard = ScfYesNoCode.no;
	private ScfAccountTypeCode accountType = ScfAccountTypeCode.general; //账户类型

	private String bindId = ""; // 平安银行绑定id
	private String bankName = "";
	private String telephone = ""; // 银行预留手机号

	public ScfUserBankData() {
		super();
	}

	public ScfUserBankData(Map<String, Object> params) {
		super();
		if (params != null && !params.isEmpty()) {
			this.id = (String) params.get("id");
			this.userId = (String) params.get("userId");
			this.accountName = (String) params.get("accountName");
			this.cardNo = (String) params.get("cardNo");
			this.bindId = (String) params.get("bindId");
			this.bankName = (String) params.get("bankName");
			this.telephone = (String) params.get("telephone");

			String bankCode = (String) params.get("bankCode");
			if ((bankCode != null) && (!bankCode.equals(""))) {
				this.bankCode = BankCode.valueOf(bankCode);
			}

			this.bindTime = (String) params.get("bindTime");

			String isRcvCard = (String) params.get("isRcvCard");
			if ((isRcvCard != null) && (!isRcvCard.equals(""))) {
				this.isRcvCard = ScfYesNoCode.valueOf(isRcvCard);
			}

			String accountType = (String) params.get("accountType");
			if ((accountType != null) && (!accountType.equals(""))) {
				this.accountType = ScfAccountTypeCode.valueOf(accountType);
			}
		}
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public BankCode getBankCode() {
		return bankCode;
	}

	public void setBankCode(BankCode bankCode) {
		this.bankCode = bankCode;
	}

	public String getBindTime() {
		return bindTime;
	}

	public void setBindTime(String bindTime) {
		this.bindTime = bindTime;
	}

	public ScfYesNoCode getIsRcvCard() {
		return isRcvCard;
	}

	public void setIsRcvCard(ScfYesNoCode isRcvCard) {
		this.isRcvCard = isRcvCard;
	}

	public ScfAccountTypeCode getAccountType() {
		return accountType;
	}

	public void setAccountType(ScfAccountTypeCode accountType) {
		this.accountType = accountType;
	}

	public String getBindId() {
		return bindId;
	}

	public void setBindId(String bindId) {
		this.bindId = bindId;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

}
