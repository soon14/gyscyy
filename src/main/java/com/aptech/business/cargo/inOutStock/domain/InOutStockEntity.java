package com.aptech.business.cargo.inOutStock.domain;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.type.Alias;

import com.aptech.business.component.dictionary.ManagementTypeEnum;
import com.aptech.common.system.dictionary.util.DictionaryUtil;
import com.aptech.common.system.dictionary.web.SysDictionaryVO;
import com.aptech.framework.json.JsonDateDeserializer;
import com.aptech.framework.json.JsonDateSerializer;
import com.aptech.framework.orm.BaseEntity;
import com.aptech.framework.util.DateFormatUtil;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * 
 * 出入库明细实体类
 *
 * @author
 * @created 2017-07-15 16:14:14
 * @lastModified
 * @history
 *
 */
@Alias("InOutStockEntity")
public class InOutStockEntity extends BaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6848351897766017190L;
	/**
	 * 单据编号
	 */
	private String code;
	/**
	 * 组织机构ID
	 */
	private Long unitId;
	
	/**
	 * 组织机构名称
	 */
	private String unitName;
	/**
	 * 单据时间
	 */
	private Date time;
	/**
	 * 单据类型
	 */
	private String type;
	/**
	 * 物资类别
	 */
	private Long materialId;
	/**
	 * 数量
	 */
	private String count;

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
	//物资类别
	private String materialTypeName;
	
	
	
	public String getMaterialTypeName() {
		return materialTypeName;
	}

	public void setMaterialTypeName(String materialTypeName) {
		this.materialTypeName = materialTypeName;
	}

	public String getWareHouseName() {
		return wareHouseName;
	}

	public void setWareHouseName(String wareHouseName) {
		this.wareHouseName = wareHouseName;
	}

	private String timeString;

	private Long wareHouseId;
	
	private String wareHouseName;

	public Long getWareHouseId() {
		return wareHouseId;
	}

	public void setWareHouseId(Long wareHouseId) {
		this.wareHouseId = wareHouseId;
	}

	public String getTimeString() {
		return timeString;
	}

	public void setTimeString(String timeString) {
		this.timeString = timeString;
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

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	@JsonSerialize(using = JsonDateSerializer.class)
	public Date getTime() {
		return time;
	}

	@JsonDeserialize(using = JsonDateDeserializer.class)
	public void setTime(Date time) {
		this.time = time;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

	public String getTypeName() {
		if (!StringUtils.isEmpty(this.type)) {
			Map<String, SysDictionaryVO> billTypeMap = DictionaryUtil
					.getDictionaries("BILL_TYPE");
			Map<String, String> TypeEnumMap = new HashMap<String, String>();
			for (String key : billTypeMap.keySet()) {
				SysDictionaryVO sysDictionaryVO = billTypeMap.get(key);
				TypeEnumMap.put(sysDictionaryVO.getCode(),
						sysDictionaryVO.getName());
			}
			return TypeEnumMap.get(this.type);
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

	// 转换管理方式
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
	public String getShowTime() {
		if (time != null) {
			DateFormatUtil dfu = DateFormatUtil.getInstance("yyyy-MM-dd");
			return dfu.format(this.time);
		}
		return "";
	}

}