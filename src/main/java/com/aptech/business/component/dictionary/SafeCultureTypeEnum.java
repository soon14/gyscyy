package com.aptech.business.component.dictionary;

import com.aptech.common.system.dictionary.domain.BaseCodeEnum;

public enum SafeCultureTypeEnum implements BaseCodeEnum {
	/**
	 * 季度检查
	 */
	QUARTERCHECK(0, "0", "季度安全文化"),
	/**
	 * 节假日检查
	 */
	HOLIDAYCHECK(1, "1", "节假日安全文化"),
	/**
	 * 专项检查
	 */
	SPECIALCHECK(2, "2", "专项安全文化");
	
	private Integer id;
	
	private String code;
	
	private String name;
	
	SafeCultureTypeEnum(Integer id, String code, String name){
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
