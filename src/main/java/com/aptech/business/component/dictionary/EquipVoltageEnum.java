package com.aptech.business.component.dictionary;

import com.aptech.common.system.dictionary.domain.BaseCodeEnum;

public enum EquipVoltageEnum implements BaseCodeEnum {
//	KV11(0, "0", "11kV"),
	KV35(1, "1", "35"),
	KV110(2, "2", "110"),
	V220(3, "4", "220V"),
	V380(4, "4", "380V"),
	V400(5, "5", "400V"),
	V620(6, "6", "620V"),
	V690(7, "7", "690V");



	private Integer id;
	
	private String code;
	
	private String name;
	
	EquipVoltageEnum(Integer id, String code, String name){
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
