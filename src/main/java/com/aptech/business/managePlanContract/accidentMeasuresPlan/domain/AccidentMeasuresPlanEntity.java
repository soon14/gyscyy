package com.aptech.business.managePlanContract.accidentMeasuresPlan.domain;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.ibatis.type.Alias;

import com.aptech.business.component.dictionary.ScienceTechnologyPlanStatusEnum;
import com.aptech.framework.json.JsonDateTime1Deserializer;
import com.aptech.framework.json.JsonDateTime1Serializer;
import com.aptech.framework.orm.BaseEntity;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * 
 * 年度反事故措施计划实体类
 *
 * @author 
 * @created 2018-04-16 15:12:09
 * @lastModified 
 * @history
 *
 */
@Alias("AccidentMeasuresPlanEntity")
public class AccidentMeasuresPlanEntity extends BaseEntity{
		/**
		 * 主键
		 */
    	private Long id;
		/**
		 * 项目名称
		 */
    	private String subject;
		/**
		 * 项目内容
		 */
    	private String content;
		/**
		 * 计划费用（万元）
		 */
    	private String charge;
		/**
		 * 计划完成时间
		 */
    	private String planDate;
		/**
		 * 责任单位
		 */
    	private String unitId;
		/**
		 * 上传时间
		 */
    	private Date uploadTime;
		/**
		 * 上传人
		 */
    	private String uploadPerson;
		/**
		 * 审核状态
		 */
    	private String status;
		/**
		 * 备注
		 */
    	private String remark;
    	private String fileId;
    	private String uploadPersonName;
    	private String unitName;
    	private int number;
    	/**
		 * 保存还是提交   
		 */
    	private String saveOrSubmit;
    	/**
		 * selectUser   审批人
		 */
    	private String selectUser;
    	/**
		 * 审批按钮的标示
		 */
    	private String  spFlag;
    	//（审批意见）
    	private String approveIdea;
    	
    	
    	
    	
    	
		public String getSaveOrSubmit() {
			return saveOrSubmit;
		}
		public void setSaveOrSubmit(String saveOrSubmit) {
			this.saveOrSubmit = saveOrSubmit;
		}
		public String getSelectUser() {
			return selectUser;
		}
		public void setSelectUser(String selectUser) {
			this.selectUser = selectUser;
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
		public String getUploadPersonName() {
			return uploadPersonName;
		}
		public void setUploadPersonName(String uploadPersonName) {
			this.uploadPersonName = uploadPersonName;
		}
		public String getUnitName() {
			return unitName;
		}
		public void setUnitName(String unitName) {
			this.unitName = unitName;
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
		public String getSubject(){
			return subject;
		}
		public void setSubject(String subject){
			this.subject = subject;
		}
		public String getContent(){
			return content;
		}
		public void setContent(String content){
			this.content = content;
		}
		public String getCharge(){
			return charge;
		}
		public void setCharge(String charge){
			this.charge = charge;
		}
		public String getPlanDate(){
			return planDate;
		}
		public void setPlanDate(String planDate){
			this.planDate = planDate;
		}
		public String getUnitId(){
			return unitId;
		}
		public void setUnitId(String unitId){
			this.unitId = unitId;
		}
	    @JsonSerialize(using = JsonDateTime1Serializer.class)
		public Date getUploadTime(){
			return uploadTime;
		}
	    @JsonDeserialize(using = JsonDateTime1Deserializer.class)
		public void setUploadTime(Date uploadTime){
			this.uploadTime = uploadTime;
		}
		public String getUploadPerson(){
			return uploadPerson;
		}
		public void setUploadPerson(String uploadPerson){
			this.uploadPerson = uploadPerson;
		}
		public String getStatus(){
			return status;
		}
		public void setStatus(String status){
			this.status = status;
		}
		public String getRemark(){
			return remark;
		}
		public void setRemark(String remark){
			this.remark = remark;
		}
		public String getUploadTimeString() {
    		if(uploadTime!=null){
    			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    			return  sdf.format(uploadTime);
    		}
    		return "";
    	}
		public String getStatusName(){
			for (ScienceTechnologyPlanStatusEnum scienceTechnologyPlanStatusEnum : ScienceTechnologyPlanStatusEnum.values()) {
				if (scienceTechnologyPlanStatusEnum.getCode().equals(this.status)) {
					return scienceTechnologyPlanStatusEnum.getName();
				}
			}
			return null;
		}
}