package com.aptech.business.safeManage.organization.domain;

import java.util.Date;

import org.apache.ibatis.type.Alias;

import com.aptech.framework.json.JsonDateTimeDeserializer;
import com.aptech.framework.json.JsonDateTimeSerializer;
import com.aptech.framework.orm.BaseEntity;
import com.aptech.framework.util.DateFormatUtil;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * 
 * 组织机构实体类
 *
 * @author 
 * @created 2018-03-27 16:31:43
 * @lastModified 
 * @history
 *
 */
@Alias("OrganizationEntity")
public class OrganizationEntity extends BaseEntity{
		/**
		 * 主键
		 */
    	private Long id;
		/**
		 * 组织机构名称
		 */
    	private String name;
		/**
		 * 编码
		 */
    	private String code;
		/**
		 * 创建时间
		 */
    	private Date createDate;
		/**
		 * 创建人
		 */
    	private String createUserId;
		/**
		 * 年号
		 */
    	private Date unitDate;
		/**
		 * 单位Id
		 */
    	private Long unitId;
		/**
		 * 单位名称
		 */
    	private String unitName;
		/**
		 * 发文号
		 */
    	private String docCode;
		/**
		 * 备注
		 */
    	private String remark;
		/**
		 * 附件
		 */
    	private String  fileId;
		/**
		 * 状态
		 */
    	private String status;
    	
    	private Long type;
    	private String userName;
    	
    	/**
    	 * 附件名称
    	 */
		private String[] fileName;
		/**
		 * 附件URL
		 */
		private String[] fileUrl;
    	/**
    	 * 年号
    	 */
    	private String unitDateString;
    	/**
    	 * 建立时间
    	 */
    	private String createDateString ;
    	

		public String getUserName() {
			return userName;
		}
		public void setUserName(String userName) {
			this.userName = userName;
		}
		public Long getType() {
			return type;
		}
		public void setType(Long type) {
			this.type = type;
		}
		public String[] getFileName() {
			return fileName;
		}
		public void setFileName(String[] fileName) {
			this.fileName = fileName;
		}
		public String[] getFileUrl() {
			return fileUrl;
		}
		public void setFileUrl(String[] fileUrl) {
			this.fileUrl = fileUrl;
		}
		public String getUnitDateString() {
			if (unitDateString !=null && unitDateString !="") {
				return unitDateString;
			}
			DateFormatUtil df= DateFormatUtil.getInstance("yyyy");
			if (this.unitDate==null) {
				return unitDateString;
			}
			return df.format(this.unitDate);
		}
		public void setUnitDateString(String unitDateString) {
			this.unitDateString = unitDateString;
		}
		public String getCreateDateString() {
			if (createDateString !=null && createDateString !="") {
				return createDateString;
			}
			DateFormatUtil df= DateFormatUtil.getInstance("yyyy-MM-dd");
			if (this.createDate==null) {
				return createDateString;
			}
			return df.format(this.createDate);
		}
		public void setCreateDateString(String createDateString) {
			this.createDateString = createDateString;
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
	    @JsonSerialize(using = JsonDateTimeSerializer.class)
		public Date getCreateDate(){
			return createDate;
		}
	    @JsonDeserialize(using = JsonDateTimeDeserializer.class)
		public void setCreateDate(Date createDate){
			this.createDate = createDate;
		}
		public String getCreateUserId(){
			return createUserId;
		}
		public void setCreateUserId(String createUserId){
			this.createUserId = createUserId;
		}
	    @JsonSerialize(using = JsonDateTimeSerializer.class)
		public Date getUnitDate(){
			return unitDate;
		}
	    @JsonDeserialize(using = JsonDateTimeDeserializer.class)
		public void setUnitDate(Date unitDate){
			this.unitDate = unitDate;
		}
		public Long getUnitId(){
			return unitId;
		}
		public void setUnitId(Long unitId){
			this.unitId = unitId;
		}
		public String getUnitName(){
			return unitName;
		}
		public void setUnitName(String unitName){
			this.unitName = unitName;
		}
		public String getDocCode(){
			return docCode;
		}
		public void setDocCode(String docCode){
			this.docCode = docCode;
		}
		public String getRemark(){
			return remark;
		}
		public void setRemark(String remark){
			this.remark = remark;
		}
		public String getFileId(){
			return fileId;
		}
		public void setFileId(String fileId){
			this.fileId = fileId;
		}
		public String getStatus(){
			return status;
		}
		public void setStatus(String status){
			this.status = status;
		}
}