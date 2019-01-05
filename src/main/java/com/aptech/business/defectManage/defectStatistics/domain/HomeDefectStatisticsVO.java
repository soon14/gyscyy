package com.aptech.business.defectManage.defectStatistics.domain;

import java.math.BigDecimal;

public class HomeDefectStatisticsVO {

	private String unitId;
	
	private String unitName;
	
	private int totalNum = 0;
	
	private int solveNum = 0;

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public int getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(int totalNum) {
		this.totalNum = totalNum;
	}

	public int getSolveNum() {
		return solveNum;
	}

	public void setSolveNum(int solveNum) {
		this.solveNum = solveNum;
	}
	
	public BigDecimal getSolveScale(){
		if(totalNum == 0){
			return new BigDecimal(0);
		}
		return new BigDecimal(solveNum).multiply(new BigDecimal(100))
				.divide(new BigDecimal(totalNum),2,BigDecimal.ROUND_HALF_UP);//消除比例
	}
	
	
}
