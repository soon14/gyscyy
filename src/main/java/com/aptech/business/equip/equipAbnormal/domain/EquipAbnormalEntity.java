package com.aptech.business.equip.equipAbnormal.domain;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.type.Alias;

import com.aptech.framework.json.JsonDateTimeDeserializer;
import com.aptech.framework.json.JsonDateTimeSerializer;
import com.aptech.framework.orm.BaseEntity;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * 
 * 设备异动实体类
 *
 * @author 
 * @created 2017-06-22 14:52:35
 * @lastModified 
 * @history
 *
 */
@Alias("EquipAbnormalEntity")
public class EquipAbnormalEntity extends BaseEntity{
		/**
		 * 设备ID
		 */
		private String equipmentId;
		/**
		 * 设备CODE
		 */
		private String equipmentCode;
    	/**
    	 * 设备CODE
    	 */
    	private String equipmentName;
		/**
		 * 异动单号
		 */
    	private String equipAbnormalBill;
		/**
		 * 单位
		 */
    	private String unitId;
    	/**
		 * 单位
		 */
    	private String unitName;
    	/**
    	 * 设备名称
    	 */
    	private String equipCode;
		/**
		 * 设备名称
		 */
    	private String equipName;
		/**
		 * 计划开始时间
		 */
    	private Date planBeginDate;
		/**
		 * 计划结束时间
		 */
    	private Date planEndDate;
    	/**
    	 * 申请人ID
    	 */
    	private Long applyUserId;
		/**
		 * 申请人姓名
		 */
    	private String applyUserName;
		/**
		 * 申请时间
		 */
    	private Date applyDate;
		/**
		 * 异动说明
		 */
    	private String abnormalDesc;
		/**
		 * 异动原因
		 */
    	private String abnormalCause;
		/**
		 * 备注
		 */
    	private String remark;
		/**
		 * 是否删除
		 */
    	private Integer status;
    	/**
    	 * 执行人
    	 */
    	private String executeUserId;
		/**
		 * 执行人登陆名
		 */
    	private String executeUserName;
		/**
		 * 执行开始时间
		 */
    	private Date startDate;
		/**
		 * 执行结束时间
		 */
    	private Date endDate;
		/**
		 * 完成情况
		 */
    	private String finishInfo;
		/**
		 * 缺陷
		 */
    	private Long defectId;
		/**
		 * 附件
		 */
    	private String fileId;
    	/**
    	 * 执行人上传附件
    	 */
    	private String executeFileId;
		//		/**
//    	 * 附件名称
//    	 */
//    	private String[] fileName;
//    	/**
//		 * 附件URL
//		 */
//    	private String[] fileUrl;
    	/**
    	 * 流程状态
    	 */
    	private String processStatus;
		/**
    	 * 流程状态(审批状态)
    	 */
    	private String processStatusName;
    	/**
    	 * 审批意见
    	 */
    	private String approveIdea;
		private String taskId;

     	private String procInstId;
     	
