package com.aptech.business.cargo.materialCategory.domain;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.type.Alias;

import com.aptech.business.component.dictionary.ManagementTypeEnum;
import com.aptech.common.system.dictionary.util.DictionaryUtil;
import com.aptech.common.system.dictionary.web.SysDictionaryVO;
import com.aptech.framework.json.JsonDateTimeDeserializer;
import com.aptech.framework.json.JsonDateTimeSerializer;
import com.aptech.framework.orm.BaseEntity;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * 
 * 物资信息实体类
 *
 * @author 
 * @created 2017-07-14 11:59:28
 * @lastModified 
 * @history
 *
 */
@Alias("MaterialCategoryEntity")
public class MaterialCategoryEntity extends BaseEntity{
		/**
		 * 主键
		 */
    	private Long id;
		/**
		 * 物资名称
		 */
    	private String name;
		/**
		 * 物资编码
		 */
    	private String code;
		/**
		 * 规格型号
		 */
    	private String model;
		/**
		 * 计数单位
		 */
    	private String unit;
		/**
		 * 生产厂家
		 */
    	private String manufacturer;
		/**
		 * 管理方式
		 */
    	private String management;
    	/**
    	 * 物资种类     wareHouseId materialCategoryId goodsAreaId goodsAreaName goodsAllocationId goodsAllocationName
    	 */
    	private String materialType;
		/**
		 * 是否出入库
		 */
    	/**
    	 * 供应商id
    	 */
    	private Long supplierId;
    	private String supplierIdName;
    	
