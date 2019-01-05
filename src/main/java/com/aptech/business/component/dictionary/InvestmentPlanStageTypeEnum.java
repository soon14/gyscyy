package com.aptech.business.component.dictionary;

import com.aptech.common.system.dictionary.domain.BaseCodeEnum;

public enum InvestmentPlanStageTypeEnum implements BaseCodeEnum {
	/**
	 * 续建
	 */
	TYPE1(0, "0", "续建"),
	/**
	 * 新开工
	 */
	TYPE2(1, "1", "新开工");
	
	private Integer id;
	
	private String code;
	
	private String name;
	
	InvestmentPlanStageTypeEnum(Integer id, String code, String name){
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
