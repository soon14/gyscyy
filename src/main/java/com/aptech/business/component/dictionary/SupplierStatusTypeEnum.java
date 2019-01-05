package com.aptech.business.component.dictionary;

import com.aptech.common.system.dictionary.domain.BaseCodeEnum;

public enum SupplierStatusTypeEnum implements BaseCodeEnum {
	/**
	 * 停用
	 */
	OPEN(0, "0", "启用"),
	/**
	 * 禁用
	 */
	STOP(1, "1", "禁用");

	
	private Integer id;
	
	private String code;
	
	private String name;
	
	SupplierStatusTypeEnum(Integer id, String code, String name){
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
