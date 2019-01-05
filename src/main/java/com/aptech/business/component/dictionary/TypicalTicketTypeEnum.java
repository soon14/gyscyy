package com.aptech.business.component.dictionary;

import com.aptech.common.system.dictionary.domain.BaseCodeEnum;

public enum TypicalTicketTypeEnum implements BaseCodeEnum {
	
//	WORKTICKETONE(1, "1", "标准电气第一种工作票"),
//	WORKTICKETTWO(2, "2", "标准电气第二种工作票"),
	OPERATIONTICKET(3, "3", "标准操作票");
	private Integer id;
	
	private String code;
	
	private String name;
	
	TypicalTicketTypeEnum(Integer id, String code, String name){
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
