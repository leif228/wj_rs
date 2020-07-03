/**
 * Copyright (C), 2016-2018, 广州航运电子商务有限公司
 * FileName: LoginUserDto
 * Author:   Guoqiang
 * Date:     2018/11/23 下午1:58
 * Description: 用户
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.wujie.common.dto.user;

import com.wujie.common.dto.BaseData;

/**
 * 用户
 *
 * @author Guoqiang
 * @since 2018/11/23
 * @version 1.0.0
 */
public class LoginUserDto extends BaseData {
	private String uid;
	private String userName;
	private String password;
	private String role;
	private String client;
	private String logo;
	private String registrationId;
	private String simCardNo;
	private String bindingCode;

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getBindingCode() {
		return bindingCode;
	}

	public void setBindingCode(String bindingCode) {
		this.bindingCode = bindingCode;
	}

	public String getRegistrationId() {
		return registrationId;
	}

	public void setRegistrationId(String registrationId) {
		this.registrationId = registrationId;
	}

	public String getSimCardNo() {
		return simCardNo;
	}

	public void setSimCardNo(String simCardNo) {
		this.simCardNo = simCardNo;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}
}
