package com.wujie.common.dto.company;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wujie.common.enums.FinCompanyCertTypeCode;
import com.wujie.common.enums.ScfRoleCode;
import com.wujie.common.enums.ScfYesNoCode;
import com.wujie.common.enums.VerifyStatusCode;

import java.io.Serializable;
import java.util.Date;

/**
 * 公司DTO
 */
public class CompanyDto implements Serializable {
	private static final long serialVersionUID = 1L;

	/** 主键 */
	private String id;
	/** 用户ID */
	private String userId;
	/** 公司名称 */
	private String compName;
	/** 办公地址 */
	private String address;
	/** 角色 */
	private ScfRoleCode role;
	/** 自认证文件路径 */
	private String url;
	/** 是否自认证 */
	private ScfYesNoCode selfAuth;
	/** 是否公司认证 */
	private ScfYesNoCode companyAuth;
	/** 复核状态；初始为未审核状态 */
	private VerifyStatusCode verifyStatus;
	/** 复核时间 */
	private String verifyTime;
	/** 证件类型 */
	private FinCompanyCertTypeCode ctCode;
	/** 证件号码 */
	private String ctCodeStr;
	/** 认证时间 */
	private String auditTime;
	/** 客户登录名 */
	private String loginName;
	/** 客户姓名 */
	private String trueName;
	/** 客户手机号 */
	private String mobile;
	/** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;

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

	public String getCompName() {
		return compName;
	}

	public void setCompName(String compName) {
		this.compName = compName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public ScfRoleCode getRole() {
		return role;
	}

	public void setRole(ScfRoleCode role) {
		this.role = role;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public ScfYesNoCode getSelfAuth() {
		return selfAuth;
	}

	public void setSelfAuth(ScfYesNoCode selfAuth) {
		this.selfAuth = selfAuth;
	}

	public ScfYesNoCode getCompanyAuth() {
		return companyAuth;
	}

	public void setCompanyAuth(ScfYesNoCode companyAuth) {
		this.companyAuth = companyAuth;
	}

	public VerifyStatusCode getVerifyStatus() {
		return verifyStatus;
	}

	public void setVerifyStatus(VerifyStatusCode verifyStatus) {
		this.verifyStatus = verifyStatus;
	}

	public String getVerifyTime() {
		return verifyTime;
	}

	public void setVerifyTime(String verifyTime) {
		this.verifyTime = verifyTime;
	}

	public FinCompanyCertTypeCode getCtCode() {
		return ctCode;
	}

	public void setCtCode(FinCompanyCertTypeCode ctCode) {
		this.ctCode = ctCode;
	}

	public String getCtCodeStr() {
		return ctCodeStr;
	}

	public void setCtCodeStr(String ctCodeStr) {
		this.ctCodeStr = ctCodeStr;
	}

	public String getAuditTime() {
		return auditTime;
	}

	public void setAuditTime(String auditTime) {
		this.auditTime = auditTime;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getTrueName() {
		return trueName;
	}

	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
