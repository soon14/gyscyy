package com.aptech.business.defectManage.defectEquipment.domain;

import org.apache.ibatis.type.Alias;
import com.aptech.framework.orm.BaseEntity;

/**
 * 
 * 缺陷设备关系表实体类
 *
 * @author 
 * @created 2018-06-15 15:44:31
 * @lastModified 
 * @history
 *
 */
@Alias("DefectEquipmentEntity")
public class DefectEquipmentEntity extends BaseEntity{
		/**
		 * 主键
		 */
    	private Long id;
		/**
		 * 缺陷id
		 */
    	private String defectId;
		/**
		 * 设备编码
		 */
    	private String equipCode;
		/**
		 * 设备名称
		 */
    	private String equipName;
		/**
		 * 状态
		 */
    	private String status;
    	//缺陷类型
    	private String equipType;
    	private String equipTypeName;
    	//设备管理类型
    	private String equipManageType;
    	private String equipManageTypeName;
    	//单位名称
    	private String unitId;
    	private String unitName;
    	//鉴定结果
    	private String appraisalResult;
    	
    	
    	
    	
		public String getAppraisalResult() {
			return appraisalResult;
		}
		public void setAppraisalResult(String appraisalResult) {
			this.appraisalResult = appraisalResult;
		}
		public String getEquipType() {
			return equipType;
		}
		public void setEquipType(String equipType) {
			this.equipType = equipType;
		}
		public String getEquipTypeName() {
			return equipTypeName;
		}
		public void setEquipTypeName(String equipTypeName) {
			this.equipTypeName = equipTypeName;
		}
		public String getEquipManageType() {
			return equipManageType;
		}
		public void setEquipManageType(String equipManageType) {
			this.equipManageType = equipManageType;
		}
		public String getEquipManageTypeName() {
			return equipManageTypeName;
		}
		public void setEquipManageTypeName(String equipManageTypeName) {
			this.equipManageTypeName = equipManageTypeName;
		}
		public String getUnitId() {
			return unitId;
		}
		public void setUnitId(String unitId) {
			this.unitId = unitId;
		}
		public String getUnitName() {
			return unitName;
		}
		public void setUnitName(String unitName) {
			this.unitName = unitName;
		}
		public Long getId(){
			return id;
		}
		public void setId(Long id){
			this.id = id;
		}
		public String getDefectId(){
			return defectId;
		}
		public void setDefectId(String defectId){
			this.defectId = defectId;
		}
		public String getEquipCode(){
			return equipCode;
		}
		public void setEquipCode(String equipCode){
			this.equipCode = equipCode;
		}
		public String getEquipName(){
			return equipName;
		}
		public void setEquipName(String equipName){
			this.equipName = equipName;
		}
		public String getStatus(){
			return status;
		}
		public void setStatus(String status){
			this.status = status;
		}
}