package com.aptech.business.operation.userOperateLog.domain;

import java.util.Date;

import org.apache.ibatis.type.Alias;

import com.aptech.framework.json.JsonDateDeserializer;
import com.aptech.framework.json.JsonDateSerializer;
import com.aptech.framework.json.JsonDateTime1Deserializer;
import com.aptech.framework.json.JsonDateTime1Serializer;
import com.aptech.framework.orm.BaseEntity;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * 
 * 用户操作实体类
 *
 * @author 
 * @created 2018-04-09 10:36:54
 * @lastModified 
 * @history
 *
 */
@Alias("OperateLogEntity")
public class OperateLogEntity extends BaseEntity{
		/**
		 * 
		 */
		private static final long serialVersionUID = -3012885835135713041L;
		/**
		 * 主键
		 */
    	private Long id;
		/**
		 * 用户id
		 */
    	private Long userId;
		/**
		 * 创建时间
		 */
    	private Date createDate;
		/**
		 * 登录名
		 */
    	private String loginName;
		/**
		 * 姓名
		 */
    	private String userName;
		/**
		 * 操作模块
		 */
    	private String moduleName;
		/**
		 * 操作描述
		 */
    	private String operateDesc;
		/**
		 * 操作Ip
		 */
    	private String ipAddress;
//    	private String operateUserName;
//    	private int operateUser;
    	
    	private Long operateType;
    	
    	
   
		public Long getOperateType() {
			return operateType;
		}
		public void setOperateType(Long operateType) {
			this.operateType = operateType;
		}
		public Long getId(){
			return id;
		}
		public void setId(Long id){
			this.id = id;
		}
		public Long getUserId(){
			return userId;
		}
		public void setUserId(Long userId){
			this.userId = userId;
		}
	    @JsonSerialize(using = JsonDateTime1Serializer.class)
		public Date getCreateDate(){
			return createDate;
		}
	    @JsonDeserialize(using = JsonDateTime1Deserializer.class)
		public void setCreateDate(Date createDate){
			this.createDate = createDate;
		}
		public String getLoginName(){
			return loginName;
		}
		public void setLoginName(String loginName){
			this.loginName = loginName;
		}
		public String getUserName(){
			return userName;
		}
		public void setUserName(String userName){
			this.userName = userName;
		}
		public String getModuleName(){
			return moduleName;
		}
		public void setModuleName(String moduleName){
			this.moduleName = moduleName;
		}
		public String getOperateDesc(){
			return operateDesc;
		}
		public void setOperateDesc(String operateDesc){
			this.operateDesc = operateDesc;
		}
		public String getIpAddress(){
			return ipAddress;
		}
		public void setIpAddress(String ipAddress){
			this.ipAddress = ipAddress;
		}
}