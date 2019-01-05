package com.aptech.business.cargo.stock.domain;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.type.Alias;

import com.aptech.business.component.dictionary.IssetEnum;
import com.aptech.business.component.dictionary.ManagementTypeEnum;
import com.aptech.business.component.dictionary.StockTypeEnum;
import com.aptech.common.system.dictionary.util.DictionaryUtil;
import com.aptech.common.system.dictionary.web.SysDictionaryVO;
import com.aptech.framework.orm.BaseEntity;
import com.aptech.framework.util.DateFormatUtil;

/**
 * 
 * 库存管理实体类
 *
 * @author
 * @created 2017-07-17 16:40:59
 * @lastModified
 * @history
 *
 */
@Alias("StockEntity")
public class StockEntity extends BaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8492414438673913590L;
	/**
	 * 单位
	 */
	private Long unitId;
	/**
	 * 机构名称
	 */
	private String unitName;
	/**
	 * 物资类别
	 */
	private Long materialId;
	/**
	 * 库存数量
	 */
	private String inventoryQuantity;
	/**
	 * 标准上限
	 */
	private String upperLimit;
	/**
	 * 标准下限
	 */
	private String lowerLimit;
	/**
	 * 短缺数量
	 */
	private String shortage;
	/**
	 * 是否短缺
	 */
	private String isShortage;
	/**
	 * 物资编码
	 */
	private String materialCode;
	/**
	 * 物资名称
	 */
	private String materialName;
	/**
	 * 规格型号
	 */
	private String materialModel;
	/**
	 * 计数单位
	 */
	private String materialUnit;

	/**
	 * 生产厂家
	 */
	private String materialManufacturer;
	/**
	 * 管理方式
	 */
	private String materialManagement;
	/**
	 * 仓库ID
	 */
	private Long wareHouseId;
	/**
	 * 仓库名称
	 */
	private String wareHouseName;
	/**
	 * 物资价格
	 */
	private String goodsPrice;
	/**
	 * 物资有效期
	 */
	private Date goodsValidity;
	/**
	 * 物资属性
	 */
	private String goodsAttribute;
	private String type;
	private String typeName;
	private String scrapLibraryId;
	//物资类别
	private String materialTypeName;
	private String spareconsumeNum;//备件消耗数量
	
	
	
	
	public String getSpareconsumeNum() {
		return spareconsumeNum;
	}

	public void setSpareconsumeNum(String spareconsumeNum) {
		this.spareconsumeNum = spareconsumeNum;
	}

	public String getMaterialTypeName() {
		return materialTypeName;
	}

	public void setMaterialTypeName(String materialTypeName) {
		this.materialTypeName = materialTypeName;
	}

	public String getScrapLibraryId() {
		return scrapLibraryId;
	}

	public void setScrapLibraryId(String scrapLibraryId) {
		this.scrapLibraryId = scrapLibraryId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTypeName() {
		if (!StringUtils.isEmpty(this.type)) {
			Map<String, String> isSetEnumMap = new HashMap<String, String>();
			for (StockTypeEnum issetEnum : StockTypeEnum.values()) {
				isSetEnumMap.put(issetEnum.getCode(), issetEnum.getName());
			}
			return isSetEnumMap.get(this.type);
		}
		return null;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
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

	public Long getUnitId() {
		return unitId;
	}

	public void setUnitId(Long unitId) {
		this.unitId = unitId;
	}

	public Long getMaterialId() {
		return materialId;
	}

	public void setMaterialId(Long materialId) {
		this.materialId = materialId;
	}

	public String getInventoryQuantity() {
		return inventoryQuantity;
	}

	public void setInventoryQuantity(String inventoryQuantity) {
		this.inventoryQuantity = inventoryQuantity;
	}

	public String getUpperLimit() {
		return upperLimit;
	}

	public void setUpperLimit(String upperLimit) {
		this.upperLimit = upperLimit;
	}

	public String getLowerLimit() {
		return lowerLimit;
	}

	public void setLowerLimit(String lowerLimit) {
		this.lowerLimit = lowerLimit;
	}

	public String getShortage() {
		return shortage;
	}

	public void setShortage(String shortage) {
		this.shortage = shortage;
	}

	public String getIsShortage() {
		return isShortage;
	}

	public void setIsShortage(String isShortage) {
		this.isShortage = isShortage;
	}

	public String getIsShortageName() {
		if (!StringUtils.isEmpty(this.isShortage)) {
			Map<String, String> isSetEnumMap = new HashMap<String, String>();
			for (IssetEnum issetEnum : IssetEnum.values()) {
				isSetEnumMap.put(issetEnum.getCode(), issetEnum.getName());
			}
			return isSetEnumMap.get(this.isShortage);
		}
		return null;
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

	public String getMaterialUnit() {
		return materialUnit;
	}

	public void setMaterialUnit(String materialUnit) {
		this.materialUnit = materialUnit;
	}

	public String getMaterialManufacturer() {
		return materialManufacturer;
	}

	public void setMaterialManufacturer(String materialManufacturer) {
		this.materialManufacturer = materialManufacturer;
	}

	public String getMaterialManagement() {
		return materialManagement;
	}

	public void setMaterialManagement(String materialManagement) {
		this.materialManagement = materialManagement;
	}

	public String getManagementTypeName() {
		if (!StringUtils.isEmpty(this.materialManagement)) {
			Map<String, String> managementTypeEnumMap = new HashMap<String, String>();
			for (ManagementTypeEnum managementEnum : ManagementTypeEnum
					.values()) {
				managementTypeEnumMap.put(managementEnum.getCode(),
						managementEnum.getName());
			}
			return managementTypeEnumMap.get(this.materialManagement);
		}
		return null;
	}

	// 转换生产厂家名称
	public String getManufacturerName() {
		if (!StringUtils.isEmpty(this.materialManufacturer)) {
			Map<String, SysDictionaryVO> manufacturerMap = DictionaryUtil
					.getDictionaries("PRODUCTION_FACTORY");
			Map<String, String> manufacturerEnumMap = new HashMap<String, String>();
			for (String key : manufacturerMap.keySet()) {
				SysDictionaryVO sysDictionaryVO = manufacturerMap.get(key);
				manufacturerEnumMap.put(sysDictionaryVO.getCode(),
						sysDictionaryVO.getName());
			}
			return manufacturerEnumMap.get(this.materialManufacturer);
		}
		return null;
	}

	// 转换计数个数
	public String getMaterialUnitName() {
		if (!StringUtils.isEmpty(this.materialUnit)) {
			Map<String, SysDictionaryVO> unitMap = DictionaryUtil
					.getDictionaries("DIGIT");
			Map<String, String> unitEnumMap = new HashMap<String, String>();
			for (String key : unitMap.keySet()) {
				SysDictionaryVO sysDictionaryVO = unitMap.get(key);
				unitEnumMap.put(sysDictionaryVO.getCode(),
						sysDictionaryVO.getName());
			}
			return unitEnumMap.get(this.materialUnit);
		}
		return null;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
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

	public String getGoodsAttribute() {
		return goodsAttribute;
	}

	public void setGoodsAttribute(String goodsAttribute) {
		this.goodsAttribute = goodsAttribute;
	}
}