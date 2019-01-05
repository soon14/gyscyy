package com.aptech.business.component.dictionary;

import com.aptech.common.system.dictionary.domain.BaseCodeEnum;

public enum UpperFileTypeEnum implements BaseCodeEnum {
	TYPE1(1, "1", "集团公司"),
	TYPE2(2, "2", "贵阳院"),
	TYPE3(3, "3", "监管单位");
	
	


	private Integer id;
	
	private String code;
	
	private String name;
	
	UpperFileTypeEnum(Integer id, String code, String name){
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
