package com.aptech.business.managePlanContract.annualTrainingPlan.domain;

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
 * 年度培训计划实体类
 *
 * @author 
 * @created 2018-04-13 15:24:06
 * @lastModified 
 * @history
 *
 */
@Alias("AnnualTrainingPlanEntity")
public class AnnualTrainingPlanEntity extends BaseEntity{
		/**
		 * 主键
		 */
    	private Long id;
		/**
		 * 培训单位
		 */
    	private String unitId;
		/**
		 * 培训名称
		 */
    	private String trainName;
		/**
		 * 培训内容
		 */
    	private String trainContent;
		/**
		 * 培训时间
		 */
    	private Date trainTime;
		/**
		 * 培训人员
		 */
    	private String trainMember;
		/**
		 * 培训地点
		 */
    	private String trainLocation;
		/**
		 * 培训类别
		 */
    	private String trainType;
    	private String unitName;
    	private String trainMemberName;
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
    	private String userId;
    	private String userName;
    	
    	
    	
    	
    	
		public String getUserId() {
			return userId;
		}
		public void setUserId(String userId) {
			this.userId = userId;
		}
		public String getUserName() {
			return userName;
		}
		public void setUserName(String userName) {
			this.userName = userName;
		}
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
		public String getTrainMemberName() {
			return trainMemberName;
		}
		public void setTrainMemberName(String trainMemberName) {
			this.trainMemberName = trainMemberName;
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
		public String getTrainName(){
			return trainName;
		}
		public void setTrainName(String trainName){
			this.trainName = trainName;
		}
		public String getTrainContent(){
			return trainContent;
		}
		public void setTrainContent(String trainContent){
			this.trainContent = trainContent;
		}
	    @JsonSerialize(using = JsonDateTime1Serializer.class)
		public Date getTrainTime(){
			return trainTime;
		}
	    @JsonDeserialize(using = JsonDateTime1Deserializer.class)
		public void setTrainTime(Date trainTime){
			this.trainTime = trainTime;
		}
		public String getTrainMember(){
			return trainMember;
		}
		public void setTrainMember(String trainMember){
			this.trainMember = trainMember;
		}
		public String getTrainLocation(){
			return trainLocation;
		}
		public void setTrainLocation(String trainLocation){
			this.trainLocation = trainLocation;
		}
		public String getTrainType(){
			return trainType;
		}
		public void setTrainType(String trainType){
			this.trainType = trainType;
		}
		public String getTrainTypeName() {
			if(!StringUtils.isEmpty(this.trainType)){
				Map<String, SysDictionaryVO> trainTypeMap  =  DictionaryUtil.getDictionaries("TRAININGTYPE");
				Map<String,String> trainTypeEnumMap = new HashMap<String, String>();
				for(String key : trainTypeMap.keySet()){
					SysDictionaryVO sysDictionaryVO = trainTypeMap.get(key);
					trainTypeEnumMap.put(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
				}
				return trainTypeEnumMap.get(this.trainType);
			}
			return null;
		}
		public String getTrainTimeString() {
			if(trainTime!=null){
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				return  sdf.format(trainTime);
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