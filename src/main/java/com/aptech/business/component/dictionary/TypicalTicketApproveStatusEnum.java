package com.aptech.business.component.dictionary;

import com.aptech.common.system.dictionary.domain.BaseCodeEnum;

public enum TypicalTicketApproveStatusEnum implements BaseCodeEnum {
	REJECT(0, "0", "驳回"),
	PENDING(1, "1", "待提交"),
	OVERHAUL(2, "2", "待专工审批"),
	OVERHAULAPPROVE(3, "3", "待检修运行主任审批"),
	BIOTECHAPPROVE(4, "4", "待生产HSE主任审批"),
	APPROVE(5, "5", "待分管领导审批"),
	RESULTS(6, "6", "已通过");
	private Integer id;
	
	private String code;
	
	private String name;
	
	TypicalTicketApproveStatusEnum(Integer id, String code, String name){
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
