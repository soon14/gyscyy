package com.aptech.business.component.dictionary;

import com.aptech.common.system.dictionary.domain.BaseCodeEnum;

public enum SysManagementStatusEnum implements BaseCodeEnum{
	/**
	 * 有效
	 */
	YES(0, "0", "有效"),
	/**
	 * 无效
	 */
	NO(1, "1", "无效");
	private Integer id;
	
	private String code;
	
	private String name;
	
	SysManagementStatusEnum(Integer id, String code, String name){
		this.id = id;
		this.code = code;
		this.name = name;
	}
	@Override
	public Integer getId() {
		// TODO Auto-generated method stub
		return id;
	}

	@Override
	public String getCode() {
		// TODO Auto-generated method stub
		return code;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}

}
