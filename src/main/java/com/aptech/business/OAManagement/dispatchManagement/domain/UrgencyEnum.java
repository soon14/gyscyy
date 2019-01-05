package com.aptech.business.OAManagement.dispatchManagement.domain;

import com.aptech.common.system.dictionary.domain.BaseCodeEnum;

/**
 * 缓急程度
 * @author Administrator
 *
 */
public enum UrgencyEnum implements BaseCodeEnum {
	URGENCY_LEVEL_COMMON(1, "1", "普通"),
	URGENCY_LEVEL_URGENT(2, "2", "加急"),
	URGENCY_LEVEL_EXTRA_URGENT(3, "3", "特急");
	

	private Integer id;
	
	private String code;
	
	private String name;
	
	UrgencyEnum(Integer id, String code, String name){
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
