package com.aptech.business.system.unit.domain;

public enum BusinessUnitLevelEnum {
	
	GROUP("0", "集团"),
	COMPANY("1", "公司"),
	FARM("2", "场站"),
	STATION("3", "调度站"),
	PERIOD("4", "期次"),
	LINE("5", "线路"),
	MASHINE("6", "单机");

	private String code;
	
	private String name = null;
	
	BusinessUnitLevelEnum(String code, String name){
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
