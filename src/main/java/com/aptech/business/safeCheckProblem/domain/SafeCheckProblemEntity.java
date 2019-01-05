package com.aptech.business.safeCheckProblem.domain;

import java.util.Date;
import java.util.Map;

import org.apache.ibatis.type.Alias;

import com.aptech.common.system.dictionary.util.DictionaryUtil;
import com.aptech.common.system.dictionary.web.SysDictionaryVO;
import com.aptech.framework.json.JsonDateTimeDeserializer;
import com.aptech.framework.orm.BaseEntity;
import com.aptech.framework.util.DateFormatUtil;
import com.aptech.framework.util.YesOrNoEnum;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * 
 * 安全检查问题实体类
 *
 * @author 
 * @created 2018-09-03 10:31:57
 * @lastModified 
 * @history
 *
 */
@Alias("SafeCheckProblemEntity")
public class SafeCheckProblemEntity extends BaseEntity{
		/**
		 * 
		 */
		private static final long serialVersionUID = -6107886368788907663L;
		/**
		 * 问题描述
		 */
		private String discrible;
		/**
		 * 发现时间
		 */
		private Date discoveryTime;
		
		/**
		 *发现时间 字符串格式
		 */
		private String discoveryTimeStr;
		/**
		 * 检查方式
		 */
		private String mode;
		/**
		 * 检查人
		 */
		private String checkPersionId;
		/**
		 * 整改措施
		 */
		private String correctiveMeasures;
		/**
		 * 计划整改时间
		 */
		private Date planTime;
		
		/**
		 * 计划整改时间字符串格式
		 */
		private String planTimeStr;
		/**
		 * 整改责任人
		 */
		private String correctivePersionId;
		/**
		 * 是否整改
		 */
		private String isCorrective;
		/**
		 * 整改完成时间
		 */
		private Date completionTime;
		/**
		 * 整改完成时间字符串格式
		 */
		private String completionTimeStr;
		/**
		 * 备注
		 */
		private String remark;
		/**
		 * 创建时间
		 */
		private Date createTime;
		/**
		 * 更新时间
		 */
		private Date updateTime;
		
		/**
		 * 组织ID
		 */
		private String unitId;
		
		/**
		 * 检查人名称
		 */
		private String checkPersionName;
		
		/**
		 * 整改责任人名称
		 */
		private String correctivePersionName;
		
		/**
		 * 是否整改显示名称
		 */
		private String isCorrectiveName;
		
		/**
		 * 检查方式名称
		 */
		private String modeName;
		
