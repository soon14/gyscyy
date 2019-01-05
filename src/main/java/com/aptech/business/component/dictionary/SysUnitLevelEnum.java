package com.aptech.business.component.dictionary;

import com.aptech.common.system.dictionary.domain.BaseCodeEnum;

public enum SysUnitLevelEnum implements BaseCodeEnum {
	LEVEL2(2, "2", "2");
	


	private Integer id;
	
	private String code;
	
	private String name;
	
	SysUnitLevelEnum(Integer id, String code, String name){
		this.id = id;
		this.code = code;
		this.name = name;
	}
	
	@Override
	public String getCode() {
		return code;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Integer getId() {
		return this.id;
	}

}
