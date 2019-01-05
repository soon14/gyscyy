package com.aptech.business.component.dictionary;

import com.aptech.common.system.dictionary.domain.BaseCodeEnum;

public enum PlanStatusEnum implements BaseCodeEnum {
	REJECT(0, "0", "驳回"),//驳回
	PENDING(1, "1", "待提交"),//待提交
	OVERHAUL_DIRECTOR(2, "2", "待检修主任审核"),//待检修主任审核
	SPECIAL_WORK(3, "3", "待专工审核"),//待专工审核
	BIOTECH_DIRECTOR(4, "4", "待生技部主任审核"),//待生技部主任审核
	PLAN_DIRECTOR(5, "5", "待计划经营部主任审核"),//待计划经营部主任审核
	LEADER(6, "6", "待领导审核"),//待领导审核
	COMPLETE(7, "7", "计划审批完成"),//计划审批完成
	DELETE(8, "8", "计划删除");//计划删除
	


	private Integer id;
	
	private String code;
	
	private String name;
	
	PlanStatusEnum(Integer id, String code, String name){
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
