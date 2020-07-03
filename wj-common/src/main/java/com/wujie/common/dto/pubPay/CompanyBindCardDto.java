package com.wujie.common.dto.pubPay;

import com.wujie.common.base.BaseEntity;
import com.wujie.common.enums.BankCode;

public class CompanyBindCardDto extends BaseEntity {

	private static final long serialVersionUID = 1L;
	
	/** 财务用户ID */
	private String userId;

	/** 财务公司ID */
	private String companyId;
	
	/** 开户银行ID */
	private Long openBankId;
	
	/** 公司证件号 */
	private String idCode;
	
	/** 卡名称 */
	private String cardName;
	
	/** 卡号 */
	private String cardNo;
	
	/** 预留手机号 */
	private String mobilePhone;
	
	/** 银行编码 */
	private BankCode bankCode;	

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public Long getOpenBankId() {
		return openBankId;
	}

	public void setOpenBankId(Long openBankId) {
		this.openBankId = openBankId;
	}

	public String getIdCode() {
		return idCode;
	}

	public void setIdCode(String idCode) {
		this.idCode = idCode;
	}

	public String getCardName() {
		return cardName;
	}

	public void setCardName(String cardName) {
		this.cardName = cardName;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public BankCode getBankCode() {
		return bankCode;
	}

	public void setBankCode(BankCode bankCode) {
		this.bankCode = bankCode;
	}

}
