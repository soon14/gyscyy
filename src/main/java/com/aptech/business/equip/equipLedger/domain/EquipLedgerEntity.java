package com.aptech.business.equip.equipLedger.domain;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.type.Alias;

import com.aptech.common.system.dictionary.util.DictionaryUtil;
import com.aptech.common.system.dictionary.web.SysDictionaryVO;
import com.aptech.framework.json.JsonDateTimeDeserializer;
import com.aptech.framework.json.JsonDateTimeSerializer;
import com.aptech.framework.orm.BaseEntity;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * 
 * 设备管理实体类
 *
 * @author 
 * @created 2017-06-08 10:50:56
 * @lastModified 
 * @history
 *
 */
@Alias("EquipLedgerEntity")
public class EquipLedgerEntity extends BaseEntity{
		/**
	 * 
	 */
	private static final long serialVersionUID = -7927880644003149276L;
		/**
		 * 修改人姓名
		 */
    	private String updateUserName;
		/**
		 * 状态
		 */
    	private Integer status;
		/**
		 * 备注
		 */
    	private String remark;
		/**
		 * 规格型号
		 */
    	private String equipmentVersion;
		/**
		 * 制造商
		 */
    	private String manuFacturer;
		/**
		 * 修改时间
		 */
    	private Date updateDate;
		/**
		 * 设备类型
		 */
    	private Long equipType;
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
		/**
		 * 修改人
		 */
    	private Long updateUserId;
		/**
		 * 创建人名称
		 */
    	private String createUserName;
		/**
		 * 设备编号
		 */
    	private String code;
	
		/**
		 * 创建时间
		 */
    	private Date createDate;
		/**
		 * 创建人
		 */
    	private Long createUserId;
    	/**
		 * 父级id(树【组织机构】表使用)  
		 */
    	private Long equipParentId;
		/**
    	 * treeid
    	 */
    	private Long treeId ;
		/**
    	 * 设备名称
    	 */
    	private String name;
    	/**
		 * 设备类型名称
		 */
    	private String equipTypeName;
    	/**
    	 * 模版ID
    	 */
    	private Long equipModelId;
		/**
    	 * 组织机构ID(树【组织机构】表使用)  
    	 */
    	private Long unitId;
    	/**
		 * 父级id(树【组织机构】表使用)
		 */
    	private String pathCode;
    	/**
    	 * 基础参数
    	 */
    	private String parameter;
    	/**
    	 * 基础参数值
    	 */
    	private String defaultValue;
    	/**
    	 * 存储设备参数数组   
    	 */
    	private String[] paramArray;
    	/**
    	 * 业务类型
    	 */
    	private String businessType ;
    	/**
    	 * 设备电压ID
    	 */
    	private Long equipVoltageId ;
    	/**
    	 * 设备管理分类
    	 */
    	private int equipManageType=10;
    	/**
    	 * 设备管理分类
    	 */
    	private String equipManageTypeName ;
    	
    	private String unitName;
    	
    	
    	
