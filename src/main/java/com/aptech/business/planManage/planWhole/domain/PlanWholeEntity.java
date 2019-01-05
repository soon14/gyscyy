package com.aptech.business.planManage.planWhole.domain;

import org.apache.ibatis.type.Alias;

import com.aptech.framework.orm.BaseEntity;

/**
 * 
 * 整体计划实体类
 *
 * @author 
 * @created 2017-11-13 15:10:22
 * @lastModified 
 * @history
 *
 */
@Alias("PlanWholeEntity")
public class PlanWholeEntity extends BaseEntity{
		/**
	 * 
	 */
	private static final long serialVersionUID = -6753143722965731371L;
		/**
		 * 主键
		 */
    	private Long id;
		/**
		 * 计划id
		 */
    	private Long planId;
		/**
		 * 序号
		 */
    	private Long num;
		/**
		 * 项目名称
		 */
    	private String projectName;
		/**
		 * 计划开工时间
		 */
    	private String stratTime;
		/**
		 * 计划完成时间
		 */
    	private String endTime;
		/**
		 * 计划总投资（万元）
		 */
    	private String planTotal;
		/**
		 * 备注
		 */
    	private String remark;
    	
    	private String  uuid;
    	
    	private String  uuidWhole;
    	
		public String getUuidWhole() {
			return uuidWhole;
		}
		public void setUuidWhole(String uuidWhole) {
			this.uuidWhole = uuidWhole;
		}
		public String getUuid() {
			return uuid;
		}
		public void setUuid(String uuid) {
			this.uuid = uuid;
		}

		public Long getId(){
			return id;
		}
		public void setId(Long id){
			this.id = id;
		}
		public Long getPlanId(){
			return planId;
		}
		public void setPlanId(Long planId){
			this.planId = planId;
		}
		public Long getNum(){
			return num;
		}
		public void setNum(Long num){
			this.num = num;
		}
		public String getProjectName(){
			return projectName;
		}
		public void setProjectName(String projectName){
			this.projectName = projectName;
		}
		public String getStratTime() {
			return stratTime;
		}
		public void setStratTime(String stratTime) {
			this.stratTime = stratTime;
		}
		public String getEndTime(){
			return endTime;
		}
		public void setEndTime(String endTime){
			this.endTime = endTime;
		}
		public String getPlanTotal(){
			return planTotal;
		}
		public void setPlanTotal(String planTotal){
			this.planTotal = planTotal;
		}
		public String getRemark(){
			return remark;
		}
		public void setRemark(String remark){
			this.remark = remark;
		}
}