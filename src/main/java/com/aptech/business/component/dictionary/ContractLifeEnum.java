package com.aptech.business.component.dictionary;

import com.aptech.common.system.dictionary.domain.BaseCodeEnum;

public enum ContractLifeEnum implements BaseCodeEnum{
	ONE(1,"1","1年"),
	TWO(2,"2","2年"),
	THREE(3,"3","3年"),
	FOUR(4,"4","4年"),
	FIVE(5,"5","5年"),
	SIX(6,"6","6年"),
	SEVEN(7,"7","7年"),
	EIGHT(8,"8","8年"),
	NINE(9,"9","9年"),
	TEN(10,"10","10年");

	private int id;
	
	private String code;
	
	private String name;
	
	ContractLifeEnum(Integer id, String code, String name){
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
