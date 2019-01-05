package com.aptech.business.managePlanContract.annualMaintenancePlan.domain;

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
 * 年度检修维护计划实体类
 *
 * @author 
 * @created 2018-04-18 15:31:10
 * @lastModified 
 * @history
 *
 */
@Alias("AnnualMaintenancePlanEntity")
public class AnnualMaintenancePlanEntity extends BaseEntity{
		/**
		 * 主键
		 */
    	private Long id;
		/**
		 * 项目名称
		 */
    	private String annualSubject;
		/**
		 * 项目内容
		 */
    	private String annualContent;
		/**
		 * 计划费用
		 */
    	private String annualCharge;
		/**
		 * 计划完成时间
		 */
    	private String planDate;
		/**
		 * 责任单位
		 */
    	private String unitId;
		/**
		 * 上传人
		 */
    	private String uploadPerson;
		/**
		 * 上传时间
		 */
    	private Date uploadTime;
		/**
		 * 审批状态
		 */
    	private String status;
		/**
		 * 备注
		 */
    	private String remark;
		/**
		 * 附件id
		 */
    	private String fileId;
    	private String unitName;
    	private String uploadPersonName;
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
		public String getUnitName() {
			return unitName;
		}
		public void setUnitName(String unitName) {
			this.unitName = unitName;
		}
		public String getUploadPersonName() {
			return uploadPersonName;
		}
		public void setUploadPersonName(String uploadPersonName) {
			this.uploadPersonName = uploadPersonName;
		}
		public Long getId(){
			return id;
		}
		public void setId(Long id){
			this.id = id;
		}
		public String getAnnualSubject(){
			return annualSubject;
		}
		public void setAnnualSubject(String annualSubject){
			this.annualSubject = annualSubject;
		}
		public String getAnnualContent(){
			return annualContent;
		}
		public void setAnnualContent(String annualContent){
			this.annualContent = annualContent;
		}
		public String getAnnualCharge(){
			return annualCharge;
		}
		public void setAnnualCharge(String annualCharge){
			this.annualCharge = annualCharge;
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
		public String getUploadPerson(){
			return uploadPerson;
		}
		public void setUploadPerson(String uploadPerson){
			this.uploadPerson = uploadPerson;
		}
	    @JsonSerialize(using = JsonDateTime1Serializer.class)
		public Date getUploadTime(){
			return uploadTime;
		}
	    @JsonDeserialize(using = JsonDateTime1Deserializer.class)
		public void setUploadTime(Date uploadTime){
			this.uploadTime = uploadTime;
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
		public String getFileId(){
			return fileId;
		}
		public void setFileId(String fileId){
			this.fileId = fileId;
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