    	public void setEquipManageTypeName(String equipManageTypeName) {
			this.equipManageTypeName = equipManageTypeName;
		}
		public String getUnitName() {
			return unitName;
		}
		public void setUnitName(String unitName) {
			this.unitName = unitName;
		}
		public int getEquipManageType() {
			return equipManageType;
		}
		public void setEquipManageType(int equipManageType) {
			this.equipManageType = equipManageType;
		}
    	public Long getTreeId() {
			return treeId;
		}
		public void setTreeId(Long treeId) {
			this.treeId = treeId;
		}
		public Long getEquipVoltageId() {
			return equipVoltageId;
		}
		public void setEquipVoltageId(Long equipVoltageId) {
			this.equipVoltageId = equipVoltageId;
		}
		public String getBusinessType() {
			return businessType;
		}
		public void setBusinessType(String businessType) {
			this.businessType = businessType;
		}
		/**
    	 * 设备类型名称
    	 */
	   public String getEquipTypeName() {
			Map<String, SysDictionaryVO> codeDateTypeMap  =  DictionaryUtil.getDictionaries("DEVICE_TYPE");
			String equipTypeName = "";
			if(codeDateTypeMap.keySet().size()>0){
				equipTypeName = codeDateTypeMap.get(String.valueOf(this.equipType)).getName();
			}
	        return equipTypeName;
		}
		public String[] getParamArray() {
			return paramArray;
		}
		public void setParamArray(String[] paramArray) {
			this.paramArray = paramArray;
		}
		public String getParameter() {
			return parameter;
		}
		public void setParameter(String parameter) {
			this.parameter = parameter;
		}
		public String getDefaultValue() {
			return defaultValue;
		}
		public void setDefaultValue(String defaultValue) {
			this.defaultValue = defaultValue;
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
		public String getPathCode() {
			return pathCode;
		}
		public void setPathCode(String pathCode) {
			this.pathCode = pathCode;
		}
		public void setEquipTypeName(String equipTypeName) {
			this.equipTypeName = equipTypeName;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getUpdateUserName(){
			return updateUserName;
		}
		public void setUpdateUserName(String updateUserName){
			this.updateUserName = updateUserName;
		}
		public Integer getStatus(){
			return status;
		}
		public void setStatus(Integer status){
			this.status = status;
		}
		public String getRemark(){
			return remark;
		}
		public void setRemark(String remark){
			this.remark = remark;
		}
		public String getEquipmentVersion(){
			return equipmentVersion;
		}
		public void setEquipmentVersion(String equipmentVersion){
			this.equipmentVersion = equipmentVersion;
		}
		public String getManuFacturer(){
			return manuFacturer;
		}
		public void setManuFacturer(String manuFacturer){
			this.manuFacturer = manuFacturer;
		}
	    @JsonSerialize(using = JsonDateTimeSerializer.class)
		public Date getUpdateDate(){
			return updateDate;
		}
	    @JsonDeserialize(using = JsonDateTimeDeserializer.class)
		public void setUpdateDate(Date updateDate){
			this.updateDate = updateDate;
		}
		public Long getEquipType(){
			return equipType;
		}
		public void setEquipType(Long equipType){
			this.equipType = equipType;
		}
		public Long getUpdateUserId(){
			return updateUserId;
		}
		public void setUpdateUserId(Long updateUserId){
			this.updateUserId = updateUserId;
		}
		public String getCreateUserName(){
			return createUserName;
		}
		public void setCreateUserName(String createUserName){
			this.createUserName = createUserName;
		}
	    @JsonSerialize(using = JsonDateTimeSerializer.class)
		public Date getCreateDate(){
			return createDate;
		}
	    @JsonDeserialize(using = JsonDateTimeDeserializer.class)
		public void setCreateDate(Date createDate){
			this.createDate = createDate;
		}
		public Long getCreateUserId(){
			return createUserId;
		}
		public void setCreateUserId(Long createUserId){
			this.createUserId = createUserId;
		}
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		public Long getUnitId() {
			return unitId;
		}
		public void setUnitId(Long unitId) {
			this.unitId = unitId;
		}
		public Long getEquipModelId() {
			return equipModelId;
		}
		public void setEquipModelId(Long equipModelId) {
			this.equipModelId = equipModelId;
		}
		public Long getEquipParentId() {
			return equipParentId;
		}
		public void setEquipParentId(Long equipParentId) {
			this.equipParentId = equipParentId;
		}
		/**
    	 * 设备管理分类
    	 */
//	   public String getEquipManageTypeName() {
//			Map<String, SysDictionaryVO> specialCodeMap  =  DictionaryUtil.getDictionaries("EQUIPMANAGETYPE");
//			Map<String,String> specialCodeEnumMap = new HashMap<String, String>();
//			for(String key : specialCodeMap.keySet()){
//				SysDictionaryVO sysDictionaryVO = specialCodeMap.get(key);
//				specialCodeEnumMap.put(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
//			}
//			return specialCodeEnumMap.get(this.equipManageType);
//		}
	   public String getEquipManageTypeName() {
			Map<String, SysDictionaryVO> codeDateTypeMap  =  DictionaryUtil.getDictionaries("EQUIPMANAGETYPE");
			String equipManageTypeName = "";
			if(codeDateTypeMap.keySet().size()>0){
				equipManageTypeName = codeDateTypeMap.get(String.valueOf(this.equipManageType)).getName();
			}
	        return equipManageTypeName;
		}
	   
}