		public String getDiscrible(){
			return discrible;
		}
		public void setDiscrible(String discrible){
			this.discrible = discrible;
		}
		@JsonDeserialize(using = JsonDateTimeDeserializer.class)
		public Date getDiscoveryTime(){
			return discoveryTime;
		}
		@JsonDeserialize(using = JsonDateTimeDeserializer.class)
		public void setDiscoveryTime(Date discoveryTime){
			this.discoveryTime = discoveryTime;
		}
		public String getMode(){
			return mode;
		}
		public void setMode(String mode){
			this.mode = mode;
		}
		public String getCheckPersionId(){
			return checkPersionId;
		}
		public void setCheckPersionId(String checkPersionId){
			this.checkPersionId = checkPersionId;
		}
		public String getCorrectiveMeasures(){
			return correctiveMeasures;
		}
		public void setCorrectiveMeasures(String correctiveMeasures){
			this.correctiveMeasures = correctiveMeasures;
		}
		@JsonDeserialize(using = JsonDateTimeDeserializer.class)
		public Date getPlanTime(){
			return planTime;
		}
		@JsonDeserialize(using = JsonDateTimeDeserializer.class)
		public void setPlanTime(Date planTime){
			this.planTime = planTime;
		}
		public String getCorrectivePersionId(){
			return correctivePersionId;
		}
		public void setCorrectivePersionId(String correctivePersionId){
			this.correctivePersionId = correctivePersionId;
		}
		public String getIsCorrective(){
			return isCorrective;
		}
		public void setIsCorrective(String isCorrective){
			this.isCorrective = isCorrective;
		}
		@JsonDeserialize(using = JsonDateTimeDeserializer.class)
		public Date getCompletionTime(){
			return completionTime;
		}
		@JsonDeserialize(using = JsonDateTimeDeserializer.class)
		public void setCompletionTime(Date completionTime){
			this.completionTime = completionTime;
		}
		public String getRemark(){
			return remark;
		}
		public void setRemark(String remark){
			this.remark = remark;
		}
		@JsonDeserialize(using = JsonDateTimeDeserializer.class)
		public Date getCreateTime(){
			return createTime;
		}
		@JsonDeserialize(using = JsonDateTimeDeserializer.class)
		public void setCreateTime(Date createTime){
			this.createTime = createTime;
		}
		@JsonDeserialize(using = JsonDateTimeDeserializer.class)
		public Date getUpdateTime(){
			return updateTime;
		}
		@JsonDeserialize(using = JsonDateTimeDeserializer.class)
		public void setUpdateTime(Date updateTime){
			this.updateTime = updateTime;
		}
		public String getDiscoveryTimeStr() {
			DateFormatUtil dfuYMd = DateFormatUtil.getInstance("yyyy-MM-dd HH:mm");
			if (this.discoveryTime != null) {
				discoveryTimeStr = dfuYMd.format(this.discoveryTime);
			}
			return discoveryTimeStr;
		}
		public void setDiscoveryTimeStr(String discoveryTimeStr) {
			this.discoveryTimeStr = discoveryTimeStr;
		}
		public String getPlanTimeStr() {
			DateFormatUtil dfuYMd = DateFormatUtil.getInstance("yyyy-MM-dd HH:mm");
			if (this.completionTime != null) {
				planTimeStr = dfuYMd.format(this.planTime);
			}
			return planTimeStr;
		}
		public void setPlanTimeStr(String planTimeStr) {
			this.planTimeStr = planTimeStr;
		}
		public String getCompletionTimeStr() {
			DateFormatUtil dfuYMd = DateFormatUtil.getInstance("yyyy-MM-dd HH:mm");
			if (this.completionTime != null) {
				completionTimeStr = dfuYMd.format(this.completionTime);
			}
			return completionTimeStr;
		}
		public void setCompletionTimeStr(String completionTimeStr) {
			this.completionTimeStr = completionTimeStr;
		}
		public String getUnitId() {
			return unitId;
		}
		public void setUnitId(String unitId) {
			this.unitId = unitId;
		}
		public String getCheckPersionName() {
			return checkPersionName;
		}
		public void setCheckPersionName(String checkPersionName) {
			this.checkPersionName = checkPersionName;
		}
		public String getCorrectivePersionName() {
			return correctivePersionName;
		}
		public void setCorrectivePersionName(String correctivePersionName) {
			this.correctivePersionName = correctivePersionName;
		}
		public String getIsCorrectiveName() {
			if ("1".equals(this.isCorrective)) {
				isCorrectiveName = YesOrNoEnum.YES.getDisplayName();
			} else if ("0".equals(this.isCorrective)) {
				isCorrectiveName = YesOrNoEnum.NO.getDisplayName();
			}
			return isCorrectiveName;
		}
		public void setIsCorrectiveName(String isCorrectiveName) {
			this.isCorrectiveName = isCorrectiveName;
		}
		public String getModeName() {
			Map<String, SysDictionaryVO> checkModeMap = DictionaryUtil.getDictionaries("SAFE_CHECK_MODE");
			for(String key : checkModeMap.keySet()){
				SysDictionaryVO checkModePreVO = checkModeMap.get(key);
				if (checkModePreVO.getCode().equals(this.mode)) {
					modeName = checkModePreVO.getName();
					break;
				}
			}
			return modeName;
		}
		public void setModeName(String modeName) {
			this.modeName = modeName;
		}
		
}