     	private String userList;
     	public String getEquipmentName() {
			return equipmentName;
		}
		public void setEquipmentName(String equipmentName) {
			this.equipmentName = equipmentName;
		}
     	public String getEquipCode() {
			return equipCode;
		}
		public void setEquipCode(String equipCode) {
			this.equipCode = equipCode;
		}
     	public String getEquipmentId() {
			return equipmentId;
		}
		public void setEquipmentId(String equipmentId) {
			this.equipmentId = equipmentId;
		}
		public String getEquipmentCode() {
			return equipmentCode;
		}
		public void setEquipmentCode(String equipmentCode) {
			this.equipmentCode = equipmentCode;
		}
    	public String getExecuteUserId() {
			return executeUserId;
		}
		public void setExecuteUserId(String executeUserId) {
			this.executeUserId = executeUserId;
		}
    	public String getExecuteFileId() {
			return executeFileId;
		}
		public void setExecuteFileId(String executeFileId) {
			this.executeFileId = executeFileId;
		}
		public String getExecuteUserName() {
			return executeUserName;
		}
		public void setExecuteUserName(String executeUserName) {
			this.executeUserName = executeUserName;
		}
    	public String getTaskId() {
			return taskId;
		}
		public void setTaskId(String taskId) {
			this.taskId = taskId;
		}
		public String getProcInstId() {
			return procInstId;
		}
		public void setProcInstId(String procInstId) {
			this.procInstId = procInstId;
		}
		public String getUserList() {
			return userList;
		}
		public void setUserList(String userList) {
			this.userList = userList;
		}
    	public String getApproveIdea() {
			return approveIdea;
		}
		public void setApproveIdea(String approveIdea) {
			this.approveIdea = approveIdea;
		}
		public String getProcessStatus() {
			return processStatus;
		}
		public void setProcessStatus(String processStatus) {
			this.processStatus = processStatus;
		}
    	public String getApplyUserName() {
			return applyUserName;
		}
		public void setApplyUserName(String applyUserName) {
			this.applyUserName = applyUserName;
		}
    	public String getUnitName() {
			return unitName;
		}
		public void setUnitName(String unitName) {
			this.unitName = unitName;
		}
		public Long getApplyUserId() {
			return applyUserId;
		}
		public void setApplyUserId(Long applyUserId) {
			this.applyUserId = applyUserId;
		}
		public String getProcessStatusName() {
			for (EquipAbnormalEnum equipAbnormalEnum : EquipAbnormalEnum.values()) {
    			if (StringUtils.equals(equipAbnormalEnum.getCode(),this.getProcessStatus())) {
    				return equipAbnormalEnum.getName();
    			}
    		}
			return "";
		}
		public void setProcessStatusName(String processStatusName) {
			this.processStatusName = processStatusName;
		}
    	public String getFileId() {
			return fileId;
		}
		public void setFileId(String fileId) {
			this.fileId = fileId;
		}
    	public String getAbnormalDesc() {
			return abnormalDesc;
		}
		public void setAbnormalDesc(String abnormalDesc) {
			this.abnormalDesc = abnormalDesc;
		}
		public String getEquipAbnormalBill(){
			return equipAbnormalBill;
		}
		public void setEquipAbnormalBill(String equipAbnormalBill){
			this.equipAbnormalBill = equipAbnormalBill;
		}
		public String getUnitId(){
			return unitId;
		}
		public void setUnitId(String unitId){
			this.unitId = unitId;
		}
		public String getEquipName(){
			return equipName;
		}
		public void setEquipName(String equipName){
			this.equipName = equipName;
		}
	    @JsonSerialize(using = JsonDateTimeSerializer.class)
		public Date getPlanBeginDate(){
			return planBeginDate;
		}
	    @JsonDeserialize(using = JsonDateTimeDeserializer.class)
		public void setPlanBeginDate(Date planBeginDate){
			this.planBeginDate = planBeginDate;
		}
	    @JsonSerialize(using = JsonDateTimeSerializer.class)
		public Date getPlanEndDate(){
			return planEndDate;
		}
	    @JsonDeserialize(using = JsonDateTimeDeserializer.class)
		public void setPlanEndDate(Date planEndDate){
			this.planEndDate = planEndDate;
		}
	    @JsonSerialize(using = JsonDateTimeSerializer.class)
		public Date getApplyDate(){
			return applyDate;
		}
	    @JsonDeserialize(using = JsonDateTimeDeserializer.class)
		public void setApplyDate(Date applyDate){
			this.applyDate = applyDate;
		}
		public String getAbnormalCause(){
			return abnormalCause;
		}
		public void setAbnormalCause(String abnormalCause){
			this.abnormalCause = abnormalCause;
		}
		public String getRemark(){
			return remark;
		}
		public void setRemark(String remark){
			this.remark = remark;
		}
		public Integer getStatus(){
			return status;
		}
		public void setStatus(Integer status){
			this.status = status;
		}
	    @JsonSerialize(using = JsonDateTimeSerializer.class)
		public Date getStartDate(){
			return startDate;
		}
	    @JsonDeserialize(using = JsonDateTimeDeserializer.class)
		public void setStartDate(Date startDate){
			this.startDate = startDate;
		}
	    @JsonSerialize(using = JsonDateTimeSerializer.class)
		public Date getEndDate(){
			return endDate;
		}
	    @JsonDeserialize(using = JsonDateTimeDeserializer.class)
		public void setEndDate(Date endDate){
			this.endDate = endDate;
		}
		public String getFinishInfo(){
			return finishInfo;
		}
		public void setFinishInfo(String finishInfo){
			this.finishInfo = finishInfo;
		}
		public Long getDefectId(){
			return defectId;
		}
		public void setDefectId(Long defectId){
			this.defectId = defectId;
		}
}