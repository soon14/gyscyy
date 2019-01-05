package com.aptech.business.defectManage.defectStatistics.domain;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.type.Alias;

import com.aptech.business.defectManage.defect.domain.DefectEntity;
import com.aptech.common.system.dictionary.domain.SysDictionaryEntity;
import com.aptech.framework.orm.BaseEntity;

/**
 * 
 * 缺陷统计实体类
 * 
 * @author
 * @created 2017-06-09 09:12:26
 * @lastModified
 * @history
 * 
 */
@Alias("defectStatisticsEntity")
public class DefectStatisticsEntity extends BaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4802396903618955275L;
	/**
	 * 主键
	 */
	private Long id;
	/**
	 * 缺陷类型比率
	 */
	private String scale;
	/**
	 * 消除比例
	 */
	private String solveScale;
	/**
	 * 未消除次数
	 */
	private String avoid;
	/**
	 * 消除次数
	 */
	private String solveNum;
	/**
	 * 挂起比例
	 */
	private String hangScale;
	/**
	 * 公司名称
	 */
	private String unitName;
	/**
	 * 发现次数
	 */
	private String findNum;
	/**
	 * 发现总数
	 */
	private String findSum;
	/**
	 * 统计月份
	 */
	private String statisticsMonth;
	/**
	 * 未消除比例
	 */
	private String avoidScale;
	/**
	 * 缺陷类型
	 */
	private String type;
	/**
	 * 缺陷类型
	 */
	private String typeName;
	/**
	 * 挂起次数
	 */
	private String hangNum;
	/**
	 * 单位id
	 */
	private String unitId;
	/**
	 * 合并数
	 */
	private String rowspanNum;
	/**
	 * 是否隐藏
	 */
	private String tdHide;
	/**
	 * 未消除比例
	 */
	private String avoidScaleAll;
	/**
	 * 缺陷类型比率
	 */
	private String scaleAll;
	/**
	 * 消除比例
	 */
	private String solveScaleAll;
	/**
	 * 未消除次数
	 */
	private String avoidAll;
	/**
	 * 消除次数
	 */
	private String solveNumAll;
	/**
	 * 挂起比例
	 */
	private String hangScaleAll;
	/**
	 * 挂起次数
	 */
	private String hangNumAll;
	/**
	 * 发现次数
	 */
	private String findNumAll;
	/**
	 * 发现总数
	 */
	private String findSumAll;
	/**
	 * 设备管理类型
	 */
	private String equiptypeName;
	
	
	private List equiptypeList= new ArrayList();
	
	private String rowspanNumber;
	
	/**
	 * 缺陷类型合并数
	 */
	private String typeRowspanNum;
	
	/**
	 * 缺陷类型显示
	 */
	private String typeRowShow;
	
	public String getRowspanNumber() {
		return rowspanNumber;
	}

	public void setRowspanNumber(String rowspanNumber) {
		this.rowspanNumber = rowspanNumber;
	}

	public List getEquiptypeList() {
		return equiptypeList;
	}

	public void setEquiptypeList(List equiptypeList) {
		this.equiptypeList = equiptypeList;
	}

	public String getEquiptypeName() {
		return equiptypeName;
	}

	public void setEquiptypeName(String equiptypeName) {
		this.equiptypeName = equiptypeName;
	}

	public String getAvoidScaleAll() {
		return avoidScaleAll;
	}

	public void setAvoidScaleAll(String avoidScaleAll) {
		this.avoidScaleAll = avoidScaleAll;
	}

	public String getScaleAll() {
		return scaleAll;
	}

	public void setScaleAll(String scaleAll) {
		this.scaleAll = scaleAll;
	}

	public String getSolveScaleAll() {
		return solveScaleAll;
	}

	public void setSolveScaleAll(String solveScaleAll) {
		this.solveScaleAll = solveScaleAll;
	}

	public String getAvoidAll() {
		return avoidAll;
	}

	public void setAvoidAll(String avoidAll) {
		this.avoidAll = avoidAll;
	}

	public String getSolveNumAll() {
		return solveNumAll;
	}

	public void setSolveNumAll(String solveNumAll) {
		this.solveNumAll = solveNumAll;
	}

	public String getHangScaleAll() {
		return hangScaleAll;
	}

	public void setHangScaleAll(String hangScaleAll) {
		this.hangScaleAll = hangScaleAll;
	}

	public String getHangNumAll() {
		return hangNumAll;
	}

	public void setHangNumAll(String hangNumAll) {
		this.hangNumAll = hangNumAll;
	}

	public String getFindNumAll() {
		return findNumAll;
	}

	public void setFindNumAll(String findNumAll) {
		this.findNumAll = findNumAll;
	}

	public String getFindSumAll() {
		return findSumAll;
	}

	public void setFindSumAll(String findSumAll) {
		this.findSumAll = findSumAll;
	}

	public String getRowspanNum() {
		return rowspanNum;
	}

	public void setRowspanNum(String rowspanNum) {
		this.rowspanNum = rowspanNum;
	}

	public String getTdHide() {
		return tdHide;
	}

	public void setTdHide(String tdHide) {
		this.tdHide = tdHide;
	}

	public String getFindSum() {
		return findSum;
	}

	public void setFindSum(String findSum) {
		this.findSum = findSum;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getScale() {
		return scale;
	}

	public void setScale(String scale) {
		this.scale = scale;
	}

	public String getSolveScale() {
		return solveScale;
	}

	public void setSolveScale(String solveScale) {
		this.solveScale = solveScale;
	}

	public String getAvoid() {
		return avoid;
	}

	public void setAvoid(String avoid) {
		this.avoid = avoid;
	}

	public String getSolveNum() {
		return solveNum;
	}

	public void setSolveNum(String solveNum) {
		this.solveNum = solveNum;
	}

	public String getHangScale() {
		return hangScale;
	}

	public void setHangScale(String hangScale) {
		this.hangScale = hangScale;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getFindNum() {
		return findNum;
	}

	public void setFindNum(String findNum) {
		this.findNum = findNum;
	}

	public String getStatisticsMonth() {
		return statisticsMonth;
	}

	public void setStatisticsMonth(String statisticsMonth) {
		this.statisticsMonth = statisticsMonth;
	}

	public String getAvoidScale() {
		return avoidScale;
	}

	public void setAvoidScale(String avoidScale) {
		this.avoidScale = avoidScale;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getHangNum() {
		return hangNum;
	}

	public void setHangNum(String hangNum) {
		this.hangNum = hangNum;
	}

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public String getTypeRowspanNum() {
		return typeRowspanNum;
	}

	public void setTypeRowspanNum(String typeRowspanNum) {
		this.typeRowspanNum = typeRowspanNum;
	}

	public String getTypeRowShow() {
		return typeRowShow;
	}

	public void setTypeRowShow(String typeRowShow) {
		this.typeRowShow = typeRowShow;
	}
	
}