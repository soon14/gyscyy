package com.aptech.business.component.dictionary;

import com.aptech.common.system.dictionary.domain.BaseCodeEnum;

public enum CompanyTrendsStatusEnum implements BaseCodeEnum {
//	REJECT(0, "0", "驳回"),
	PENDING(1, "1", "待提交"),
	GUARDIAN(2, "2", "已发布"),
	MONITOR(3, "3", "已完成"),
	FOREMANEXAMINE (4, "4", "待部门领导审核"),
	IMPLEMENT(5, "5", "部门领导驳回"),
//	FOREMANCONFIRM(6, "6", "待党群部审核"),
//	FOREMANREJECT(7, "7", "党群部驳回"),
	RESULTS(8, "8", "审核通过");
	private Integer id;
	
	private String code;
	
	private String name;
	
	CompanyTrendsStatusEnum(Integer id, String code, String name){
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
