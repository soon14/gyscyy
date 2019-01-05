package com.aptech.business.component.dictionary;

import com.aptech.common.system.dictionary.domain.BaseCodeEnum;

public enum DefectStatusEnum implements BaseCodeEnum {
	PENDING(1, "1", "待提交"),//pending 
	OVERHAUL(3, "2", "待集控中心人员鉴定"),//overhaul 
	MONITOR(2, "3", "待检修中心人员鉴定"),//monitor 
	BIOTECH(5, "4", "待验收"),//biotech 
	ENGINEER(5, "5", "待总工、生产分管领导鉴定"),//engineer 
	IMPLEMENT(13, "6", "待执行人进行消缺"),//implement
	BIOTECH_APPROVE(5, "8", "挂起"),//biotechApprove 
	CHECK(14, "10", "待验收人验收"),//check 
	SOLVE(16, "11", "已消缺"),//solve 
	NODEFECT(17, "12", "非缺陷");//solve

	private Integer id;
	
	private String code;
	
	private String name;
	
	DefectStatusEnum(Integer id, String code, String name){
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
