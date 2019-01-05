package com.aptech.business.sysManagement.domain;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.type.Alias;

import com.aptech.business.component.dictionary.SysManagementStatusEnum;
import com.aptech.business.component.dictionary.WorkTypeEnum;
import com.aptech.common.system.dictionary.util.DictionaryUtil;
import com.aptech.common.system.dictionary.web.SysDictionaryVO;
import com.aptech.framework.json.JsonDateTime1Deserializer;
import com.aptech.framework.json.JsonDateTime1Serializer;
import com.aptech.framework.orm.BaseEntity;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * 
 * 制度管理实体类
 *
 * @author 
 * @created 2018-03-14 16:26:37
 * @lastModified 
 * @history
 *
 */
@Alias("SysManagementEntity")
public class SysManagementEntity extends BaseEntity{
		/**
		 * 主键
		 */
    	private Long id;
		/**
		 * 编码
		 */
    	private String code;
		/**
		 * 制度名称
		 */
    	private String sysName;
		/**
		 * 年号
		 */
    	private Date yearNum;
    	
    	private String yearNumString;
		/**
		 * 状态
		 */
    	private String status;
		/**
		 * 实施日期
		 */
    	private Date materialDate;
		/**
		 * 类别
		 */
    	private String type;
		/**
		 * 单位名称
		 */
    	private String unitId;
		/**
		 * 填报人
		 */
    	private String applyUserId;
    	/**
		 * 附件Id
		 */
    	private String fileId;
		/**
    	 * 附件名称
    	 */
		private String[] fileName;
		/**
		 * 附件URL
		 */
		private String[] fileUrl;
		private String statusName;
		private String yearNumName;
		private String typeName;
		private String unitName;
		private String applyUserName;
		
		/**
		 * 归口单位id
		 */
		private String centralizedUnitId;
		/**
		 * 归口单位名称
		 */
		private String centralizedUnitName;
		
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
		public String getSysName(){
			return sysName;
		}
		public void setSysName(String sysName){
			this.sysName = sysName;
		}
		public String getStatus(){
			return status;
		}
		public void setStatus(String status){
			this.status = status;
		}
		@JsonSerialize(using = JsonDateTime1Serializer.class)
	    public Date getMaterialDate() {
			return materialDate;
		}
		 @JsonDeserialize(using = JsonDateTime1Deserializer.class)
		public void setMaterialDate(Date materialDate) {
			this.materialDate = materialDate;
		}
		public String getType(){
			return type;
		}
		public void setType(String type){
			this.type = type;
		}
		public String getUnitId(){
			return unitId;
		}
		public void setUnitId(String unitId){
			this.unitId = unitId;
		}
		public String getApplyUserId(){
			return applyUserId;
		}
		public void setApplyUserId(String applyUserId){
			this.applyUserId = applyUserId;
		}
		public String getFileId() {
			return fileId;
		}
		public void setFileId(String fileId) {
			this.fileId = fileId;
		}
		public String[] getFileName() {
			return fileName;
		}
		public void setFileName(String[] fileName) {
			this.fileName = fileName;
		}
		public String[] getFileUrl() {
			return fileUrl;
		}
		public void setFileUrl(String[] fileUrl) {
			this.fileUrl = fileUrl;
		}
		public String getStatusName() {
			for (SysManagementStatusEnum  sysManagementStatusEnum: SysManagementStatusEnum.values()) {
				if (sysManagementStatusEnum.getCode().equals(this.getStatus())) {
					statusName =  sysManagementStatusEnum.getName();
				}
			}
			return statusName;
		}
		public void setStatusName(String statusName) {
			this.statusName = statusName;
		}
//		public String getYearNumName() {
//			if(!StringUtils.isEmpty(this.yearNum)){
//				Map<String, SysDictionaryVO> yearNumMap  =  DictionaryUtil.getDictionaries("YEARNUM");
//				Map<String,String> yearNumEnumMap = new HashMap<String, String>();
//				for(String key : yearNumMap.keySet()){
//					SysDictionaryVO sysDictionaryVO = yearNumMap.get(key);
//					yearNumEnumMap.put(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
//				}
//				return yearNumEnumMap.get(this.yearNum);
//			}
//			return null;
//		}
//		public void setYearNumName(String yearNumName) {
//			this.yearNumName = yearNumName;
//		}
		public String getTypeName() {
			if(!StringUtils.isEmpty(this.type)){
				Map<String, SysDictionaryVO> typeNumMap  =  DictionaryUtil.getDictionaries("SYSMANAGENTMENTTYPE");
				Map<String,String> typeEnumMap = new HashMap<String, String>();
				for(String key : typeNumMap.keySet()){
					SysDictionaryVO sysDictionaryVO = typeNumMap.get(key);
					typeEnumMap.put(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
				}
				return typeEnumMap.get(this.type);
			}
			return null;
		}
		public void setTypeName(String typeName) {
			this.typeName = typeName;
		}
		public String getUnitName() {
			return unitName;
		}
		public void setUnitName(String unitName) {
			this.unitName = unitName;
		}
		public String getApplyUserName() {
			return applyUserName;
		}
		public void setApplyUserName(String applyUserName) {
			this.applyUserName = applyUserName;
		}
		@JsonSerialize(using = JsonDateTime1Serializer.class)
		public Date getYearNum() {
			return yearNum;
		}
		@JsonDeserialize(using = JsonDateTime1Deserializer.class)
		public void setYearNum(Date yearNum) {
			this.yearNum = yearNum;
		}
		public String getYearNumString() {
			return yearNumString;
		}
		public void setYearNumString(String yearNumString) {
			this.yearNumString = yearNumString;
		}
		public String getCentralizedUnitId() {
			return centralizedUnitId;
		}
		public void setCentralizedUnitId(String centralizedUnitId) {
			this.centralizedUnitId = centralizedUnitId;
		}
		public String getCentralizedUnitName() {
			return centralizedUnitName;
		}
		public void setCentralizedUnitName(String centralizedUnitName) {
			this.centralizedUnitName = centralizedUnitName;
		}
		
}