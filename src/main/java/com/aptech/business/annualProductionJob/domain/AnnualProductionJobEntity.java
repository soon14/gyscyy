package com.aptech.business.annualProductionJob.domain;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.aptech.business.component.dictionary.ScienceTechnologyPlanStatusEnum;
import com.aptech.business.util.JsonDateDeserializer;
import com.aptech.common.system.dictionary.util.DictionaryUtil;
import com.aptech.common.system.dictionary.web.SysDictionaryVO;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.type.Alias;

import com.aptech.framework.json.JsonDateSerializer;
import com.aptech.framework.json.JsonDateTime1Deserializer;
import com.aptech.framework.json.JsonDateTime1Serializer;
import com.aptech.framework.orm.BaseEntity;
import com.aptech.framework.util.DateFormatUtil;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * 
 * 年度生产工作计划实体类
 *
 * @author 
 * @created 2018-09-20 13:26:33
 * @lastModified 
 * @history
 *
 */
@Alias("AnnualProductionJobEntity")
public class AnnualProductionJobEntity extends BaseEntity{
		/**
		 * 主键
		 */
    	private Long id;
		/**
		 * 创建人
		 */
    	private String createUserId;
		/**
		 * 创建时间
		 */
    	private Date createDate;
		/**
		 * 修改人
		 */
    	private String updateUserId;
		/**
		 * 修改时间
		 */
    	private Date updateDate;
		/**
		 * 计划日期
		 */
    	private Date planTime;
    	/**
    	 * 年度工作计划日期字符串格式
    	 */
    	private String planYearTimeStr;
    	/**
    	 * 月度工作计划日期字符串格式
    	 */
    	private String planMonthTimeStr;
		/**
		 * 计划内容
		 */
    	private String content;
		/**
		 * 工作名称
		 */
    	private String workName;
		/**
		 * 单位名称id
		 */
    	private String unitId;
    	/**
    	 * 单位中文名称
    	 */
    	private String unitName;
		/**
		 * 进度安排
		 */
    	private String progress;
		/**
		 * 上传附件
		 */
    	private String file;
		/**
		 * 流程状态
		 */
    	private String approveStatus;
    	/**
    	 * 流程状态名称
    	 */
    	private String approveStatusName;
		/**
		 * 数据类型,1年度生产工作计划，2月度生产工作计划
		 */
    	private String type;
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
    	//封存状态（1封存0未封存）
    	private String sealStatus;
    	//完成状态（1完成0未完成）
    	private String completeStatus;
    	//完成时间
    	private Date completeDate;
    	private String completeDateStr;
    	//鉴定结果（1完成0未完成）
    	private String result;
    	
    	
    	
    	
		public String getSealStatus() {
			return sealStatus;
		}
		public void setSealStatus(String sealStatus) {
			this.sealStatus = sealStatus;
		}
		public String getCompleteStatus() {
			return completeStatus;
		}
		public void setCompleteStatus(String completeStatus) {
			this.completeStatus = completeStatus;
		}
		 @JsonSerialize(using = JsonDateTime1Serializer.class)
		public Date getCompleteDate() {
			return completeDate;
		}
		 @JsonDeserialize(using = JsonDateTime1Deserializer.class)
		public void setCompleteDate(Date completeDate) {
			this.completeDate = completeDate;
		}
		public String getResult() {
			return result;
		}
		public void setResult(String result) {
			this.result = result;
		}
		public Long getId(){
			return id;
		}
		public void setId(Long id){
			this.id = id;
		}
		public String getCreateUserId(){
			return createUserId;
		}
		public void setCreateUserId(String createUserId){
			this.createUserId = createUserId;
		}
	    @JsonSerialize(using = JsonDateSerializer.class)
		public Date getCreateDate(){
			return createDate;
		}
	    @JsonDeserialize(using = JsonDateDeserializer.class)
		public void setCreateDate(Date createDate){
			this.createDate = createDate;
		}
		public String getUpdateUserId(){
			return updateUserId;
		}
		public void setUpdateUserId(String updateUserId){
			this.updateUserId = updateUserId;
		}
	    @JsonSerialize(using = JsonDateSerializer.class)
		public Date getUpdateDate(){
			return updateDate;
		}
	    @JsonDeserialize(using = JsonDateDeserializer.class)
		public void setUpdateDate(Date updateDate){
			this.updateDate = updateDate;
		}
	    @JsonSerialize(using = JsonDateSerializer.class)
		public Date getPlanTime(){
			return planTime;
		}
	    @JsonDeserialize(using = JsonDateDeserializer.class)
		public void setPlanTime(Date planTime){
			this.planTime = planTime;
		}
		public String getContent(){
			return content;
		}
		public void setContent(String content){
			this.content = content;
		}
		public String getWorkName(){
			return workName;
		}
		public void setWorkName(String workName){
			this.workName = workName;
		}
		public String getUnitId(){
			return unitId;
		}
		public void setUnitId(String unitId){
			this.unitId = unitId;
		}
		public String getProgress(){
			return progress;
		}
		public void setProgress(String progress){
			this.progress = progress;
		}
		public String getFile(){
			return file;
		}
		public void setFile(String file){
			this.file = file;
		}
		public String getApproveStatus(){
			return approveStatus;
		}
		public void setApproveStatus(String approveStatus){
			this.approveStatus = approveStatus;
		}
		public String getType(){
			return type;
		}
		public void setType(String type){
			this.type = type;
		}
		public String getUnitName() {
			return unitName;
		}
		public void setUnitName(String unitName) {
			this.unitName = unitName;
		}
		public String getPlanYearTimeStr() {
			if (planYearTimeStr !=null && planYearTimeStr !="") {
				return planYearTimeStr;
			}
			DateFormatUtil df= DateFormatUtil.getInstance("yyyy-MM");
			if (this.planTime==null) {
				return planYearTimeStr;
			}
			return df.format(this.planTime);
		}
		public void setPlanYearTimeStr(String planYearTimeStr) {
			this.planYearTimeStr = planYearTimeStr;
		}
		public String getPlanMonthTimeStr() {
			if (planMonthTimeStr !=null && planMonthTimeStr !="") {
				return planMonthTimeStr;
			}
			DateFormatUtil df= DateFormatUtil.getInstance("yyyy-MM-dd");
			if (this.planTime==null) {
				return planMonthTimeStr;
			}
			return df.format(this.planTime);
		}
		public void setPlanMonthTimeStr(String planMonthTimeStr) {
			this.planMonthTimeStr = planMonthTimeStr;
		}
		public String getApproveStatusName() {
			for(ScienceTechnologyPlanStatusEnum sps : ScienceTechnologyPlanStatusEnum.values()){
				if(sps.getCode().equals(this.approveStatus)){
					return sps.getName();
				}
			}
			return approveStatusName;
		}
		public void setApproveStatusName(String approveStatusName) {
			this.approveStatusName = approveStatusName;
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
		public String getCompleteDateStr() {
			if (completeDateStr !=null && completeDateStr !="") {
				return completeDateStr;
			}
			DateFormatUtil df= DateFormatUtil.getInstance("yyyy-MM-dd HH:mm");
			if (this.completeDate==null) {
				return completeDateStr;
			}
			return df.format(this.completeDate);
		}
		// 封存状态
		public String getSealStatusName() {
			if (!StringUtils.isEmpty(this.sealStatus)) {
				Map<String, SysDictionaryVO> sealStatusMap = DictionaryUtil
						.getDictionaries("SEAL_STATUS");
				Map<String, String> sealStatusEnumMap = new HashMap<String, String>();
				for (String key : sealStatusMap.keySet()) {
					SysDictionaryVO sysDictionaryVO = sealStatusMap.get(key);
					sealStatusEnumMap.put(sysDictionaryVO.getCode(),
							sysDictionaryVO.getName());
				}
				return sealStatusEnumMap.get(this.approveStatus);
			}
			return null;
		}
		// 完成状态
		public String getCompleteStatusName() {
			if (!StringUtils.isEmpty(this.completeStatus)) {
				Map<String, SysDictionaryVO> completeStatusMap = DictionaryUtil
						.getDictionaries("COMPLETE_STATUS");
				Map<String, String> completeStatusEnumMap = new HashMap<String, String>();
				for (String key : completeStatusMap.keySet()) {
					SysDictionaryVO sysDictionaryVO = completeStatusMap.get(key);
					completeStatusEnumMap.put(sysDictionaryVO.getCode(),
							sysDictionaryVO.getName());
				}
				return completeStatusEnumMap.get(this.completeStatus);
			}
			return null;
		}
		// 鉴定结果
		public String getResultName() {
			if (!StringUtils.isEmpty(this.result)) {
				Map<String, SysDictionaryVO> resultMap = DictionaryUtil
						.getDictionaries("RESULT");
				Map<String, String> resultEnumMap = new HashMap<String, String>();
				for (String key : resultMap.keySet()) {
					SysDictionaryVO sysDictionaryVO = resultMap.get(key);
					resultEnumMap.put(sysDictionaryVO.getCode(),
							sysDictionaryVO.getName());
				}
				return resultEnumMap.get(this.result);
			}
			return null;
		}
}