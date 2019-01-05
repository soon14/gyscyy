package com.aptech.business.managePlanContract.investmentPlan.domain;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.type.Alias;

import com.aptech.business.component.dictionary.EducationCategoryTypeEnum;
import com.aptech.business.component.dictionary.InvestmentPlanCategoryTypeEnum;
import com.aptech.business.component.dictionary.InvestmentPlanStageTypeEnum;
import com.aptech.business.component.dictionary.InvestmentPlanStatusEnum;
import com.aptech.business.component.dictionary.YearPurchaseStatusEnum;
import com.aptech.framework.json.JsonDateTimeDeserializer;
import com.aptech.framework.json.JsonDateTimeSerializer;
import com.aptech.framework.orm.BaseEntity;
import com.aptech.framework.util.DateFormatUtil;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * 
 * 投资计划实体类
 *
 * @author 
 * @created 2018-04-08 13:31:35
 * @lastModified 
 * @history
 *
 */
@Alias("InvestmentPlanEntity")
public class InvestmentPlanEntity extends BaseEntity{
		/**
		 * 主键
		 */
    	private Long id;
		/**
		 * 名称
		 */
    	private String name;
		/**
		 * 创建人
		 */
    	private String createUserId;
		/**
		 * 创建时间
		 */
    	private Date createDate;
		/**
		 * 项目类别
		 */
    	private String category;
		/**
		 * 年份
		 */
    	private Date yearNum;
		/**
		 * 股权结构
		 */
    	private String stockStruct;
		/**
		 * 建设规模
		 */
    	private String buildSize;
		/**
		 * 建设规模量纲
		 */
    	private String buildSizeUnit;
		/**
		 * 建设阶段
		 */
    	private String buildStage;
		/**
		 * 年度完成工程节点内容
		 */
    	private String content;
		/**
		 * 小计
		 */
    	private String totalMoney;
		/**
		 * 自有金额
		 */
    	private String ownMoney;
		/**
		 * 贷款
		 */
    	private String loanMoney;
		/**
		 * 其他
		 */
    	private String other;
		/**
		 * 备注
		 */
    	private String remark;
		/**
		 * 附件
		 */
    	private String fileId;
    	
    	/**
    	 * 附件名称
    	 */
		private String[] fileNames;
		/**
		 * 附件URL
		 */
		private String[] fileUrl;
		
		private String yearNumString;
		
		private String userName;
		
	   	private String auditMsg;
    	private String auditBtn;
    	private String taskId;
    	private String procInstId;
		
    	private String userList;
		/**
		 * 审批状态
		 */
    	private String approveStatus;
		
    	

		public String getUserList() {
			return userList;
		}
		public void setUserList(String userList) {
			this.userList = userList;
		}
		public String getProcInstId() {
			return procInstId;
		}
		public void setProcInstId(String procInstId) {
			this.procInstId = procInstId;
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
		public String getAuditBtn() {
			return auditBtn;
		}
		public void setAuditBtn(String auditBtn) {
			this.auditBtn = auditBtn;
		}
		public String getApproveStatus() {
			return approveStatus;
		}
		public void setApproveStatus(String approveStatus) {
			this.approveStatus = approveStatus;
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
		public String getName(){
			return name;
		}
		public void setName(String name){
			this.name = name;
		}
		public String getCreateUserId(){
			return createUserId;
		}
		public void setCreateUserId(String createUserId){
			this.createUserId = createUserId;
		}
	    @JsonSerialize(using = JsonDateTimeSerializer.class)
		public Date getCreateDate(){
			return createDate;
		}
	    @JsonDeserialize(using = JsonDateTimeDeserializer.class)
		public void setCreateDate(Date createDate){
			this.createDate = createDate;
		}
		public String getCategory(){
			return category;
		}
		public void setCategory(String category){
			this.category = category;
		}
	    @JsonSerialize(using = JsonDateTimeSerializer.class)
		public Date getYearNum(){
			return yearNum;
		}
	    @JsonDeserialize(using = JsonDateTimeDeserializer.class)
		public void setYearNum(Date yearNum){
			this.yearNum = yearNum;
		}
		public String getStockStruct(){
			return stockStruct;
		}
		public void setStockStruct(String stockStruct){
			this.stockStruct = stockStruct;
		}
		public String getBuildSize(){
			return buildSize;
		}
		public void setBuildSize(String buildSize){
			this.buildSize = buildSize;
		}
		public String getBuildSizeUnit(){
			return buildSizeUnit;
		}
		public void setBuildSizeUnit(String buildSizeUnit){
			this.buildSizeUnit = buildSizeUnit;
		}
		public String getBuildStage(){
			return buildStage;
		}
		public void setBuildStage(String buildStage){
			this.buildStage = buildStage;
		}
		public String getContent(){
			return content;
		}
		public void setContent(String content){
			this.content = content;
		}
		
		public String getTotalMoney() {
			return totalMoney;
		}
		public void setTotalMoney(String totalMoney) {
			this.totalMoney = totalMoney;
		}
		public String getOwnMoney() {
			return ownMoney;
		}
		public void setOwnMoney(String ownMoney) {
			this.ownMoney = ownMoney;
		}
		public String getLoanMoney() {
			return loanMoney;
		}
		public void setLoanMoney(String loanMoney) {
			this.loanMoney = loanMoney;
		}
		public String getOther(){
			return other;
		}
		public void setOther(String other){
			this.other = other;
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
		public String[] getFileNames() {
			return fileNames;
		}
		public void setFileNames(String[] fileNames) {
			this.fileNames = fileNames;
		}
		public String[] getFileUrl() {
			return fileUrl;
		}
		public void setFileUrl(String[] fileUrl) {
			this.fileUrl = fileUrl;
		}
		public String getYearNumString() {
			if (yearNumString !=null && yearNumString !="") {
				return yearNumString;
			}
			DateFormatUtil df= DateFormatUtil.getInstance("yyyy");
			if (this.yearNum==null) {
				return yearNumString;
			}
			return df.format(this.yearNum);
		}
		public void setYearNumString(String yearNumString) {
			this.yearNumString = yearNumString;
		}
		
		
		/**
		 * 状态名字
		 */
		public String getApproveStatusName() {
			for (InvestmentPlanStatusEnum workStatusEnum : InvestmentPlanStatusEnum.values()) {
				if (workStatusEnum.getCode().equals(this.approveStatus.toString())) {
					return workStatusEnum.getName();
				}
			}
			return null;
		}
		
		
		public String getCategoryName(){
			if (this.category!=null) {
				Map<String, String> categoryEnumMap = new HashMap<String, String>();
				for(InvestmentPlanCategoryTypeEnum key : InvestmentPlanCategoryTypeEnum.values()){
					categoryEnumMap.put(key.getCode(), key.getName());
				}
				return categoryEnumMap.get(this.category);
			}
			return null;
		}
		
		public String getBuildStageName(){
			if (this.buildStage!=null) {
				Map<String, String> stageEnumMap = new HashMap<String, String>();
				for(InvestmentPlanStageTypeEnum key : InvestmentPlanStageTypeEnum.values()){
					stageEnumMap.put(key.getCode(), key.getName());
				}
				return stageEnumMap.get(this.buildStage);
			}
			return null;
		}
		
}