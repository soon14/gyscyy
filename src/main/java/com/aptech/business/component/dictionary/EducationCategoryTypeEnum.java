package com.aptech.business.component.dictionary;

import com.aptech.common.system.dictionary.domain.BaseCodeEnum;

public enum EducationCategoryTypeEnum implements BaseCodeEnum {
	/**
	 * 院内培训
	 */
	INHOME(0, "0", "院内培训"),
	/**
	 * 请进来/走出去
	 */
	COMEIN(1, "1", "请进来/走出去"),
	/**
	 * 取证/复审
	 */
	QUZHENG(2, "2", "取证/复审");
	
	private Integer id;
	
	private String code;
	
	private String name;
	
	EducationCategoryTypeEnum(Integer id, String code, String name){
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
