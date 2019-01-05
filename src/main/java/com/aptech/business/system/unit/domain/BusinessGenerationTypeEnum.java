package com.aptech.business.system.unit.domain;

public enum BusinessGenerationTypeEnum {
	FIRE("0", "火"),
	WIND("1","风"),
	LIGHT("2","光"),
	WIND_LIGHT("3", "风光"),
	WATER("4", "水");
	
	private String code;
	
	private String name;

	BusinessGenerationTypeEnum(String code, String name){
		this.code = code;
		this.name = name;
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
	
	
}
