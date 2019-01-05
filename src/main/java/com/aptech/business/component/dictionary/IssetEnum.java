package com.aptech.business.component.dictionary;

import com.aptech.common.system.dictionary.domain.BaseCodeEnum;

public enum IssetEnum implements BaseCodeEnum {
	/**
	 * 是否被设置为典型票"1", "是"
	 */
	ISSETYES(1, "1", "是"),
	/**
	 * 是否被设置为典型票"0", "否"
	 */
	ISSETNO(0, "0", "否");

	private Integer id;
	
	private String code;
	
	private String name;
	
	IssetEnum(Integer id, String code, String name){
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
