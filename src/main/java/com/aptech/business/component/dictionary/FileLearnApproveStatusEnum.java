package com.aptech.business.component.dictionary;

import com.aptech.common.system.dictionary.domain.BaseCodeEnum;

public enum FileLearnApproveStatusEnum implements BaseCodeEnum {
	
	PENDING(0, "0", "待提交"),//pending 
	REJECT(1, "1", "驳回"),//reject 
	LEADER(2, "2", "待领导审批"),//storekeeper 
	PUBLISH(3, "3", "待发布"),//storekeeper 
	END(4, "4", "审核通过");//END

	private int id;
	
	private String code;
	
	private String name;
	
	FileLearnApproveStatusEnum(Integer id, String code, String name){
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
