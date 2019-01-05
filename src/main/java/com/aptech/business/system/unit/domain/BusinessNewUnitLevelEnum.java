package com.aptech.business.system.unit.domain;

public enum BusinessNewUnitLevelEnum {
	
	GROUP("0", "集团"),
	AREA("1", "区域"),
	COMPANY("2", "公司"),
	DEPARTMENT("3", "部门");

	private String code;
	
	private String name = null;
	
	BusinessNewUnitLevelEnum(String code, String name){
		this.code = code;
		this.name = name;
	}
	
	public String getCode(){
		return code;
	}
	
	public String getName(){
		return name;
	}
}
