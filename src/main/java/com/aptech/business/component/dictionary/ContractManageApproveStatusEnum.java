package com.aptech.business.component.dictionary;

import com.aptech.common.system.dictionary.domain.BaseCodeEnum;

public enum ContractManageApproveStatusEnum implements BaseCodeEnum {
	
	PENDING(0, "0", "待提交"),//pending 
	REJECT(1, "1", "驳回"),//reject 
	TECH(2, "2", "待生产技术处审核"),//TECH 
	OPERATE(3, "3", "待计划经营处审核"),//OPERATE 
	LEADER(4, "4", "待单位领导讨论"),//LEADER 
	EXCUTE(5, "5", "待计划经营处下文执行"),//EXCUTE 
	END(6, "6", "审核通过");//END

	private int id;
	
	private String code;
	
	private String name;
	
	ContractManageApproveStatusEnum(Integer id, String code, String name){
		this.id = id;
		this.code = code;
		this.name = name;
	}

	public Integer getId() {
		return id;
	}


	public String getCode() {
		return code;
	}


	public String getName() {
		return name;
	}

	
	
}
