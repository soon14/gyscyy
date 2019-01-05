package com.aptech.business.component.dictionary;

import com.aptech.common.system.dictionary.domain.BaseCodeEnum;

public enum StockTypeEnum implements BaseCodeEnum {
	SCRAP(1,"1","报废库内"),
	UNSCRAP(2,"2","非报废"),
	SCRAPOUT(3,"3","报废库外");

	private Integer id;
	
	private String code;
	
	private String name;
	
	StockTypeEnum(Integer id, String code, String name){
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
