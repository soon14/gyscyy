package com.aptech.business.managePlanContract.goodsRelation.domain;

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
 * 物资关联实体类
 *
 * @author 
 * @created 2018-04-19 09:23:45
 * @lastModified 
 * @history
 *
 */
@Alias("GoodsRelationEntity")
public class GoodsRelationEntity extends BaseEntity{
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
		 * 设备名称
		 */
    	private String equipName;
		/**
		 * 设备类别
		 */
    	private String equipType;
		/**
		 * 型号规格
		 */
    	private String specification;
		/**
		 * 数量
		 */
    	private String amount;
		/**
		 * 预计单价
		 */
    	private String budgePrice;
		/**
		 * 预计总价
		 */
    	private String totalPrice;
		/**
		 * 项目名称
		 */
    	private String projectName;
		/**
		 * 物资采购Id
		 */
    	private Long goodsPurchaseId;
    	
    	
    	private Long yearPurchaseId;
    	
    	
    	private String unit;
    	
    	

		public String getUnit() {
			return unit;
		}
		public void setUnit(String unit) {
			this.unit = unit;
		}
		public Long getYearPurchaseId() {
			return yearPurchaseId;
		}
		public void setYearPurchaseId(Long yearPurchaseId) {
			this.yearPurchaseId = yearPurchaseId;
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
	    @JsonSerialize(using = JsonDateTimeSerializer.class)
		public Date getCreateDate(){
			return createDate;
		}
	    @JsonDeserialize(using = JsonDateTimeDeserializer.class)
		public void setCreateDate(Date createDate){
			this.createDate = createDate;
		}
		public String getEquipName(){
			return equipName;
		}
		public void setEquipName(String equipName){
			this.equipName = equipName;
		}
		public String getEquipType(){
			return equipType;
		}
		public void setEquipType(String equipType){
			this.equipType = equipType;
		}
		public String getSpecification(){
			return specification;
		}
		public void setSpecification(String specification){
			this.specification = specification;
		}
		public String getAmount(){
			return amount;
		}
		public void setAmount(String amount){
			this.amount = amount;
		}
		public String getBudgePrice(){
			return budgePrice;
		}
		public void setBudgePrice(String budgePrice){
			this.budgePrice = budgePrice;
		}
		public String getTotalPrice(){
			return totalPrice;
		}
		public void setTotalPrice(String totalPrice){
			this.totalPrice = totalPrice;
		}
		public String getProjectName(){
			return projectName;
		}
		public void setProjectName(String projectName){
			this.projectName = projectName;
		}
		public Long getGoodsPurchaseId(){
			return goodsPurchaseId;
		}
		public void setGoodsPurchaseId(Long goodsPurchaseId){
			this.goodsPurchaseId = goodsPurchaseId;
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
}