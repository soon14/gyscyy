package com.aptech.business.component.dictionary;

import com.aptech.common.system.dictionary.domain.BaseCodeEnum;

public enum ThreeBusCategoryTypeEnum implements BaseCodeEnum {
	/**
	 * 职业健康
	 */
	 HEALTH(0, "0", "职业健康"),
	/**
	 * 节能减排energy 
	 */
	ENERGY(1, "1", "节能减排"),
	/**
	 * 环境保护environment
	 */
	ENVIRONMENT(2, "2", "环境保护");
	
	private Integer id;
	
	private String code;
	
	private String name;
	
	ThreeBusCategoryTypeEnum(Integer id, String code, String name){
		this.id = id;
		this.code = code;
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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
