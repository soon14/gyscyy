package com.aptech.business.technical.eventAnalysis.domain;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.aptech.business.component.dictionary.EventNotificationStatusEnum;
import com.aptech.framework.json.JsonDateDeserializer;
import com.aptech.framework.json.JsonDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import org.apache.ibatis.type.Alias;

import com.aptech.framework.orm.BaseEntity;

/**
 * 
 * 事件分析实体类
 *
 * @author 
 * @created 2018-09-07 17:36:53
 * @lastModified 
 * @history
 *
 */
@Alias("EventAnalysisEntity")
public class EventAnalysisEntity extends BaseEntity{
		/**
		 * 主键
		 */
    	private Long id;
		/**
		 * 事件名称
		 */
    	private String name;
		/**
		 * 填报人
		 */
    	private String userId;
		/**
		 * 填报单位
		 */
    	private String unitId;
		/**
		 * 填报时间
		 */
    	private Date time;
		/**
		 * 状态
		 */
    	private String status;
		/**
		 * 附件id
		 */
    	private String fileId;
    	private String userName;
    	private String unitName;
    	private String statusName;
    	private int number;
    	private String spFlag;
    	private String approveIdea;
    	private String copyUserIds;
    	
    	
    	
    	
		public String getCopyUserIds() {
			return copyUserIds;
		}
		public void setCopyUserIds(String copyUserIds) {
			this.copyUserIds = copyUserIds;
		}
		public String getSpFlag() {
			return spFlag;
		}
		public void setSpFlag(String spFlag) {
			this.spFlag = spFlag;
		}
		public String getApproveIdea() {
			return approveIdea;
		}
		public void setApproveIdea(String approveIdea) {
			this.approveIdea = approveIdea;
		}
		public int getNumber() {
			return number;
		}
		public void setNumber(int number) {
			this.number = number;
		}
		public String getUserName() {
			return userName;
		}
		public void setUserName(String userName) {
			this.userName = userName;
		}
		public String getUnitName() {
			return unitName;
		}
		public void setUnitName(String unitName) {
			this.unitName = unitName;
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
		public String getUserId(){
			return userId;
		}
		public void setUserId(String userId){
			this.userId = userId;
		}
		public String getUnitId(){
			return unitId;
		}
		public void setUnitId(String unitId){
			this.unitId = unitId;
		}
	    @JsonSerialize(using = JsonDateSerializer.class)
		public Date getTime(){
			return time;
		}
	    @JsonDeserialize(using = JsonDateDeserializer.class)
		public void setTime(Date time){
			this.time = time;
		}
		public String getStatus(){
			return status;
		}
		public void setStatus(String status){
			this.status = status;
		}
		public String getFileId(){
			return fileId;
		}
		public void setFileId(String fileId){
			this.fileId = fileId;
		}
		public String getStatusName() {
			for (EventNotificationStatusEnum  worktypeenum: EventNotificationStatusEnum.values()) {
				if (worktypeenum.getCode().equals(String.valueOf(this.getStatus()))) {
					statusName =  worktypeenum.getName();
				}
			}
			return statusName;
		}
		public String getTimeString() {
    		if(time!=null){
    			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    			return  sdf.format(time);
    		}
    		return "";
    	}
}