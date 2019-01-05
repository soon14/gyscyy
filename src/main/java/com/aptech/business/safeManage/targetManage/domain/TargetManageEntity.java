package com.aptech.business.safeManage.targetManage.domain;

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
 * 目标管理实体类
 *
 * @author 
 * @created 2018-03-23 14:32:07
 * @lastModified 
 * @history
 *
 */
@Alias("TargetManageEntity")
public class TargetManageEntity extends BaseEntity{
		/**
		 * 主键
		 */
    	private Long id;
		/**
		 * 书名称
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
		 * 修改时间
		 */
    	private Date updateDate;
		/**
		 * 创建人
		 */
    	private String createUserId;
		/**
		 * 修改人
		 */
    	private String updateUserId;
		/**
		 * 年号
		 */
    	private Date targetDate;
		/**
		 * 签订日期
		 */
    	private Date signDate;
		/**
		 * 单位Id
		 */
    	private Long signUnitId;
		/**
		 * 单位名称
		 */
    	private String signUnitName;
		/**
		 * 附件
		 */
    	private String fileId;
		/**
		 * 删除状态
		 */
    	private String status;
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
    	private String targetDateString;
    	/**
    	 * 签订时间
    	 */
    	private String signDateString;
    	
    	private String remark;
    	
    	private Long type;
    	
    	private String userName;
    	
    	

    	
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
		public String getRemark() {
			return remark;
		}
		public void setRemark(String remark) {
			this.remark = remark;
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
		public String getTargetDateString() {
			if (targetDateString !=null && targetDateString !="") {
				return targetDateString;
			}
			DateFormatUtil df= DateFormatUtil.getInstance("yyyy");
			if (this.targetDate==null) {
				return targetDateString;
			}
			return df.format(this.targetDate);
		}
		public void setTargetDateString(String targetDateString) {
			this.targetDateString = targetDateString;
		}
		public String getSignDateString() {
			if(signDateString !=null && signDateString !=""){
				return signDateString;
			}
			DateFormatUtil df= DateFormatUtil.getInstance("yyyy-MM-dd");
			if(this.signDate ==null){
				return signDateString;
			}
			return df.format(this.signDate);
		}
		public void setSignDateString(String signDateString) {
			this.signDateString = signDateString;
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
	    @JsonSerialize(using = JsonDateTimeSerializer.class)
		public Date getUpdateDate(){
			return updateDate;
		}
	    @JsonDeserialize(using = JsonDateTimeDeserializer.class)
		public void setUpdateDate(Date updateDate){
			this.updateDate = updateDate;
		}
		public String getCreateUserId(){
			return createUserId;
		}
		public void setCreateUserId(String createUserId){
			this.createUserId = createUserId;
		}
		public String getUpdateUserId(){
			return updateUserId;
		}
		public void setUpdateUserId(String updateUserId){
			this.updateUserId = updateUserId;
		}
	    @JsonSerialize(using = JsonDateTimeSerializer.class)
		public Date getTargetDate(){
			return targetDate;
		}
	    @JsonDeserialize(using = JsonDateTimeDeserializer.class)
		public void setTargetDate(Date targetDate){
			this.targetDate = targetDate;
		}
	    @JsonSerialize(using = JsonDateTimeSerializer.class)
		public Date getSignDate(){
			return signDate;
		}
	    @JsonDeserialize(using = JsonDateTimeDeserializer.class)
		public void setSignDate(Date signDate){
			this.signDate = signDate;
		}
		public Long getSignUnitId(){
			return signUnitId;
		}
		public void setSignUnitId(Long signUnitId){
			this.signUnitId = signUnitId;
		}
		public String getSignUnitName(){
			return signUnitName;
		}
		public void setSignUnitName(String signUnitName){
			this.signUnitName = signUnitName;
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