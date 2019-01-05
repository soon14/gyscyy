package com.aptech.business.component.dictionary;

import com.aptech.common.system.dictionary.domain.BaseCodeEnum;

public enum TechnicalBtnTypeEnum implements BaseCodeEnum{
	/**
	 * "1", "生技处审批"
	 */
	BIOTECH(1, "1", "生技处审批"),
	/**
	 * "2", "生技处主任审批"
	 */
	BIOTECHMANAGE(2, "2", "生技处主任审批"),
	/**
	 * "3", "上级领导审批"
	 */
	LEADER(3, "3", "上级领导审批"),
	/**
	 * "4", "驳回到检修专工"
	 */
	REJECT(4, "4", "驳回到检修专工");

	
	private Integer id;
	
	private String code;
	
	private String name;
	
	TechnicalBtnTypeEnum(Integer id, String code, String name){
		this.id = id;
		this.code = code;
		this.name = name;
	}
	
	@Override
	public String getCode() {
		// TODO Auto-generated method stub
		return code;
	}

	@Override
	public Integer getId() {
		// TODO Auto-generated method stub
		return id;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}

}
