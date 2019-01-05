package com.aptech.business.component.dictionary;

import com.aptech.common.system.dictionary.domain.BaseCodeEnum;

public enum AnnualMaintenanceStatusEnum implements BaseCodeEnum{
	TOBESUBMIT(1,"1","待提交");
	
	
	private Integer id;
	private String code;
	private String name;
	AnnualMaintenanceStatusEnum(Integer id,String code,String name){
		this.id=id;
		this.code=code;
		this.name=name;
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
