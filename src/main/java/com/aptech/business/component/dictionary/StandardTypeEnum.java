package com.aptech.business.component.dictionary;

import com.aptech.common.system.dictionary.domain.BaseCodeEnum;

public enum StandardTypeEnum implements BaseCodeEnum {
	TYPE1(1, "1", "作业危险点"),
	TYPE2(2, "2", "检修文件包"),
	TYPE3(3, "3", "规程规范"),
	TYPE4(4, "4", "应急预案");
	


	private Integer id;
	
	private String code;
	
	private String name;
	
	StandardTypeEnum(Integer id, String code, String name){
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