    	private String quote;
    	/**
    	 * 仓库id
    	 */
    	private Long wareHouseId;
    	/**
    	 * 物资id
    	 */
    	private String materialCategoryId;
    	/**
    	 * 货区id
    	 */
    	private Long goodsAreaId;
    	private String goodsAreaName;
    	/**
    	 * 货位id
    	 */
    	private Long goodsAllocationId;
    	private String goodsAllocationName;
    	/**
    	 * 物资属性
    	 * add  by  zhangxb
    	 */
    	private String goodsAttribute;
		/**
    	 * 有效期
    	 * add  by  zhangxb
    	 */
    	private Date goodsValidity;
    	/**
    	 * 货品价格
    	 * add  by  zhangxb
    	 */
    	private String goodsPrice;
    	/**
		 * 创建人id
		 */
    	private String createPeopleId;
    	/**
    	 * 物资数量
    	 */
    	private String inventoryCount;
    	private String stockId;
    	/**
    	 * 创建时间
    	 */
    	private Date createDate;
    	/**
    	 * 设备名称
    	 */
    	private String equipName;
    	/**
    	 * 设备名称
    	 */
    	private String equipCode;
    	//生产厂家打印
    	private String manufacturerName;
    	//备注
    	private String backUp;
    	
    	
    	
    	
		public String getBackUp() {
			return backUp;
		}
		public void setBackUp(String backUp) {
			this.backUp = backUp;
		}
		public void setManufacturerName(String manufacturerName) {
			this.manufacturerName = manufacturerName;
		}
		public String getEquipCode() {
			return equipCode;
		}
		public void setEquipCode(String equipCode) {
			this.equipCode = equipCode;
		}
		public String getEquipName() {
			return equipName;
		}
		public void setEquipName(String equipName) {
			this.equipName = equipName;
		}
		public String getStockId() {
			return stockId;
		}
		public void setStockId(String stockId) {
			this.stockId = stockId;
		}
		//    	public String getGoodsArea() {
//			return goodsArea;
//		}
//		public void setGoodsArea(String goodsArea) {
//			this.goodsArea = goodsArea;
//		}
//		public String getGoodsAllocation() {
//			return goodsAllocation;
//		}
//		public void setGoodsAllocation(String goodsAllocation) {
//			this.goodsAllocation = goodsAllocation;
//		}
//		private String goodsArea;
//    	
//    	private String goodsAllocation;
		@JsonSerialize(using = JsonDateTimeSerializer.class)
		public Date getCreateDate() {
			return createDate;
		}
		@JsonDeserialize(using = JsonDateTimeDeserializer.class)
		public void setCreateDate(Date createDate) {
			this.createDate = createDate;
		}
    	public String getSupplierIdName() {
			return supplierIdName;
		}
		public void setSupplierIdName(String supplierIdName) {
			this.supplierIdName = supplierIdName;
		}
		public Long getSupplierId() {
			return supplierId;
		}
		public void setSupplierId(Long supplierId) {
			this.supplierId = supplierId;
		}
		public Long getId(){
			return id;
		}
		public void setId(Long id){
			this.id = id;
		}
		public String getName(){
			return name;
		}
		public void setName(String name){
			this.name = name;
		}
		public String getCode(){
			return code;
		}
		public void setCode(String code){
			this.code = code;
		}
		public String getModel(){
			return model;
		}
		public void setModel(String model){
			this.model = model;
		}
		public String getUnit(){
			return unit;
		}
		public void setUnit(String unit){
			this.unit = unit;
		}
		public String getManufacturer(){
			return manufacturer;
		}
		public void setManufacturer(String manufacturer){
			this.manufacturer = manufacturer;
		}
		public String getManagement(){
			return management;
		}
		public void setManagement(String management){
			this.management = management;
		}
		public String getMaterialType() {
			return materialType;
		}
		public void setMaterialType(String materialType) {
			this.materialType = materialType;
		}
		public String getQuote(){
			return quote;
		}
		public void setQuote(String quote){
			this.quote = quote;
		}
		
		
		public Long getWareHouseId() {
			return wareHouseId;
		}
		public void setWareHouseId(Long wareHouseId) {
			this.wareHouseId = wareHouseId;
		}
		public String getMaterialCategoryId() {
			return materialCategoryId;
		}
		public void setMaterialCategoryId(String materialCategoryId) {
			this.materialCategoryId = materialCategoryId;
		}
		public Long getGoodsAreaId() {
			return goodsAreaId;
		}
		public void setGoodsAreaId(Long goodsAreaId) {
			this.goodsAreaId = goodsAreaId;
		}
		public String getGoodsAreaName() {
			return goodsAreaName;
		}
		public void setGoodsAreaName(String goodsAreaName) {
			this.goodsAreaName = goodsAreaName;
		}
		public Long getGoodsAllocationId() {
			return goodsAllocationId;
		}
		public void setGoodsAllocationId(Long goodsAllocationId) {
			this.goodsAllocationId = goodsAllocationId;
		}
		public String getGoodsAllocationName() {
			return goodsAllocationName;
		}
		public void setGoodsAllocationName(String goodsAllocationName) {
			this.goodsAllocationName = goodsAllocationName;
		}
		//转换生产厂家名称
		public String getManufacturerName(){
			if(!StringUtils.isEmpty(this.manufacturer)){
				Map<String, SysDictionaryVO> manufacturerMap  =  DictionaryUtil.getDictionaries("PRODUCTION_FACTORY");
				Map<String,String> manufacturerEnumMap = new HashMap<String, String>();
				for(String key : manufacturerMap.keySet()){
					SysDictionaryVO sysDictionaryVO = manufacturerMap.get(key);
					manufacturerEnumMap.put(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
				}
				return manufacturerEnumMap.get(this.manufacturer);
			}
			return null;
		}
		//转换管理方式
		public String getManagementTypeName(){
			if(!StringUtils.isEmpty(this.management)){
				Map<String,String> managementTypeEnumMap = new HashMap<String, String>();
				for(ManagementTypeEnum managementEnum : ManagementTypeEnum.values()){
					managementTypeEnumMap.put(managementEnum.getCode(), managementEnum.getName());
				}
				return managementTypeEnumMap.get(this.management);
			}
			return null;
		}
		//转换计数个数
		public String getUnitName(){
			if(!StringUtils.isEmpty(this.unit)){
				Map<String, SysDictionaryVO> unitMap  =  DictionaryUtil.getDictionaries("DIGIT");
				Map<String,String> unitEnumMap = new HashMap<String, String>();
				for(String key : unitMap.keySet()){
					SysDictionaryVO sysDictionaryVO = unitMap.get(key);
					unitEnumMap.put(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
				}
				return unitEnumMap.get(this.unit);
			}
			return null;
		}
		//转换计数个数
		public String getMaterialTypeName(){
			if(!StringUtils.isEmpty(this.materialType)){
				Map<String, SysDictionaryVO> materialTypeMap  =  DictionaryUtil.getDictionaries("MATERIAL_CATEGORY");
				Map<String,String> materialEnumMap = new HashMap<String, String>();
				for(String key : materialTypeMap.keySet()){
					SysDictionaryVO sysDictionaryVO = materialTypeMap.get(key);
					materialEnumMap.put(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
				}
				return materialEnumMap.get(this.materialType);
			}
			return null;
		}
		
		public String getGoodsAttribute() {
			return goodsAttribute;
		}
		public void setGoodsAttribute(String goodsAttribute) {
			this.goodsAttribute = goodsAttribute;
		}
		public Date getGoodsValidity() {
			return goodsValidity;
		}
		public void setGoodsValidity(Date goodsValidity) {
			this.goodsValidity = goodsValidity;
		}
		public String getGoodsPrice() {
			return goodsPrice;
		}
		public void setGoodsPrice(String goodsPrice) {
			this.goodsPrice = goodsPrice;
		}
		public String getShowGoodsAttribute() {
			if(!StringUtils.isEmpty(this.goodsAttribute)){
				Map<String, SysDictionaryVO> attributeMap  =  DictionaryUtil.getDictionaries("GOODS_ATTRIBUTE");
				Map<String,String> attributeEnumMap = new HashMap<String, String>();
				for(String key : attributeMap.keySet()){
					SysDictionaryVO sysDictionaryVO = attributeMap.get(key);
					attributeEnumMap.put(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
				}
				return attributeEnumMap.get(this.goodsAttribute);
			}
			return "";
		}
		public String getCreatePeopleId() {
			return createPeopleId;
		}
		public void setCreatePeopleId(String createPeopleId) {
			this.createPeopleId = createPeopleId;
		}
		public String getInventoryCount() {
			return inventoryCount;
		}
		public void setInventoryCount(String inventoryCount) {
			this.inventoryCount = inventoryCount;
		}
		
}