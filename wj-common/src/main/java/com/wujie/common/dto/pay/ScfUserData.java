/**
 * Copyright (C), 2016-2019, 广州航运电子商务有限公司
 * FileName: ScfUserData
 * Author:   Guoqiang
 * Date:     2019/1/5 上午10:50
 * Description: 用户类Stub
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.wujie.common.dto.pay;

import com.wujie.common.dto.BaseData;
import com.wujie.common.enums.ScfActiveCode;
import com.wujie.common.enums.ScfRoleCode;
import com.wujie.common.enums.ScfYesNoCode;
import com.wujie.common.utils.DESHelper;

import java.util.Date;
import java.util.Map;

/**
 * 用户类Stub
 *
 * @author Guoqiang
 * @since 2019/1/5
 * @version 1.0.0
 */
public class ScfUserData extends BaseData {

	private static final long serialVersionUID = 3952288859802553833L;
    
    /** 用户ID，主键 */
    private String id;	
	/** 电子邮箱 */
	private String email;
	/** 身份证号 */
	private String idCardNo;
	/** 登录名 */
	private String loginName;
	/** 手机号 */
	private String mobile;
	/** 昵称 */
	private String nickName;
	/** 用户密码 */
	private String password;
	/** 支付密码 */
	private String paypwd;
	/** 角色 */
	private ScfRoleCode role;
	/** SIM卡号 */
	private String simCardNo;
	/** 状态 */
	private ScfActiveCode status;
	/** 姓名 */
	private String trueName;
	/** 图标图片 */
	private String userLogo;
	/** 邀请人手机 */
	private String inviter;
	/** 所属顾问用户ID */
	private Long advisorId;
	/** 最后登录时间 */
	private Date lastLoginTime;
	/** 所挂公司数 */
	private Integer compCount;
	/** 是否认证 */
	private ScfYesNoCode isAuth;
	/** 是否绑卡 */
	private ScfYesNoCode isBindCard;
	/** 所挂船舶数 */
	private Integer shipCount;
	/** 所上报需求数，每船每天上报一次 */
	private Integer demondCount;

 
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setEmail(String email) 
	{
		this.email = email;
	}

	public String getEmail() 
	{
		return email;
	}
 
	public void setIdCardNo(String idCardNo) 
	{
		this.idCardNo = idCardNo;
	}

	public String getIdCardNo() 
	{
		return idCardNo;
	}
 
	public void setLoginName(String loginName) 
	{
		this.loginName = loginName;
	}

	public String getLoginName() 
	{
		return loginName;
	}
 
	public void setMobile(String mobile) 
	{
		this.mobile = mobile;
	}

	public String getMobile() 
	{
		if(this.mobile.matches("^\\d{11}$")){
			return mobile;
		}else{
			return DESHelper.DoDES(this.mobile, "e5102y1025d0251925108", DESHelper.DECRYPT_MODE);

		}
	}
 
	public void setNickName(String nickName) 
	{
		this.nickName = nickName;
	}

	public String getNickName() 
	{
		return nickName;
	}
 
	public void setPassword(String password) 
	{
		this.password = password;
	}

	public String getPassword() 
	{
		return password;
	}
 
	public void setPaypwd(String paypwd) 
	{
		this.paypwd = paypwd;
	}

	public String getPaypwd() 
	{
		return paypwd;
	}
 
	public void setRole(ScfRoleCode role) 
	{
		this.role = role;
	}

	public ScfRoleCode getRole() 
	{
		return role;
	}
 
	public void setSimCardNo(String simCardNo) 
	{
		this.simCardNo = simCardNo;
	}

	public String getSimCardNo() 
	{
		return simCardNo;
	}
 
	public void setStatus(ScfActiveCode status) 
	{
		this.status = status;
	}

	public ScfActiveCode getStatus() 
	{
		return status;
	}
 
	public void setTrueName(String trueName) 
	{
		this.trueName = trueName;
	}

	public String getTrueName() 
	{
		return trueName;
	}
 
	public void setUserLogo(String userLogo) 
	{
		this.userLogo = userLogo;
	}

	public String getUserLogo() 
	{
		return userLogo;
	}
 
	public void setInviter(String inviter) 
	{
		this.inviter = inviter;
	}

	public String getInviter() 
	{
		return inviter;
	}
 
	public void setAdvisorId(Long advisorId) 
	{
		this.advisorId = advisorId;
	}

	public Long getAdvisorId() 
	{
		return advisorId;
	}
 
	public void setLastLoginTime(Date lastLoginTime) 
	{
		this.lastLoginTime = lastLoginTime;
	}

	public Date getLastLoginTime() 
	{
		return lastLoginTime;
	}
 
	public void setCompCount(Integer compCount) 
	{
		this.compCount = compCount;
	}

	public Integer getCompCount() 
	{
		return compCount;
	}
 
	public void setIsAuth(ScfYesNoCode isAuth) 
	{
		this.isAuth = isAuth;
	}

	public ScfYesNoCode getIsAuth() 
	{
		return isAuth;
	}
 
	public void setIsBindCard(ScfYesNoCode isBindCard) 
	{
		this.isBindCard = isBindCard;
	}

	public ScfYesNoCode getIsBindCard() 
	{
		return isBindCard;
	}
 
	public void setShipCount(Integer shipCount) 
	{
		this.shipCount = shipCount;
	}

	public Integer getShipCount() 
	{
		return shipCount;
	}
 
	public void setDemondCount(Integer demondCount) 
	{
		this.demondCount = demondCount;
	}

	public Integer getDemondCount() 
	{
		return demondCount;
	}

    public ScfUserData(Map<String, Object> params) {
        super();
        if (params != null && !params.isEmpty()) {
            this.id = (String) params.get("id");
            this.mobile = (String) params.get("mobile");
            this.loginName = (String) params.get("loginName");
            this.nickName = (String) params.get("nickName");
            this.trueName = (String) params.get("trueName");
        }
    }
	public ScfUserData(){
		super();
	}

	public ScfUserData(String id, String email, String idCardNo, String loginName, String mobile, String nickName, String password, String paypwd, ScfRoleCode role, String simCardNo, ScfActiveCode status, String trueName, String userLogo, String inviter, Long advisorId, Date lastLoginTime, Integer compCount, ScfYesNoCode isAuth, ScfYesNoCode isBindCard, Integer shipCount, Integer demondCount) {
		this.id = id;
		this.email = email;
		this.idCardNo = idCardNo;
		this.loginName = loginName;
		this.mobile = mobile;
		this.nickName = nickName;
		this.password = password;
		this.paypwd = paypwd;
		this.role = role;
		this.simCardNo = simCardNo;
		this.status = status;
		this.trueName = trueName;
		this.userLogo = userLogo;
		this.inviter = inviter;
		this.advisorId = advisorId;
		this.lastLoginTime = lastLoginTime;
		this.compCount = compCount;
		this.isAuth = isAuth;
		this.isBindCard = isBindCard;
		this.shipCount = shipCount;
		this.demondCount = demondCount;
	}

}
