package com.aptech.business.component.dictionary;

import com.aptech.common.system.dictionary.domain.BaseCodeEnum;

public enum OperationStatusEnum implements BaseCodeEnum {
	REJECT(0, "0", "驳回"),
	PENDING(1, "1", "待提交"),
	GUARDIAN(1, "2", "待监护人审核"),
	MONITOR(3, "3", "待值班负责人审核"),
	FOREMANEXAMINE (4, "4", "待值长审核"),
	IMPLEMENT(5, "5", "待操作人记录结果"),
	FOREMANCONFIRM(6, "6", "待值长确定"),
	FOREMANREJECT(7, "7", "值长驳回"),
	INVALID(8, "8", "废票"),
	RESULTS(9, "9", "已确定结果");
	private Integer id;
	
	private String code;
	
	private String name;
	
	OperationStatusEnum(Integer id, String code, String name){
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
