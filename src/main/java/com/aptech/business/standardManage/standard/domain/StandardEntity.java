package com.aptech.business.standardManage.standard.domain;

import java.util.Date;

import org.apache.ibatis.type.Alias;

import com.aptech.framework.json.JsonDateDeserializer;
import com.aptech.framework.json.JsonDateSerializer;
import com.aptech.framework.orm.BaseEntity;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * 
 * 标准管理实体类
 *
 * @author 
 * @created 2017-12-07 10:17:00
 * @lastModified 
 * @history
 *
 */
@Alias("StandardEntity")
public class StandardEntity extends BaseEntity{
		/**
	 * 
	 */
	private static final long serialVersionUID = -4806143772902107013L;
		/**
		 * 主键
		 */
		private Long id;
		/**
		 * 文件名称
		 */
		private String name;
		/**
		 * 文件编号
		 */
		private String code;
		/**
		 * 版本
		 */
		private String edition;
		/**
		 * 备注
		 */
		private String remark;
		/**
		 * 上传人
		 */
		private Long uploadUser;
		/**
		 * 上传人
		 */
		private String uploadUserName;
		/**
		 * 上传时间
		 */
		private Date uploadTime;
		/**
		 * 类型
		 */
		private Long type;
		/**
		 * 附件
		 */
		private String fileId;
		
		
		/**
		 * 归口单位id
		 */
		private String unitId;
		
		
		/**
		 * 归口单位名称
		 */
		private String unitName;

		public String getUploadUserName() {
			return uploadUserName;
		}
		public void setUploadUserName(String uploadUserName) {
			this.uploadUserName = uploadUserName;
		}
		public String getFileId() {
			return fileId;
		}
		public void setFileId(String fileId) {
			this.fileId = fileId;
		}
		public Long getId(){
			return id;
		}
		public void setId(Long id){
			this.id = id;
		}
		public String getName(){
			return name;
		}
		public void setName(String name){
			this.name = name;
		}
		public String getCode(){
			return code;
		}
		public void setCode(String code){
			this.code = code;
		}
		public String getEdition(){
			return edition;
		}
		public void setEdition(String edition){
			this.edition = edition;
		}
		public String getRemark(){
			return remark;
		}
		public void setRemark(String remark){
			this.remark = remark;
		}
		public Long getUploadUser(){
			return uploadUser;
		}
		public void setUploadUser(Long uploadUser){
			this.uploadUser = uploadUser;
		}
		@JsonSerialize(using = JsonDateSerializer.class)
		public Date getUploadTime(){
			return uploadTime;
		}
		@JsonDeserialize(using = JsonDateDeserializer.class)
		public void setUploadTime(Date uploadTime){
			this.uploadTime = uploadTime;
		}
		public Long getType(){
			return type;
		}
		public void setType(Long type){
			this.type = type;
		}
		public String getUnitId() {
			return unitId;
		}
		public void setUnitId(String unitId) {
			this.unitId = unitId;
		}
		public String getUnitName() {
			return unitName;
		}
		public void setUnitName(String unitName) {
			this.unitName = unitName;
		}
		
}