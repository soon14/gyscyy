package com.aptech.business.managePlanContract.scienceTechnologyPlan.domain;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.aptech.business.component.dictionary.ScienceTechnologyPlanStatusEnum;
import com.aptech.common.system.dictionary.util.DictionaryUtil;
import com.aptech.common.system.dictionary.web.SysDictionaryVO;
import com.aptech.framework.json.JsonDateDeserializer;
import com.aptech.framework.json.JsonDateSerializer;
import com.aptech.framework.json.JsonDateTime1Deserializer;
import com.aptech.framework.json.JsonDateTime1Serializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.type.Alias;

import com.aptech.framework.orm.BaseEntity;

/**
 * 
 * 年度科技计划实体类
 *
 * @author 
 * @created 2018-04-02 19:54:46
 * @lastModified 
 * @history
 *
 */
@Alias("ScienceTechnologyPlanEntity")
public class ScienceTechnologyPlanEntity extends BaseEntity{
		/**
		 * 主键
		 */
    	private Long id;
		/**
		 * 上传人Id
		 */
    	private String userId;
		/**
		 * 单位名称
		 */
    	private String unitId;
		/**
		 * 计划名称
		 */
    	private String planName;
		/**
		 * 内容描述
		 */
    	private String description;
		/**
		 * 计划分类
		 */
    	private String planType;
		/**
		 * 上传时间
		 */
    	private Date uploadTime;
		/**
		 * 计划完成时间
		 */
    	private String planCompleteTime;
		/**
		 * 备注
		 */
    	private String remark;
		/**
		 * 附件id
		 */
    	private String fileId;
		/**
		 * 状态
		 */
    	private String status;
    	private String unitName;
    	private String userName;
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
		public String getUserId(){
			return userId;
		}
		public void setUserId(String userId){
			this.userId = userId;
		}
		public String getUnitId(){
			return unitId;
		}
		public void setUnitId(String unitId){
			this.unitId = unitId;
		}
		public String getDescription(){
			return description;
		}
		public void setDescription(String description){
			this.description = description;
		}
	    @JsonSerialize(using = JsonDateTime1Serializer.class)
		public Date getUploadTime(){
			return uploadTime;
		}
	    @JsonDeserialize(using = JsonDateTime1Deserializer.class)
		public void setUploadTime(Date uploadTime){
			this.uploadTime = uploadTime;
		}
		public String getRemark(){
			return remark;
		}
		public String getPlanName() {
			return planName;
		}
		public void setPlanName(String planName) {
			this.planName = planName;
		}
		public String getPlanType() {
			return planType;
		}
		public void setPlanType(String planType) {
			this.planType = planType;
		}
		public String getPlanTypeName() {
			if(!StringUtils.isEmpty(this.planType)){
				Map<String, SysDictionaryVO> planTypeMap  =  DictionaryUtil.getDictionaries("PLANTYPE");
				Map<String,String> planTypeEnumMap = new HashMap<String, String>();
				for(String key : planTypeMap.keySet()){
					SysDictionaryVO sysDictionaryVO = planTypeMap.get(key);
					planTypeEnumMap.put(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
				}
				return planTypeEnumMap.get(this.planType);
			}
			return null;
		}
		public String getPlanCompleteTime() {
			return planCompleteTime;
		}
		public void setPlanCompleteTime(String planCompleteTime) {
			this.planCompleteTime = planCompleteTime;
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
		public String getStatus(){
			return status;
		}
		public void setStatus(String status){
			this.status = status;
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