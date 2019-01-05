package com.aptech.business.safeCheckUnit.domain;

import java.util.Date;

import org.apache.ibatis.type.Alias;

import com.aptech.framework.json.JsonDateDeserializer;
import com.aptech.framework.json.JsonDateSerializer;
import com.aptech.framework.orm.BaseEntity;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * 
 * 安全检查组织机构表实体类
 *
 * @author 
 * @created 2018-09-03 10:56:03
 * @lastModified 
 * @history
 *
 */
@Alias("SafeCheckUnitEntity")
public class SafeCheckUnitEntity extends BaseEntity{
		/**
		 * 
		 */
		private static final long serialVersionUID = 7285537969873158029L;
		/**
		 * 主键
		 */
		private Long id;
		/**
		 * 编码
		 */
		private String code;
		/**
		 * 名称
		 */
		private String name;
		/**
		 * 创建人Id
		 */
		private String createUserId;
		/**
		 * 创建人名称
		 */
		private String createUserName;
		/**
		 * 创建时间
		 */
		private Date createDate;
		/**
		 * 修改人
		 */
		private String updateUserId;
		/**
		 * 修改时间
		 */
		private Date updateDate;
		
		/**
		 * 组织机构ID
		 */
		private String unitId;

		public Long getId(){
			return id;
		}
		public void setId(Long id){
			this.id = id;
		}
		public String getCode(){
			return code;
		}
		public void setCode(String code){
			this.code = code;
		}
		public String getName(){
			return name;
		}
		public void setName(String name){
			this.name = name;
		}
		public String getCreateUserId(){
			return createUserId;
		}
		public void setCreateUserId(String createUserId){
			this.createUserId = createUserId;
		}
		
		public String getCreateUserName() {
			return createUserName;
		}
		public void setCreateUserName(String createUserName) {
			this.createUserName = createUserName;
		}
		@JsonSerialize(using = JsonDateSerializer.class)
		public Date getCreateDate(){
			return createDate;
		}
		@JsonDeserialize(using = JsonDateDeserializer.class)
		public void setCreateDate(Date createDate){
			this.createDate = createDate;
		}
		public String getUpdateUserId(){
			return updateUserId;
		}
		public void setUpdateUserId(String updateUserId){
			this.updateUserId = updateUserId;
		}
		@JsonSerialize(using = JsonDateSerializer.class)
		public Date getUpdateDate(){
			return updateDate;
		}
		@JsonDeserialize(using = JsonDateDeserializer.class)
		public void setUpdateDate(Date updateDate){
			this.updateDate = updateDate;
		}
		public String getUnitId() {
			return unitId;
		}
		public void setUnitId(String unitId) {
			this.unitId = unitId;
		}
}