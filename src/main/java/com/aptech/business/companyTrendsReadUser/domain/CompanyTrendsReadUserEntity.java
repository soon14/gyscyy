package com.aptech.business.companyTrendsReadUser.domain;

import org.apache.ibatis.type.Alias;

import com.aptech.framework.orm.BaseEntity;

/**
 * 
 * 公司动态已读用户实体类
 *
 * @author 
 * @created 2018-07-27 11:24:27
 * @lastModified 
 * @history
 *
 */
@Alias("CompanyTrendsReadUserEntity")
public class CompanyTrendsReadUserEntity extends BaseEntity{
		/**
	 * 
	 */
	private static final long serialVersionUID = -9139654342082989791L;
		/**
		 * 公司动态ID
		 */
    	private Long companyTrendsId;
		/**
		 * 用户ID
		 */
    	private Long userId;
		/**
		 * 用户名称
		 */
    	private String userName;

		public Long getCompanyTrendsId(){
			return companyTrendsId;
		}
		public void setCompanyTrendsId(Long companyTrendsId){
			this.companyTrendsId = companyTrendsId;
		}
		public Long getUserId(){
			return userId;
		}
		public void setUserId(Long userId){
			this.userId = userId;
		}
		public String getUserName(){
			return userName;
		}
		public void setUserName(String userName){
			this.userName = userName;
		}
}