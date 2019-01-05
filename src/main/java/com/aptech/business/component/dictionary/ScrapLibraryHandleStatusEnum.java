package com.aptech.business.component.dictionary;

import com.aptech.common.system.dictionary.domain.BaseCodeEnum;

public enum ScrapLibraryHandleStatusEnum implements BaseCodeEnum{
	PENDING(0, "0", "待提交"),//pending 
	REJECT(1, "1", "驳回到管理人员"),//reject ;
	TOBEMANAGE(2,"2","待审核"),
	TOBEPROFESSIONAL(3,"3","待专业人员修复"),
	TOBESTOREHOUSE(4,"4","待库管员确认"),
	TOBEBIOTECH(5,"5","待生技处审核"),
	TOBEPROMANAGE(6,"6","待生产副总审批"),
	TOBESTOREHOUSEDEAL(7,"7","待库管员处理"),
	TOBEGENERALMANAGE(8,"8","待综合管理员处理"),
	END(13,"13","报废"),
	DISCARDED(10, "10", "已丢弃"),//MANAGERAPPROVE
	SHOPED(11, "11", "已变卖"),//MANAGERAPPROVE
	REPAIRED(12,"12","已修复");
	
	
	
	private Integer id;
	private String code;
	private String name;
	ScrapLibraryHandleStatusEnum(Integer id,String code,String name){
		this.id=id;
		this.code=code;
		this.name=name;
	}
	@Override
	public String getCode() {
		// TODO Auto-generated method stub
		return code;
	}

	@Override
	public Integer getId() {
		// TODO Auto-generated method stub
		return id;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}

}
