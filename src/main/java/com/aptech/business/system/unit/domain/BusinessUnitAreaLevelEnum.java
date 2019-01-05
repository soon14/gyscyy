package com.aptech.business.system.unit.domain;

public enum BusinessUnitAreaLevelEnum {
	
	GROUP("0", "集团"),
	AREA("1", "区域"),
	DEPARTMENT("2", "部门");

	private String code;
	
	private String name = null;
	
	BusinessUnitAreaLevelEnum(String code, String name){
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
