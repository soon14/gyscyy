package com.aptech.business.safeManage.upperFile.domain;

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
 * 上级文件实体类
 *
 * @author 
 * @created 2018-03-28 10:18:07
 * @lastModified 
 * @history
 *
 */
@Alias("UpperFileEntity")
public class UpperFileEntity extends BaseEntity{
		/**
		 * 主键
		 */
    	private Long id;
		/**
		 * 上级文件名称
		 */
    	private String name;
		/**
		 * 精神名称
		 */
    	private String spiritName;
		/**
		 * 创建时间
		 */
    	private Date createDate;
		/**
		 * 创建人
		 */
    	private String createUserId;
		/**
		 * 单位Id
		 */
    	private Long unitId;
		/**
		 * 单位名称
		 */
    	private String unitName;
		/**
		 * 来文时间
		 */
    	private Date receiveTime;
		/**
		 * 年号 
		 */
    	private Date fileDate;
		/**
		 * 附件
		 */
    	private String fileId;
		/**
		 * 相关资料
		 */
    	private String docName;
		/**
		 * 状态
		 */
    	private String status;
		/**
		 * 类型
		 */
    	private Long type;
		/**
		 * 相关资料附件
		 */
    	private String docId;
		/**
		 * 精神文件附件
		 */
    	private String spiritDocId;
    	
    	
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
    	private String fileDateString;
    	/**
    	 * 建立时间
    	 */
    	private String receiveTimeString ;
    	private String userName;
    	
    	

		public String getUserName() {
			return userName;
		}
		public void setUserName(String userName) {
			this.userName = userName;
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
		public String getFileDateString() {
			if (fileDateString !=null && fileDateString !="") {
				return fileDateString;
			}
			DateFormatUtil df= DateFormatUtil.getInstance("yyyy");
			if (this.fileDate==null) {
				return fileDateString;
			}
			return df.format(this.fileDate);
			
		}
		public void setFileDateString(String fileDateString) {
			this.fileDateString = fileDateString;
		}
		public String getReceiveTimeString() {
			if (receiveTimeString !=null && receiveTimeString !="") {
				return receiveTimeString;
			}
			DateFormatUtil df= DateFormatUtil.getInstance("yyyy-MM-dd");
			if (this.receiveTime==null) {
				return receiveTimeString;
			}
			return df.format(this.receiveTime);
			
		}
		public void setReceiveTimeString(String receiveTimeString) {
			this.receiveTimeString = receiveTimeString;
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
		public String getSpiritName(){
			return spiritName;
		}
		public void setSpiritName(String spiritName){
			this.spiritName = spiritName;
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
	    @JsonSerialize(using = JsonDateTimeSerializer.class)
		public Date getReceiveTime(){
			return receiveTime;
		}
	    @JsonDeserialize(using = JsonDateTimeDeserializer.class)
		public void setReceiveTime(Date receiveTime){
			this.receiveTime = receiveTime;
		}
	    @JsonSerialize(using = JsonDateTimeSerializer.class)
		public Date getFileDate(){
			return fileDate;
		}
	    @JsonDeserialize(using = JsonDateTimeDeserializer.class)
		public void setFileDate(Date fileDate){
			this.fileDate = fileDate;
		}
		public String getFileId(){
			return fileId;
		}
		public void setFileId(String fileId){
			this.fileId = fileId;
		}
		public String getDocName(){
			return docName;
		}
		public void setDocName(String docName){
			this.docName = docName;
		}
		public String getStatus(){
			return status;
		}
		public void setStatus(String status){
			this.status = status;
		}
		public Long getType(){
			return type;
		}
		public void setType(Long type){
			this.type = type;
		}
		public String getDocId(){
			return docId;
		}
		public void setDocId(String docId){
			this.docId = docId;
		}
		public String getSpiritDocId(){
			return spiritDocId;
		}
		public void setSpiritDocId(String spiritDocId){
			this.spiritDocId = spiritDocId;
		}
}