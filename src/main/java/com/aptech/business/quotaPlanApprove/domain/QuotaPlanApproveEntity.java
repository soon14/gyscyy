package com.aptech.business.quotaPlanApprove.domain;

import java.util.Date;

import com.aptech.business.component.dictionary.ScienceTechnologyPlanStatusEnum;
import com.aptech.framework.json.JsonDateDeserializer;
import com.aptech.framework.json.JsonDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import org.apache.ibatis.type.Alias;

import com.aptech.framework.orm.BaseEntity;
import com.aptech.framework.util.DateFormatUtil;

/**
 * 
 * 指标计划流程表实体类
 *
 * @author 
 * @created 2018-09-21 10:10:36
 * @lastModified 
 * @history
 *
 */
@Alias("QuotaPlanApproveEntity")
public class QuotaPlanApproveEntity extends BaseEntity{
		/**
		 * 主键
		 */
    	private Long id;
		/**
		 * 场站id
		 */
    	private String farmId;
    	/**
    	 * 场站中文名称
    	 */
    	private String farmName;
		/**
		 * 年份
		 */
    	private Date year;
    	/**
    	 * 年份字符串格式
    	 */
    	private String yearStr;
		/**
		 * 数据类型
		 */
    	private String type;
		/**
		 * 生产技术处id
		 */
    	private String productionSkillId;
    	/**
    	 * 生产技术处人员名称
    	 */
    	private String productionSkillName;
		/**
		 * 计划经营处id
		 */
    	private String planRunningId;
    	/**
    	 * 计划经营处人员名称
    	 */
    	private String planRunningName;
		/**
		 * 单位领导id
		 */
    	private String leaderId;
    	/**
    	 * 单位领导人员名称
    	 */
    	private String leaderName;
		/**
		 * 流程状态
		 */
    	private String status;
    	/**
    	 * 流程状态中文名称
    	 */
    	private String statusName;
		/**
		 * 生产运行单位id
		 */
    	private String productionRunningId;
    	/**
    	 * 生产运行单位人员名称
    	 */
    	private String productionRunningName;
    	/**
    	 * 计划经营处下文执行id
    	 */
    	private String planRunningDown;
    	/**
    	 * 计划经营处下文执行名称
    	 */
    	private String planRunningDownName;
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

		public Long getId(){
			return id;
		}
		public void setId(Long id){
			this.id = id;
		}
		public String getFarmId(){
			return farmId;
		}
		public void setFarmId(String farmId){
			this.farmId = farmId;
		}
	    @JsonSerialize(using = JsonDateSerializer.class)
		public Date getYear(){
			return year;
		}
	    @JsonDeserialize(using = JsonDateDeserializer.class)
		public void setYear(Date year){
			this.year = year;
		}
		public String getType(){
			return type;
		}
		public void setType(String type){
			this.type = type;
		}
		public String getProductionSkillId(){
			return productionSkillId;
		}
		public void setProductionSkillId(String productionSkillId){
			this.productionSkillId = productionSkillId;
		}
		public String getPlanRunningId(){
			return planRunningId;
		}
		public void setPlanRunningId(String planRunningId){
			this.planRunningId = planRunningId;
		}
		public String getLeaderId(){
			return leaderId;
		}
		public void setLeaderId(String leaderId){
			this.leaderId = leaderId;
		}
		public String getStatus(){
			return status;
		}
		public void setStatus(String status){
			this.status = status;
		}
		public String getProductionRunningId(){
			return productionRunningId;
		}
		public void setProductionRunningId(String productionRunningId){
			this.productionRunningId = productionRunningId;
		}
		public String getFarmName() {
			return farmName;
		}
		public void setFarmName(String farmName) {
			this.farmName = farmName;
		}
		public String getYearStr() {
			if (yearStr !=null && yearStr !="") {
				return yearStr;
			}
			DateFormatUtil df= DateFormatUtil.getInstance("yyyy");
			if (this.year==null) {
				return yearStr;
			}
			return df.format(this.year);
		}
		public void setYearStr(String yearStr) {
			this.yearStr = yearStr;
		}
		public String getStatusName() {
			for(ScienceTechnologyPlanStatusEnum sps : ScienceTechnologyPlanStatusEnum.values()){
				if(sps.getCode().equals(this.status)){
					return sps.getName();
				}
			}
			return statusName;
		}
		public void setStatusName(String statusName) {
			this.statusName = statusName;
		}
		public String getProductionSkillName() {
			return productionSkillName;
		}
		public void setProductionSkillName(String productionSkillName) {
			this.productionSkillName = productionSkillName;
		}
		public String getPlanRunningName() {
			return planRunningName;
		}
		public void setPlanRunningName(String planRunningName) {
			this.planRunningName = planRunningName;
		}
		public String getLeaderName() {
			return leaderName;
		}
		public void setLeaderName(String leaderName) {
			this.leaderName = leaderName;
		}
		public String getProductionRunningName() {
			return productionRunningName;
		}
		public void setProductionRunningName(String productionRunningName) {
			this.productionRunningName = productionRunningName;
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
		public String getPlanRunningDown() {
			return planRunningDown;
		}
		public void setPlanRunningDown(String planRunningDown) {
			this.planRunningDown = planRunningDown;
		}
		public String getPlanRunningDownName() {
			return planRunningDownName;
		}
		public void setPlanRunningDownName(String planRunningDownName) {
			this.planRunningDownName = planRunningDownName;
		}
}