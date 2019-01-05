package com.aptech.business.technical.testReport.domain;

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
 * 试验报告实体类
 *
 * @author 
 * @created 2018-09-05 14:35:08
 * @lastModified 
 * @history
 *
 */
@Alias("TestReportEntity")
public class TestReportEntity extends BaseEntity{
		/**
		 * 主键
		 */
    	private Long id;
		/**
		 * 项目名称
		 */
    	private String name;
		/**
		 * 试验人员
		 */
    	private String testMember;
		/**
		 * 填报人
		 */
    	private String userId;
		/**
		 * 填报时间
		 */
    	private Date time;
		/**
		 * 填报单位
		 */
    	private String unitId;
		/**
		 * 附件id
		 */
    	private String fileId;
		/**
		 * 状态
		 */
    	private String status;
    	private String statusName;
    	private String userName;
    	private String unitName;
    	private String spFlag;
    	private String approveIdea;
    	private int number;
    	
    	
    	
    	
    	
    	
    	public int getNumber() {
			return number;
		}
		public void setNumber(int number) {
			this.number = number;
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
		public String getStatusName() {
			for (TechnicalStatusEnum  worktypeenum: TechnicalStatusEnum.values()) {
				if (worktypeenum.getCode().equals(String.valueOf(this.getStatus()))) {
					statusName =  worktypeenum.getName();
				}
			}
			return statusName;
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
		public String getTestMember(){
			return testMember;
		}
		public void setTestMember(String testMember){
			this.testMember = testMember;
		}
		public String getUserId(){
			return userId;
		}
		public void setUserId(String userId){
			this.userId = userId;
		}
	    @JsonSerialize(using = JsonDateSerializer.class)
		public Date getTime(){
			return time;
		}
	    @JsonDeserialize(using = JsonDateDeserializer.class)
		public void setTime(Date time){
			this.time = time;
		}
		public String getUnitId(){
			return unitId;
		}
		public void setUnitId(String unitId){
			this.unitId = unitId;
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
		public String getTimeString() {
    		if(time!=null){
    			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    			return  sdf.format(time);
    		}
    		return "";
    	}
}