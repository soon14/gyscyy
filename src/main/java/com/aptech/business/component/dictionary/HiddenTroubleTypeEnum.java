package com.aptech.business.component.dictionary;

import com.aptech.common.system.dictionary.domain.BaseCodeEnum;

public enum HiddenTroubleTypeEnum implements BaseCodeEnum {
	TYPE1(1, "1", "上级排查"),
	TYPE2(2, "2", "生产单位"),
	TYPE3(3, "3", "存在隐患及排查情况");
	
	


	private Integer id;
	
	private String code;
	
	private String name;
	
	HiddenTroubleTypeEnum(Integer id, String code, String name){
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
