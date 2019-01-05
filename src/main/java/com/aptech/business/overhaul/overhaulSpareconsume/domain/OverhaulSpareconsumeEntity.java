package com.aptech.business.overhaul.overhaulSpareconsume.domain;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
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
 * 备件消耗实体类
 *
 * @author 
 * @created 2018-03-15 10:23:12
 * @lastModified 
 * @history
 *
 */
@Alias("OverhaulSpareconsumeEntity")
public class OverhaulSpareconsumeEntity extends BaseEntity{
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
		 * 创建时间
		 */
    	private Date createDate;
		/**
		 * 创建人
		 */
    	private Long createUserId;
		/**
		 * 规格型号
		 */
    	private String model;
		/**
		 * 生产厂家
		 */
    	private String manufacturer;
		/**
		 * 单位
		 */
    	private String unit;
		/**
		 * 数量
		 */
    	private String number;
		/**
		 * 设备名称
		 */
    	private String equipName;
		/**
		 * 工作安排Id
		 */
    	private Long overhaulRecordId;
    	
		private String status;//状态
    	
    	private Long equipId;
    	
		public Long getEquipId() {
			return equipId;
		}
		public void setEquipId(Long equipId) {
			this.equipId = equipId;
		}
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
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
		public Long getOverhaulRecordId() {
			return overhaulRecordId;
		}
		public void setOverhaulRecordId(Long overhaulRecordId) {
			this.overhaulRecordId = overhaulRecordId;
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
		public String getModel(){
			return model;
		}
		public void setModel(String model){
			this.model = model;
		}
		//转换生产厂家
		public String getManufacturer(){
			return manufacturer;
		}
		public void setManufacturer(String manufacturer){
			this.manufacturer = manufacturer;
		}
		public String getUnit(){
			return unit;
		}
		public void setUnit(String unit){
			this.unit = unit;
		}
		public String getNumber(){
			return number;
		}
		public void setNumber(String number){
			this.number = number;
		}
		public String getEquipName(){
			return equipName;
		}
		public void setEquipName(String equipName){
			this.equipName = equipName;
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
		
}