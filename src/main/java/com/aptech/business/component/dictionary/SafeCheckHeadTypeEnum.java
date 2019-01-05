package com.aptech.business.component.dictionary;

import com.aptech.common.system.dictionary.domain.BaseCodeEnum;

public enum SafeCheckHeadTypeEnum implements BaseCodeEnum {
	/**
	 * 月度检查
	 */
	QUARTERCHECK(0, "0", "月度检查"),
	/**
	 * 节假日检查
	 */
	HOLIDAYCHECK(1, "1", "节假日检查"),
	/**
	 * 专项检查
	 */
	SPECIALCHECK(2, "2", "专项检查"),
	/**
	 * 四不两直
	 */
	FOURNOTTWOSTRAIGHT(3,"3","四不两直"),
	/**
	 * 日常检查
	 */
	DAILYCHECK(4,"4","日常检查");
	
	private Integer id;
	
	private String code;
	
	private String name;
	
	SafeCheckHeadTypeEnum(Integer id, String code, String name){
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
