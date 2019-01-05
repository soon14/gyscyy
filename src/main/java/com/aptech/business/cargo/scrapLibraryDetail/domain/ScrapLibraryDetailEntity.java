package com.aptech.business.cargo.scrapLibraryDetail.domain;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.type.Alias;

import com.aptech.common.system.dictionary.util.DictionaryUtil;
import com.aptech.common.system.dictionary.web.SysDictionaryVO;
import com.aptech.framework.orm.BaseEntity;
import com.aptech.framework.util.DateFormatUtil;

/**
 * 
 * 报废库明细实体类
 *
 * @author 
 * @created 2018-03-16 19:29:22
 * @lastModified 
 * @history
 *
 */
@Alias("ScrapLibraryDetailEntity")
public class ScrapLibraryDetailEntity extends BaseEntity{
		/**
		 * 主键
		 */
    	private Long id;
		/**
		 * 入库单
		 */
    	private Long instockId;
		/**
		 * 入库物资
		 */
    	private Long metrialId;
		/**
		 * 入库物资数量
		 */
    	private String count;
    	private String materialCode;
    	private String materialName;
    	private String materialModel;
    	private String goodsAreaId;
    	private String materialManufacturer;
    	private String materialUnitName;
    	private String goodsAllocationId;
    	private String goodsAreaName;
    	private String goodsAllocationName;
    	/**
    	 * 物资属性
    	 * add  by  zhangxb
    	 */
    	private Long unitId;
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
    	
    	private String showGoodsValidity;
    	
    	private double totalPrice;
    	
    	
		public double getTotalPrice() {
			return totalPrice;
		}
		public void setTotalPrice(double totalPrice) {
			this.totalPrice = totalPrice;
		}
		public Long getId(){
			return id;
		}
		public void setId(Long id){
			this.id = id;
		}
		public Long getInstockId(){
			return instockId;
		}
		public void setInstockId(Long instockId){
			this.instockId = instockId;
		}
		public Long getMetrialId(){
			return metrialId;
		}
		public void setMetrialId(Long metrialId){
			this.metrialId = metrialId;
		}
		public String getCount(){
			return count;
		}
		public void setCount(String count){
			this.count = count;
		}
		public String getMaterialCode() {
			return materialCode;
		}
		public void setMaterialCode(String materialCode) {
			this.materialCode = materialCode;
		}
		public String getMaterialName() {
			return materialName;
		}
		public void setMaterialName(String materialName) {
			this.materialName = materialName;
		}
		public String getMaterialModel() {
			return materialModel;
		}
		public void setMaterialModel(String materialModel) {
			this.materialModel = materialModel;
		}
		public String getGoodsAreaId() {
			return goodsAreaId;
		}
		public void setGoodsAreaId(String goodsAreaId) {
			this.goodsAreaId = goodsAreaId;
		}
		public String getMaterialManufacturer() {
			return materialManufacturer;
		}
		public void setMaterialManufacturer(String materialManufacturer) {
			this.materialManufacturer = materialManufacturer;
		}
		public String getMaterialUnitName() {
			return materialUnitName;
		}
		public void setMaterialUnitName(String materialUnitName) {
			this.materialUnitName = materialUnitName;
		}
		public String getGoodsAllocationId() {
			return goodsAllocationId;
		}
		public void setGoodsAllocationId(String goodsAllocationId) {
			this.goodsAllocationId = goodsAllocationId;
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
		public String getGoodsAreaName() {
			return goodsAreaName;
		}
		public void setGoodsAreaName(String goodsAreaName) {
			this.goodsAreaName = goodsAreaName;
		}
		public String getGoodsAllocationName() {
			return goodsAllocationName;
		}
		public void setGoodsAllocationName(String goodsAllocationName) {
			this.goodsAllocationName = goodsAllocationName;
		}
		public String getShowGoodsValidity() {
			if (goodsValidity != null) {
				DateFormatUtil dfu = DateFormatUtil.getInstance("yyyy-MM-dd");
				return dfu.format(this.goodsValidity);
			}
			return "";
		}
		public void setShowGoodsValidity(String showGoodsValidity) {
			this.showGoodsValidity = showGoodsValidity;
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
		public Long getUnitId() {
			return unitId;
		}
		public void setUnitId(Long unitId) {
			this.unitId = unitId;
		}
}