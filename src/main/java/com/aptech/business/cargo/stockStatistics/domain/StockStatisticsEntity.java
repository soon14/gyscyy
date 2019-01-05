package com.aptech.business.cargo.stockStatistics.domain;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.type.Alias;

import com.aptech.common.system.dictionary.util.DictionaryUtil;
import com.aptech.common.system.dictionary.web.SysDictionaryVO;
import com.aptech.framework.orm.BaseEntity;

/**
 * 
 * 运行检查实体类
 *
 * @author
 * @created 2017-09-08 15:00:42
 * @lastModified
 * @history
 *
 */
@Alias("StockStatisticsEntity")
public class StockStatisticsEntity extends BaseEntity {
	
	/**
	 * 总数
	 */
	private String totalCount;
	/**
	 * 所属部门id
	 */
	private String unitId;
	/**
	 * 所属部门
	 */
	private String unitName;
	
	/**
	 * 所属仓库
	 */
	private String wareHouseName;
	/**
	 * 所属仓库id
	 */
	private String wareHouseId;
	/**
	 * 材料名称
	 */
	private String materialName;
	/**
	 * 材料名称
	 */
	private String materialModel;
	
	/**
	 * 材料id
	 */
	private String materialId;
	
	/**
	 * 本月出库数
	 */
	private String mouthOutNum;
	
	/**
	 * 本月初始数量
	 */
	private String initialNum;
	
	/**
	 * 本月入库数
	 */
	private String mouthInNum;
	
	/**
	 * 当前数量
	 */
	private String nowNum;
	/**
	 * 本月前入库数
	 */
	private String beforeInNum;
	/**
	 * 本月前出库数
	 */
	private String beforeOutNum;
	/**
	 * 物资单位
	 */
	private String materialUnit;
	/**
	 * 物资单位中文名称
	 */
	private String materialUnitName;
	/**
	 * 查询月份
	 */
	private Date giveDate;

	public String getWareHouseName() {
		return wareHouseName;
	}

	public void setWareHouseName(String wareHouseName) {
		this.wareHouseName = wareHouseName;
	}

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public String getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(String totalCount) {
		this.totalCount = totalCount;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getWareHouseId() {
		return wareHouseId;
	}

	public void setWareHouseId(String wareHouseId) {
		this.wareHouseId = wareHouseId;
	}

	public String getMaterialName() {
		return materialName;
	}

	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}

	public String getMaterialId() {
		return materialId;
	}

	public void setMaterialId(String materialId) {
		this.materialId = materialId;
	}

	public String getMaterialModel() {
		return materialModel;
	}

	public void setMaterialModel(String materialModel) {
		this.materialModel = materialModel;
	}

	public String getMouthOutNum() {
		return mouthOutNum;
	}

	public void setMouthOutNum(String mouthOutNum) {
		this.mouthOutNum = mouthOutNum;
	}

	public String getInitialNum() {
		return initialNum;
	}

	public void setInitialNum(String initialNum) {
		this.initialNum = initialNum;
	}

	public String getMouthInNum() {
		return mouthInNum;
	}

	public void setMouthInNum(String mouthInNum) {
		this.mouthInNum = mouthInNum;
	}

	public String getNowNum() {
		return nowNum;
	}

	public void setNowNum(String nowNum) {
		this.nowNum = nowNum;
	}

	public Date getGiveDate() {
		return giveDate;
	}

	public void setGiveDate(Date giveDate) {
		this.giveDate = giveDate;
	}

	public String getBeforeInNum() {
		return beforeInNum;
	}

	public void setBeforeInNum(String beforeInNum) {
		this.beforeInNum = beforeInNum;
	}

	public String getBeforeOutNum() {
		return beforeOutNum;
	}

	public void setBeforeOutNum(String beforeOutNum) {
		this.beforeOutNum = beforeOutNum;
	}

	public String getMaterialUnit() {
		return materialUnit;
	}

	public void setMaterialUnit(String materialUnit) {
		this.materialUnit = materialUnit;
	}
	
	// 计数单位
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

	public void setMaterialUnitName(String materialUnitName) {
		this.materialUnitName = materialUnitName;
	}

}