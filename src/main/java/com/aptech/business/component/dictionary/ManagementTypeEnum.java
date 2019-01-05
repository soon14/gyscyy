package com.aptech.business.component.dictionary;

import com.aptech.common.system.dictionary.domain.BaseCodeEnum;

public enum ManagementTypeEnum implements BaseCodeEnum {

	INSTANCE(1,"1","实例"),
	TALLY(2,"2","记账");
	
	
	private Integer id;
	
	private String name;
	
	private String code;
	
	ManagementTypeEnum(Integer id, String code, String name){
		this.id = id;
		this.code = code;
	    this.name = name;
	}
	

	@Override
	public Integer getId() {
		// TODO Auto-generated method stub
		return this.id;
	}

	@Override
	public String getCode() {
		// TODO Auto-generated method stub
		return this.code;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return this.name;
	}	
}
