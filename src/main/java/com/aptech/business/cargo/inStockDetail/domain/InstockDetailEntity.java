package com.aptech.business.cargo.inStockDetail.domain;

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
 * 入库物资明细实体类
 *
 * @author 
 * @created 2017-07-24 16:20:23
 * @lastModified 
 * @history
 *
 */
@Alias("InstockDetailEntity")
public class InstockDetailEntity extends BaseEntity{
		/**
	 * 
	 */
	private static final long serialVersionUID = 5545652317446050845L;
		/**
		 * 入库单
		 */
    	private Long instockId;
		/**
		 * 入库物资
		 */
    	private Long materialId;
		/**
		 * 入库物资数量
		 */
    	private String count;
    	/**
    	 * 货区id
    	 */
    	private String goodsAreaId;
    	
    	/**
    	 * 货区名称
    	 */
    	private String goodsAreaName;
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
    	/**
    	 * 货位id
    	 */
    	private String goodsAllocationId;
    	
    	/**
    	 * 货位名称
    	 */
    	private String goodsAllocationName;
    	
    	/**
    	 * 物资编码
    	 */
    	private String materialCode;
       	/**
    	 * 物资名称
    	 */
    	private String materialName;
       	/**
    	 * 仓库ID
    	 */
    	private Long wareHouseId;
       	/**
    	 * 仓库名称
    	 */
    	private String wareHouseName;
    	/**
    	 * 物资单位
    	 */
    	private String materialUnit;
    	/**
    	 * 物资单位名称
    	 */
    	private String materialUnitName;

    	/**
    	 * 物资型号
    	 */
    	private String materialModel;
    	/**
    	 * 物资生产厂家
    	 */
    	private String materialManufacturer;
    	/**
    	 * 入库类型
    	 * add  by  zhangxb
    	 */
    	private String materialType;
    	/**
    	 * 物资生产厂家code
    	 * add  by  zhangxb
    	 */
    	private String materialFacturer;
    	
    	private String management;
    	//入库明细总价
    	private String totalPrice;
    	//入库编号打印用
    	private int number;
    	
    	
    	
    	
		public int getNumber() {
			return number;
		}
		public void setNumber(int number) {
			this.number = number;
		}
		public String getTotalPrice() {
			return totalPrice;
		}
		public void setTotalPrice(String totalPrice) {
			this.totalPrice = totalPrice;
		}
		public Long getInstockId(){
			return instockId;
		}
		public void setInstockId(Long instockId){
			this.instockId = instockId;
		}
		public Long getMaterialId() {
			return materialId;
		}
		public void setMaterialId(Long materialId) {
			this.materialId = materialId;
		}
		
		
		public String getCount() {
			return count;
		}
		public void setCount(String count) {
			this.count = count;
		}
		public String getGoodsAreaId() {
			return goodsAreaId;
		}
		public void setGoodsAreaId(String goodsAreaId) {
			this.goodsAreaId = goodsAreaId;
		}

		public String getGoodsAllocationId() {
			return goodsAllocationId;
		}
		public void setGoodsAllocationId(String goodsAllocationId) {
			this.goodsAllocationId = goodsAllocationId;
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
		public Long getWareHouseId() {
			return wareHouseId;
		}
		public void setWareHouseId(Long wareHouseId) {
			this.wareHouseId = wareHouseId;
		}
		public String getWareHouseName() {
			return wareHouseName;
		}
		public void setWareHouseName(String wareHouseName) {
			this.wareHouseName = wareHouseName;
		}
		public String getMaterialUnit() {
			return materialUnit;
		}
		public void setMaterialUnit(String materialUnit) {
			this.materialUnit = materialUnit;
		}
		public String getMaterialModel() {
			return materialModel;
		}
		public void setMaterialModel(String materialModel) {
			this.materialModel = materialModel;
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
		public String getShowGoodsValidity() {
			if (goodsValidity != null) {
				DateFormatUtil dfu = DateFormatUtil.getInstance("yyyy-MM-dd");
				return dfu.format(this.goodsValidity);
			}
			return "";
		}
		public String getMaterialType() {
			return materialType;
		}
		public void setMaterialType(String materialType) {
			this.materialType = materialType;
		}
		public String getMaterialFacturer() {
			return materialFacturer;
		}
		public void setMaterialFacturer(String materialFacturer) {
			this.materialFacturer = materialFacturer;
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
		public String getManagement() {
			return management;
		}
		public void setManagement(String management) {
			this.management = management;
		}
		
}