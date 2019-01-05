package com.aptech.business.overhaul.overhaulPlan.domain;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.type.Alias;

import com.aptech.business.component.dictionary.OverhaulPlanStatusEnum;
import com.aptech.business.component.dictionary.PowerStatusEnum;
import com.aptech.business.overhaul.overhaulProject.domain.OverhaulProjectEntity;
import com.aptech.framework.json.JsonDateTimeDeserializer;
import com.aptech.framework.json.JsonDateTimeSerializer;
import com.aptech.framework.orm.BaseEntity;
import com.aptech.framework.util.DateFormatUtil;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * 
 * 检修计划实体类
 *
 * @author 
 * @created 2017-06-09 10:42:58
 * @lastModified 
 * @history
 *
 */
@Alias("overhaulPlanEntity")
public class OverhaulPlanEntity extends BaseEntity{
		/**
		 * 状态
		 */
    	private String status;
		/**
		 * 备注
		 */
    	private String remark;
		/**
		 * 附件
		 */
    	private String attachementIds;
		/**
		 * 修改时间
		 */
    	private Date updateDate;
		/**
		 * 主键
		 */
    	private Long id;
		/**
		 * 检修负责人id
		 */
    	private Long dutyUserId;
		/**
		 * 审批状态
		 */
    	private String approveStatus;
		/**
		 * 计划年份
		 */
    	private Date planYear;
		/**
		 * 部门名称
		 */
    	private String unitName;
		/**
		 * 检修负责人姓名
		 */
    	private String dutyUserName;
		/**
		 * 计划编号
		 */
    	private String planNumber;
		/**
		 * 创建时间
		 */
    	private Date createDate;
		/**
		 * 部门id
		 */
    	private String unitId;
		/**
		 * 创建人
		 */
    	private String createUserId;
    	
    	private String approveStatusString;

    	private String planYearString;
    	
    	private List<OverhaulProjectEntity> list;
    	private String userList;
    	private String taskId;
    	private String auditMsg;

    	private String procInstId;
    	
    	private String start;
    	private String end;
    	
    	private List<Long> delIds;
    	private String auditBtn;
    	
    	private String overhaulPlanId;//停送电管理使用
    	
    	private String updateProject;//修改项目
    	
    	private String planName;//检修计划名称
    	private Long planNameId;//检修计划名称
  
    	
    	private Long accidentId;//反事故措施Id
    	
    	private Long maintenanceId;//检修维护计划ID
    	
    	private Long technicalId;// 技改ID
    	
    	/**
    	 * 发文标题
    	 */
    	private String dispatchTitle;
    	
    	/**
    	 * 发文字号
    	 */
    	private String dispatchNumber;
    	
    	private Long dispatchId;
    	
    	/**
    	 * 生技部检修专工审核标识
    	 */
    	private String overhaulWorkerReviewFlag;
    	
    	/**
    	 *  生技部检修专工审核人
    	 */
    	private String overhaulWorkerReviewer;
    	/**
    	 *  选择的生技部检修专工审核人
    	 */
    	private String overhaulWorkerSelected;
    	
    	/**
    	 * 集控中心审核标识
    	 */
    	private String centralizedReviewFlag;
    	
    	/**
    	 *  集控中心审核人
    	 */
    	private String centralizedReviewer;
    	/**
    	 *  选择的集控中心审核人
    	 */
    	private String centralizedSelected;
    	
    	/**
    	 * 安监处审核标识
    	 */
    	private String safetyReviewFlag;
    	
