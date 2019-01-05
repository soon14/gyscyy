package com.aptech.business.mobile.userMobile.domain;

import org.apache.ibatis.type.Alias;

import com.aptech.framework.orm.BaseEntity;
@Alias("UserMobileEntity")
public class UserMobileEntity  extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7769876715548791555L;
	/**
	 * 登录名
	 */
	private String loginName;
	/**
	 * 手机标识
	 */
	private String mobileFlag;

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getMobileFlag() {
		return mobileFlag;
	}

	public void setMobileFlag(String mobileFlag) {
		this.mobileFlag = mobileFlag;
	}
	
	
}
