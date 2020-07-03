package com.wujie.common.dto.pay;


import com.wujie.common.dto.BaseData;
import com.wujie.common.enums.PayStyleCode;
import com.wujie.common.enums.ScfYesNoCode;

import java.util.Map;

public class ScfAccountData extends BaseData {
	private static final long serialVersionUID = -1L;

	private Long id = 0L; // ID
	private Long userId = 0L; // 用户ID
	private String accounter = ""; // 开户人
	private PayStyleCode payStyle = PayStyleCode.alipay; // 支付方式
	private String accountNo = ""; // 账号
	private String accountNoBuz = ""; // 账号
	private ScfYesNoCode payPassWord = ScfYesNoCode.no; // 设置支付密码
	private String idCode = ""; // 身份证号码
	private String mobile = ""; // 平安绑定用手机
	private String mobileBuz = ""; // 平安绑定用手机
	public ScfAccountData() {
		super();
	}

	public ScfAccountData(Map<String, Object> params) {
		super();
		if (params != null && !params.isEmpty()) {
			this.id = ((Double) params.get("id")).longValue();
			this.userId = ((Double) params.get("userId")).longValue();
			this.accounter = (String) params.get("accounter");
			this.payStyle = PayStyleCode.valueOf((String) params.get("payStyle"));
			this.accountNo = (String) params.get("accountNo");
			this.accountNoBuz = (String) params.get("accountNoBuz");
			this.payPassWord = ScfYesNoCode.valueOf((String) params.get("payPassWord"));
			this.idCode = (String) params.get("idCode");
			this.mobile = (String) params.get("mobile");
			this.mobileBuz = (String) params.get("mobileBuz");
		}
	}

	public String getAccountNoBuz() {
		return accountNoBuz;
	}

	public void setAccountNoBuz(String accountNoBuz) {
		this.accountNoBuz = accountNoBuz;
	}

	public String getMobileBuz() {
		return mobileBuz;
	}

	public void setMobileBuz(String mobileBuz) {
		this.mobileBuz = mobileBuz;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getAccounter() {
		return accounter;
	}

	public void setAccounter(String accounter) {
		this.accounter = accounter;
	}

	public PayStyleCode getPayStyle() {
		return payStyle;
	}

	public void setPayStyle(PayStyleCode payStyle) {
		this.payStyle = payStyle;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public ScfYesNoCode getPayPassWord() {
		return payPassWord;
	}

	public void setPayPassWord(ScfYesNoCode payPassWord) {
		this.payPassWord = payPassWord;
	}

	public String getIdCode() {
		return idCode;
	}

	public void setIdCode(String idCode) {
		this.idCode = idCode;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
