/*	
 * Web 5.0 Copyright 2016 Aptech, Co.ltd. All rights reserved.
 * 		 
 * FileName: SysUnitEntity.java
 *
 */

package com.aptech.business.system.unit.domain;

import org.apache.ibatis.type.Alias;

import com.aptech.framework.orm.BaseEntity;

/** 
 * 系统管理组织机构实体类
 *
 * @author zhangjx
 * @created 2016年11月22日 下午3:09:45 
 * @lastModified 
 * @history
 * 
 */
@Alias("unit")
public class UnitEntity extends BaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 编码
	 */
	private String code;
	
	/**
	 * 名称
	 */
	private String name;
	
	/**
	 * 缩写
	 */
	private String nameAB;
	
	/**
	 * 父级ID
	 */
	private Long parentId;
	
	/**
	 * 顺序
	 */
	private Integer order;
	
	/**
	 * 父级CODE
	 */
	private String parentCode;
	
	/**
	 * 层级1-公司 2-场站 3-调度站 4-期次 5-线路 6-单机
	 */
	private String level;
	
	/**
	 * 机构属性
	 */
	private String region;
	
	/**
	 * 机构地点
	 */
	private String place;
	
	/**
	 * 场站属性
	 */
	private String stationRegion;
	
	/**
	 * 1风2光
	 */
	private String generationType;
	
	/**
	 * 设备数量
	 */
	private Integer equipmentCount;
	
	/**
	 * 机构容量
	 */
	private Double capacity;
	
	/**
	 * 机构状态 0无效 1有效
	 */
	private String status;
	
	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 展开组织职务树
	 */
	private String open;

	/**
	 * 删除标记
	 */
	private String deleteFlag;
	/**
	 * 组织机构类型
	 */
	private int organization;
	

	public int getOrganization() {
		return organization;
	}

	public void setOrganization(int organization) {
		this.organization = organization;
	}

	public String getDeleteFlag() {
		return deleteFlag;
	}
	
	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
	public String getOpen() {
		return open;
	}

	public void setOpen(String open) {
		this.open = open;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNameAB() {
		return nameAB;
	}

	public void setNameAB(String nameAB) {
		this.nameAB = nameAB;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public String getParentCode() {
		return parentCode;
	}

	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getStationRegion() {
		return stationRegion;
	}

	public void setStationRegion(String stationRegion) {
		this.stationRegion = stationRegion;
	}

	public String getGenerationType() {
		return generationType;
	}

	public void setGenerationType(String generationType) {
		this.generationType = generationType;
	}

	public Integer getEquipmentCount() {
		return equipmentCount;
	}

	public void setEquipmentCount(Integer equipmentCount) {
		this.equipmentCount = equipmentCount;
	}

	public Double getCapacity() {
		return capacity;
	}

	public void setCapacity(Double capacity) {
		this.capacity = capacity;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	
	
}
