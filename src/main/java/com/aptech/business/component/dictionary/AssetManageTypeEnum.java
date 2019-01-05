package com.aptech.business.component.dictionary;

import com.aptech.common.system.dictionary.domain.BaseCodeEnum;

public enum AssetManageTypeEnum implements BaseCodeEnum {
	TYPE1(1, "1", "总部"),
	TYPE2(2, "2", "生产单位");
	
	


	private Integer id;
	
	private String code;
	
	private String name;
	
	AssetManageTypeEnum(Integer id, String code, String name){
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
