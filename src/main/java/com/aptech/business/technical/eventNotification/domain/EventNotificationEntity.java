package com.aptech.business.technical.eventNotification.domain;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.aptech.business.component.dictionary.EventNotificationStatusEnum;
import com.aptech.business.component.dictionary.TechnicalStatusEnum;
import com.aptech.framework.json.JsonDateDeserializer;
import com.aptech.framework.json.JsonDateSerializer;
import com.aptech.framework.json.JsonDateTime1Deserializer;
import com.aptech.framework.json.JsonDateTime1Serializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import org.apache.ibatis.type.Alias;

import com.aptech.framework.orm.BaseEntity;

/**
 * 
 * 事件通报实体类
 *
 * @author 
 * @created 2018-07-30 11:42:47
 * @lastModified 
 * @history
 *
 */
@Alias("EventNotificationEntity")
public class EventNotificationEntity extends BaseEntity{
		/**
		 * 主键
		 */
    	private Long id;
		/**
		 * 单位id
		 */
    	private String unitId;
		/**
		 * 填报人id
		 */
    	private String userId;
		/**
		 * 填报时间
		 */
    	private Date fillTime;
		/**
		 * 事件名称
		 */
    	private String eventName;
		/**
		 * 附件id
		 */
    	private String fileId;
    	//单位名称
    	private String unitName;
    	//填报人名称
    	private String userName;
    	private int number;
    	private String status;
    	private String statusName;
    	private String spFlag;
    	private String approveIdea;
    	private String copyUserIds;
    	
    	
    	
    	
    	public String getCopyUserIds() {
			return copyUserIds;
		}
		public void setCopyUserIds(String copyUserIds) {
			this.copyUserIds = copyUserIds;
		}
		public String getApproveIdea() {
			return approveIdea;
		}
		public void setApproveIdea(String approveIdea) {
			this.approveIdea = approveIdea;
		}
		public String getSpFlag() {
			return spFlag;
		}
		public void setSpFlag(String spFlag) {
			this.spFlag = spFlag;
		}
		public String getStatusName() {
			for (EventNotificationStatusEnum  worktypeenum: EventNotificationStatusEnum.values()) {
				if (worktypeenum.getCode().equals(String.valueOf(this.getStatus()))) {
					statusName =  worktypeenum.getName();
				}
			}
			return statusName;
		}
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
		public int getNumber() {
			return number;
		}
		public void setNumber(int number) {
			this.number = number;
		}
		public String getUnitName() {
			return unitName;
		}
		public void setUnitName(String unitName) {
			this.unitName = unitName;
		}
		public String getUserName() {
			return userName;
		}
		public void setUserName(String userName) {
			this.userName = userName;
		}
		public Long getId(){
			return id;
		}
		public void setId(Long id){
			this.id = id;
		}
		public String getUnitId(){
			return unitId;
		}
		public void setUnitId(String unitId){
			this.unitId = unitId;
		}
		public String getUserId(){
			return userId;
		}
		public void setUserId(String userId){
			this.userId = userId;
		}
	    @JsonSerialize(using = JsonDateTime1Serializer.class)
		public Date getFillTime(){
			return fillTime;
		}
	    @JsonDeserialize(using = JsonDateTime1Deserializer.class)
		public void setFillTime(Date fillTime){
			this.fillTime = fillTime;
		}
		public String getEventName(){
			return eventName;
		}
		public void setEventName(String eventName){
			this.eventName = eventName;
		}
		public String getFileId(){
			return fileId;
		}
		public void setFileId(String fileId){
			this.fileId = fileId;
		}
		public String getTimeString() {
    		if(fillTime!=null){
    			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    			return  sdf.format(fillTime);
    		}
    		return "";
    	}
}