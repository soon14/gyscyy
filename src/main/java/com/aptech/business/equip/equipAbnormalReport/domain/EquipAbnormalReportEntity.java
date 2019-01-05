package com.aptech.business.equip.equipAbnormalReport.domain;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.type.Alias;

import com.aptech.framework.json.JsonDateDeserializer;
import com.aptech.framework.json.JsonDateSerializer;
import com.aptech.framework.json.JsonDateTimeDeserializer;
import com.aptech.framework.json.JsonDateTimeSerializer;
import com.aptech.framework.orm.BaseEntity;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * 
 * 设备异动报告实体类
 *
 * @author 
 * @created 2018-09-14 13:48:29
 * @lastModified 
 * @history
 *
 */
@Alias("EquipAbnormalReportEntity")
public class EquipAbnormalReportEntity extends BaseEntity{
		/**
		 * 主键
		 */
    	private Long id;
		/**
		 * 编码
		 */
    	private String code;
		/**
		 * 名称
		 */
    	private String name;
		/**
		 * 创建人
		 */
    	private Long createUserId;
		/**
		 * 创建时间
		 */
    	private Date createDate;
		/**
		 * 修改人
		 */
    	private Long updateUserId;
		/**
		 * 修改时间
		 */
    	private Date updateDate;
		/**
		 * 报告编码
		 */
    	private String reportCode;
		/**
		 * 报告名称
		 */
    	private String reportName;
		/**
		 * 设备编码
		 */
    	private String equipCode;
		/**
		 * 设备名称
		 */
    	private String equipName;
    	/**
    	 * 提交时间
    	 */
    	private Date submitDate;
    	/**
    	 * 提交人ID
    	 */
    	private int submitPersionId;
		/**
		 * 执行情况说明
		 */
    	private String excuteInfoExplain;
		/**
		 * 备注
		 */
    	private String remark;
    	/**
    	 * 附件
    	 */
    	private String fileId;
		/**
		 * 流程状态
		 */
    	private String processStatus;
    	/**
    	 * 流程状态(审批状态)
    	 */
    	private String processStatusName;
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
    	//设备所属
    	private String equipBelong;
    	public String getEquipBelong() {
			return equipBelong;
		}
		public void setEquipBelong(String equipBelong) {
			this.equipBelong = equipBelong;
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
    	/**
    	 * 执行人上传附件
    	 */
    	private String executeFileId;
    	public String getExecuteFileId() {
			return executeFileId;
		}
		public void setExecuteFileId(String executeFileId) {
			this.executeFileId = executeFileId;
		}
    	public String getExecuteUserId() {
			return executeUserId;
		}
		public void setExecuteUserId(String executeUserId) {
			this.executeUserId = executeUserId;
		}
		public String getExecuteUserName() {
			return executeUserName;
		}
		public void setExecuteUserName(String executeUserName) {
			this.executeUserName = executeUserName;
		}
    	public String getSubmitPersionName() {
			return submitPersionName;
		}
		public void setSubmitPersionName(String submitPersionName) {
			this.submitPersionName = submitPersionName;
		}
		/**
    	 * 提交人姓名
    	 */
    	private String submitPersionName;
		/**
    	 * 状态
    	 */
    	private int status;
    	/**
    	 * 人员列表
    	 */
    	private String userList;
    	/**
    	 * 审批意见
    	 */
    	private String approveIdea;
		public String getApproveIdea() {
			return approveIdea;
		}
		public void setApproveIdea(String approveIdea) {
			this.approveIdea = approveIdea;
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
		private String taskId;
     	private String procInstId;
    	public String getUserList() {
			return userList;
		}
		public void setUserList(String userList) {
			this.userList = userList;
		}
		public int getSubmitPersionId() {
			return submitPersionId;
		}
		public void setSubmitPersionId(int submitPersionId) {
			this.submitPersionId = submitPersionId;
		}
		public int getStatus() {
			return status;
		}
		public void setStatus(int status) {
			this.status = status;
		}
		public String getProcessStatusName() {
			for (EquipAbnormalReportEnum equipAbnormalReportEnum : EquipAbnormalReportEnum.values()) {
    			if (StringUtils.equals(equipAbnormalReportEnum.getCode(),this.getProcessStatus())) {
    				return equipAbnormalReportEnum.getName();
    			}
    		}
			return "";
		}
		public void setProcessStatusName(String processStatusName) {
			this.processStatusName = processStatusName;
		}
    	public String getProcessStatus() {
			return processStatus;
		}
		public void setProcessStatus(String processStatus) {
			this.processStatus = processStatus;
		}
		public Long getUpdateUserId() {
			return updateUserId;
		}
		public void setUpdateUserId(Long updateUserId) {
			this.updateUserId = updateUserId;
		}
    	public Long getCreateUserId() {
			return createUserId;
		}
		public void setCreateUserId(Long createUserId) {
			this.createUserId = createUserId;
		}
		public Long getId(){
			return id;
		}
		public void setId(Long id){
			this.id = id;
		}
		public String getCode(){
			return code;
		}
		public void setCode(String code){
			this.code = code;
		}
		public String getName(){
			return name;
		}
		public void setName(String name){
			this.name = name;
		}
	    @JsonSerialize(using = JsonDateSerializer.class)
		public Date getCreateDate(){
			return createDate;
		}
	    @JsonDeserialize(using = JsonDateDeserializer.class)
		public void setCreateDate(Date createDate){
			this.createDate = createDate;
		}
	    @JsonSerialize(using = JsonDateSerializer.class)
		public Date getUpdateDate(){
			return updateDate;
		}
	    @JsonDeserialize(using = JsonDateDeserializer.class)
		public void setUpdateDate(Date updateDate){
			this.updateDate = updateDate;
		}
		public String getReportCode(){
			return reportCode;
		}
		public void setReportCode(String reportCode){
			this.reportCode = reportCode;
		}
		public String getReportName(){
			return reportName;
		}
		public void setReportName(String reportName){
			this.reportName = reportName;
		}
		public String getEquipCode(){
			return equipCode;
		}
		public void setEquipCode(String equipCode){
			this.equipCode = equipCode;
		}
		public String getEquipName(){
			return equipName;
		}
		public void setEquipName(String equipName){
			this.equipName = equipName;
		}
	    @JsonSerialize(using = JsonDateSerializer.class)
		public Date getSubmitDate(){
			return submitDate;
		}
	    @JsonDeserialize(using = JsonDateDeserializer.class)
		public void setSubmitDate(Date submitDate){
			this.submitDate = submitDate;
		}
		public String getExcuteInfoExplain(){
			return excuteInfoExplain;
		}
		public void setExcuteInfoExplain(String excuteInfoExplain){
			this.excuteInfoExplain = excuteInfoExplain;
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
}