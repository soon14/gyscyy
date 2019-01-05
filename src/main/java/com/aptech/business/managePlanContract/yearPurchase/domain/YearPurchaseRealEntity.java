package com.aptech.business.managePlanContract.yearPurchase.domain;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.aptech.common.system.dictionary.util.DictionaryUtil;
import com.aptech.common.system.dictionary.web.SysDictionaryVO;
import com.aptech.framework.json.JsonDateDeserializer;
import com.aptech.framework.json.JsonDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import org.apache.ibatis.type.Alias;

import com.aptech.framework.orm.BaseEntity;
import com.aptech.framework.util.DateFormatUtil;

/**
 * 
 * 年度采购计划实际填报实体类
 *
 * @author ly
 * @created 2018-09-04 13:38:00
 * @lastModified 
 * @history
 *
 */
@Alias("YearPurchaseRealEntity")
public class YearPurchaseRealEntity extends BaseEntity{
		/**
		 * 主键
		 */
    	private Long id;
		/**
		 * 年度采购计划id
		 */
    	private String yearPurchaseId;
		/**
		 * 设备名称
		 */
    	private String name;
		/**
		 * 设备类别
		 */
    	private String type;
		/**
		 * 规格型号
		 */
    	private String specification;
		/**
		 * 实际采购时间
		 */
    	private Date realBuyTime;
    	/**
    	 * 实际采购时间展示
    	 */
    	private String realBuyTimeStr;
		/**
		 * 数量
		 */
    	private String amount;
		/**
		 * 单价
		 */
    	private String budgetPrice;
		/**
		 * 总价
		 */
    	private String totalPrice;

		public Long getId(){
			return id;
		}
		public void setId(Long id){
			this.id = id;
		}
		public String getYearPurchaseId(){
			return yearPurchaseId;
		}
		public void setYearPurchaseId(String yearPurchaseId){
			this.yearPurchaseId = yearPurchaseId;
		}
		public String getName(){
			return name;
		}
		public void setName(String name){
			this.name = name;
		}
		public String getType(){
			return type;
		}
		public void setType(String type){
			this.type = type;
		}
		//设备类别中文名称
		public String getTypeName() {
			Map<String, SysDictionaryVO> typeMap  =  DictionaryUtil.getDictionaries("EQUIPTYPE");
			Map<String,String> typeEnumMap = new HashMap<String, String>();
			for(String key : typeMap.keySet()){
				SysDictionaryVO sysDictionaryVO = typeMap.get(key);
				typeEnumMap.put(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
			}
			return typeEnumMap.get(this.type);
		}
		public String getSpecification(){
			return specification;
		}
		public void setSpecification(String specification){
			this.specification = specification;
		}
	    @JsonSerialize(using = JsonDateSerializer.class)
		public Date getRealBuyTime(){
			return realBuyTime;
		}
		public String getRealBuyTimeStr() {
			
			if (realBuyTimeStr !=null && realBuyTimeStr !="") {
				return realBuyTimeStr;
			}
			DateFormatUtil df= DateFormatUtil.getInstance("yyyy-MM");
			if (this.realBuyTime==null) {
				return realBuyTimeStr;
			}
			return df.format(this.realBuyTime);
			
		}
	    @JsonDeserialize(using = JsonDateDeserializer.class)
		public void setRealBuyTime(Date realBuyTime){
			this.realBuyTime = realBuyTime;
		}
		public String getAmount(){
			return amount;
		}
		public void setAmount(String amount){
			this.amount = amount;
		}
		public String getBudgetPrice(){
			return budgetPrice;
		}
		public void setBudgetPrice(String budgetPrice){
			this.budgetPrice = budgetPrice;
		}
		public String getTotalPrice(){
			return totalPrice;
		}
		public void setTotalPrice(String totalPrice){
			this.totalPrice = totalPrice;
		}
}