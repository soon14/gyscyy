package com.aptech.business.managePlanContract.monthlyProductionPlan.domain;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.aptech.business.component.dictionary.ScienceTechnologyPlanStatusEnum;
import com.aptech.framework.json.JsonDateDeserializer;
import com.aptech.framework.json.JsonDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import org.apache.ibatis.type.Alias;

import com.aptech.framework.orm.BaseEntity;

/**
 * 
 * 月度生产物资计划实体类
 *
 * @author 
 * @created 2018-04-24 11:40:00
 * @lastModified 
 * @history
 *
 */
@Alias("MonthlyProductionPlanEntity")
public class MonthlyProductionPlanEntity extends BaseEntity{
		/**
		 * 主键
		 */
    	private Long id;
		/**
		 * 单位名称
		 */
    	private String unitId;
		/**
		 * 物资名称
		 */
    	private String materialName;
		/**
		 * 物资规格
		 */
    	private String materialType;
		/**
		 * 费用预算
		 */
    	private String charge;
		/**
		 * 型号
		 */
    	private String version;
		/**
		 * 数量
		 */
    	private Integer count;
		/**
		 * 功能要求
		 */
    	private String fuctionRequirement;
		/**
		 * 上传人
		 */
    	private String userId;
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
    	private String userName;
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
		public int getNumber() {
			return number;
		}
		public void setNumber(int number) {
			this.number = number;
		}
		public Long getId(){
			return id;
		}
		public void setId(Long id){
			this.id = id;
		}
		public String getUnitId(){
			return unitId;
		}
		public void setUnitId(String unitId){
			this.unitId = unitId;
		}
		public String getMaterialName(){
			return materialName;
		}
		public void setMaterialName(String materialName){
			this.materialName = materialName;
		}
		public String getMaterialType(){
			return materialType;
		}
		public void setMaterialType(String materialType){
			this.materialType = materialType;
		}
		
		public String getCharge() {
			return charge;
		}
		public void setCharge(String charge) {
			this.charge = charge;
		}
		public String getVersion(){
			return version;
		}
		public void setVersion(String version){
			this.version = version;
		}
		public Integer getCount(){
			return count;
		}
		public void setCount(Integer count){
			this.count = count;
		}
		public String getFuctionRequirement(){
			return fuctionRequirement;
		}
		public void setFuctionRequirement(String fuctionRequirement){
			this.fuctionRequirement = fuctionRequirement;
		}
		public String getUserId(){
			return userId;
		}
		public void setUserId(String userId){
			this.userId = userId;
		}
	    @JsonSerialize(using = JsonDateSerializer.class)
		public Date getUploadTime(){
			return uploadTime;
		}
	    @JsonDeserialize(using = JsonDateDeserializer.class)
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
		public String getStatusName(){
			for (ScienceTechnologyPlanStatusEnum scienceTechnologyPlanStatusEnum : ScienceTechnologyPlanStatusEnum.values()) {
				if (scienceTechnologyPlanStatusEnum.getCode().equals(this.status)) {
					return scienceTechnologyPlanStatusEnum.getName();
				}
			}
			return null;
		}
		public String getUploadTimeString() {
    		if(uploadTime!=null){
    			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
    			return  sdf.format(uploadTime);
    		}
    		return "";
    	}
}