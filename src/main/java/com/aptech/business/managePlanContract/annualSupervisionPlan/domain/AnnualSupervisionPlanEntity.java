package com.aptech.business.managePlanContract.annualSupervisionPlan.domain;

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
 * 年度技术监督计划实体类
 *
 * @author 
 * @created 2018-04-12 15:36:28
 * @lastModified 
 * @history
 *
 */
@Alias("AnnualSupervisionPlanEntity")
public class AnnualSupervisionPlanEntity extends BaseEntity{
		/**
		 * 主键
		 */
    	private Long id;
		/**
		 * 项目名称
		 */
    	private String subject;
		/**
		 * 责任单位
		 */
    	private String unitId;
		/**
		 * 目的及方案
		 */
    	private String purposePlan;
		/**
		 * 计划完成时间
		 */
    	private String planDate;
		/**
		 * 监督专业
		 */
    	private String supervisionMajor;
		/**
		 * 备注
		 */
    	private String remark;
		/**
		 * 上传人
		 */
    	private String userId;
		/**
		 * 上传时间
		 */
    	private Date uploadTime;
		/**
		 * 附件id
		 */
    	private String fileId;
    	private String unitName;
    	private String userName;
    	private int number;
    	private String status;
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
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
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
		public String getSubject(){
			return subject;
		}
		public void setSubject(String subject){
			this.subject = subject;
		}
		public String getUnitId(){
			return unitId;
		}
		public void setUnitId(String unitId){
			this.unitId = unitId;
		}
		public String getPurposePlan(){
			return purposePlan;
		}
		public void setPurposePlan(String purposePlan){
			this.purposePlan = purposePlan;
		}
		public String getPlanDate(){
			return planDate;
		}
		public void setPlanDate(String planDate){
			this.planDate = planDate;
		}
		public String getSupervisionMajor(){
			return supervisionMajor;
		}
		public void setSupervisionMajor(String supervisionMajor){
			this.supervisionMajor = supervisionMajor;
		}
		public String getRemark(){
			return remark;
		}
		public void setRemark(String remark){
			this.remark = remark;
		}
		public String getUserId(){
			return userId;
		}
		public void setUserId(String userId){
			this.userId = userId;
		}
	    @JsonSerialize(using = JsonDateTime1Serializer.class)
		public Date getUploadTime(){
			return uploadTime;
		}
	    @JsonDeserialize(using = JsonDateTime1Deserializer.class)
		public void setUploadTime(Date uploadTime){
			this.uploadTime = uploadTime;
		}
		public String getFileId(){
			return fileId;
		}
		public void setFileId(String fileId){
			this.fileId = fileId;
		}
		public String getSupervisionMajorName() {
			if(!StringUtils.isEmpty(this.supervisionMajor)){
				Map<String, SysDictionaryVO> supervisionMajorMap  =  DictionaryUtil.getDictionaries("SUPERVISIONMAJOR");
				Map<String,String> supervisionMajorEnumMap = new HashMap<String, String>();
				for(String key : supervisionMajorMap.keySet()){
					SysDictionaryVO sysDictionaryVO = supervisionMajorMap.get(key);
					supervisionMajorEnumMap.put(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
				}
				return supervisionMajorEnumMap.get(this.supervisionMajor);
			}
			return null;
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