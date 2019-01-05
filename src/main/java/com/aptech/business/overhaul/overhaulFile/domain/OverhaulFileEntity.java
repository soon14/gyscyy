package com.aptech.business.overhaul.overhaulFile.domain;

import java.util.Date;

import org.apache.ibatis.type.Alias;

import com.aptech.framework.json.JsonDateTimeDeserializer;
import com.aptech.framework.json.JsonDateTimeSerializer;
import com.aptech.framework.orm.BaseEntity;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * 
 * 检修文件包实体类
 *
 * @author 
 * @created 2017-08-04 14:04:07
 * @lastModified 
 * @history
 *
 */
@Alias("OverhaulFileEntity")
public class OverhaulFileEntity extends BaseEntity{
		/**
		 * 主键
		 */
    	private Long id;
		/**
		 * 文件名称
		 */
    	private String fileName;
		/**
		 * 上传人id
		 */
    	private Integer uploadUserId;
		/**
		 * 上传人姓名
		 */
    	private String uploadUserName;
		/**
		 * 上传时间
		 */
    	private Date uploadDate;
		/**
		 * 上传文件id
		 */
    	private String attchmentId;
    	
    	/**
    	 * 单位id
    	 */
    	private Long unitId;
		/**
		 * 状态
		 */
    	private String status;

		public Long getId(){
			return id;
		}
		public void setId(Long id){
			this.id = id;
		}
		public String getFileName(){
			return fileName;
		}
		public void setFileName(String fileName){
			this.fileName = fileName;
		}
		public Integer getUploadUserId(){
			return uploadUserId;
		}
		public void setUploadUserId(Integer uploadUserId){
			this.uploadUserId = uploadUserId;
		}
		public String getUploadUserName(){
			return uploadUserName;
		}
		public void setUploadUserName(String uploadUserName){
			this.uploadUserName = uploadUserName;
		}
	    @JsonSerialize(using = JsonDateTimeSerializer.class)
		public Date getUploadDate(){
			return uploadDate;
		}
	    @JsonDeserialize(using = JsonDateTimeDeserializer.class)
		public void setUploadDate(Date uploadDate){
			this.uploadDate = uploadDate;
		}
		public String getAttchmentId(){
			return attchmentId;
		}
		public void setAttchmentId(String attchmentId){
			this.attchmentId = attchmentId;
		}
		public String getStatus(){
			return status;
		}
		public void setStatus(String status){
			this.status = status;
		}
		public Long getUnitId() {
			return unitId;
		}
		public void setUnitId(Long unitId) {
			this.unitId = unitId;
		}
		
}