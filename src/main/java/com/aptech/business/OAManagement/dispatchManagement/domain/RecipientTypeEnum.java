package com.aptech.business.OAManagement.dispatchManagement.domain;

import com.aptech.common.system.dictionary.domain.BaseCodeEnum;

/**
 * 密级
 * @author Administrator
 *
 */
public enum RecipientTypeEnum implements BaseCodeEnum {
	DENSE_LEVEL_NO(1, "1", "无"),
	DENSE_LEVEL_COMMON(2, "2", "普通商业秘密"),
	URGENCY_LEVEL_CORE(3, "3", "核心商业秘密");
	

	private Integer id;
	
	private String code;
	
	private String name;
	
	RecipientTypeEnum(Integer id, String code, String name){
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