    	/**
    	 *  安监处审核人
    	 */
    	private String safetyReviewer;
    	/**
    	 *  选择的安监处审核人
    	 */
    	private String safetySelected;
    	
    
		public String getDispatchTitle() {
			return dispatchTitle;
		}
		public void setDispatchTitle(String dispatchTitle) {
			this.dispatchTitle = dispatchTitle;
		}
		public String getDispatchNumber() {
			return dispatchNumber;
		}
		public void setDispatchNumber(String dispatchNumber) {
			this.dispatchNumber = dispatchNumber;
		}
		public Long getDispatchId() {
			return dispatchId;
		}
		public void setDispatchId(Long dispatchId) {
			this.dispatchId = dispatchId;
		}
		public String getOverhaulPlanId() {
			return overhaulPlanId;
		}
		public void setOverhaulPlanId(String overhaulPlanId) {
			this.overhaulPlanId = overhaulPlanId;
		}
		public Long getPlanNameId() {
			return planNameId;
		}
		public void setPlanNameId(Long planNameId) {
			this.planNameId = planNameId;
		}
		public Long getAccidentId() {
			return accidentId;
		}
		public void setAccidentId(Long accidentId) {
			this.accidentId = accidentId;
		}
		public Long getMaintenanceId() {
			return maintenanceId;
		}
		public void setMaintenanceId(Long maintenanceId) {
			this.maintenanceId = maintenanceId;
		}
		public Long getTechnicalId() {
			return technicalId;
		}
		public void setTechnicalId(Long technicalId) {
			this.technicalId = technicalId;
		}
		public String getPlanName() {
			return planName;
		}
		public void setPlanName(String planName) {
			this.planName = planName;
		}
		public String getUpdateProject() {
			return updateProject;
		}
		public void setUpdateProject(String updateProject) {
			this.updateProject = updateProject;
		}
		public String getStart() {
			return start;
		}
		public void setStart(String start) {
			this.start = start;
		}
		public String getEnd() {
			return end;
		}
		public void setEnd(String end) {
			this.end = end;
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
		public String getAttachementIds(){
			return attachementIds;
		}
		public void setAttachementIds(String attachementIds){
			this.attachementIds = attachementIds;
		}
	    @JsonSerialize(using = JsonDateTimeSerializer.class)
		public Date getUpdateDate(){
			return updateDate;
		}
	    @JsonDeserialize(using = JsonDateTimeDeserializer.class)
		public void setUpdateDate(Date updateDate){
			this.updateDate = updateDate;
		}
		public Long getId(){
			return id;
		}
		public void setId(Long id){
			this.id = id;
		}
		public Long getDutyUserId(){
			return dutyUserId;
		}
		public void setDutyUserId(Long dutyUserId){
			this.dutyUserId = dutyUserId;
		}
		public String getApproveStatus(){
			return approveStatus;
		}
		public void setApproveStatus(String approveStatus){
			this.approveStatus = approveStatus;
		}
	    @JsonSerialize(using = JsonDateTimeSerializer.class)
		public Date getPlanYear(){
			return planYear;
		}
	    @JsonDeserialize(using = JsonDateTimeDeserializer.class)
		public void setPlanYear(Date planYear){
			this.planYear = planYear;
		}
		public String getUnitName(){
			return unitName;
		}
		public void setUnitName(String unitName){
			this.unitName = unitName;
		}
		public String getDutyUserName(){
			return dutyUserName;
		}
		public void setDutyUserName(String dutyUserName){
			this.dutyUserName = dutyUserName;
		}
		public String getPlanNumber(){
			return planNumber;
		}
		public void setPlanNumber(String planNumber){
			this.planNumber = planNumber;
		}
	    @JsonSerialize(using = JsonDateTimeSerializer.class)
		public Date getCreateDate(){
			return createDate;
		}
	    @JsonDeserialize(using = JsonDateTimeDeserializer.class)
		public void setCreateDate(Date createDate){
			this.createDate = createDate;
		}
		public String getUnitId(){
			return unitId;
		}
		public void setUnitId(String unitId){
			this.unitId = unitId;
		}
		public String getCreateUserId(){
			return createUserId;
		}
		public void setCreateUserId(String createUserId){
			this.createUserId = createUserId;
		}
		public String getApproveStatusString() {
			for (OverhaulPlanStatusEnum overhaulPlanStatusEnum : OverhaulPlanStatusEnum.values()) {
				if (overhaulPlanStatusEnum.getCode().equals(this.approveStatus)) {
					return overhaulPlanStatusEnum.getName();
				}
			}
			return this.approveStatusString;
		}
		public void setApproveStatusString(String approveStatusString) {
			this.approveStatusString = approveStatusString;
		}
		public String getPlanYearString() {
			DateFormatUtil df= DateFormatUtil.getInstance("yyyy-MM-dd");
			if(this.planYear ==null){
				return planYearString;
			}
			return df.format(this.planYear);
		}
		public void setPlanYearString(String planYearString) {
			this.planYearString = planYearString;
		}
		public List<OverhaulProjectEntity> getList() {
			return list;
		}
		public void setList(List<OverhaulProjectEntity> list) {
			this.list = list;
		}
		public String getUserList() {
			return userList;
		}
		public void setUserList(String userList) {
			this.userList = userList;
		}
		public String getTaskId() {
			return taskId;
		}
		public void setTaskId(String taskId) {
			this.taskId = taskId;
		}
		public String getAuditMsg() {
			return auditMsg;
		}
		public void setAuditMsg(String auditMsg) {
			this.auditMsg = auditMsg;
		}
		public String getProcInstId() {
			return procInstId;
		}
		public void setProcInstId(String procInstId) {
			this.procInstId = procInstId;
		}
		public List<Long> getDelIds() {
			return delIds;
		}
		public void setDelIds(List<Long> delIds) {
			this.delIds = delIds;
		}
		public String getAuditBtn() {
			return auditBtn;
		}
		public void setAuditBtn(String auditBtn) {
			this.auditBtn = auditBtn;
		}
		public String getOverhaulWorkerReviewFlag() {
			return overhaulWorkerReviewFlag;
		}
		public void setOverhaulWorkerReviewFlag(String overhaulWorkerReviewFlag) {
			this.overhaulWorkerReviewFlag = overhaulWorkerReviewFlag;
		}
		public String getOverhaulWorkerReviewer() {
			return overhaulWorkerReviewer;
		}
		public void setOverhaulWorkerReviewer(String overhaulWorkerReviewer) {
			this.overhaulWorkerReviewer = overhaulWorkerReviewer;
		}
		public String getOverhaulWorkerSelected() {
			return overhaulWorkerSelected;
		}
		public void setOverhaulWorkerSelected(String overhaulWorkerSelected) {
			this.overhaulWorkerSelected = overhaulWorkerSelected;
		}
		public String getCentralizedReviewFlag() {
			return centralizedReviewFlag;
		}
		public void setCentralizedReviewFlag(String centralizedReviewFlag) {
			this.centralizedReviewFlag = centralizedReviewFlag;
		}
		public String getCentralizedReviewer() {
			return centralizedReviewer;
		}
		public void setCentralizedReviewer(String centralizedReviewer) {
			this.centralizedReviewer = centralizedReviewer;
		}
		public String getCentralizedSelected() {
			return centralizedSelected;
		}
		public void setCentralizedSelected(String centralizedSelected) {
			this.centralizedSelected = centralizedSelected;
		}
		public String getSafetyReviewFlag() {
			return safetyReviewFlag;
		}
		public void setSafetyReviewFlag(String safetyReviewFlag) {
			this.safetyReviewFlag = safetyReviewFlag;
		}
		public String getSafetyReviewer() {
			return safetyReviewer;
		}
		public void setSafetyReviewer(String safetyReviewer) {
			this.safetyReviewer = safetyReviewer;
		}
		public String getSafetySelected() {
			return safetySelected;
		}
		public void setSafetySelected(String safetySelected) {
			this.safetySelected = safetySelected;
		}
		
		
}