package com.aptech.business.component.dictionary;

import com.aptech.common.system.dictionary.domain.BaseCodeEnum;

public enum ScienceTechnologyPlanStatusEnum implements BaseCodeEnum{
	TOBESUBMIT(0,"0","待提交"),
	TECHNICALAPPROVE(1,"1","待生产技术处审核"),
	PLANAPPROVE(2,"2","待计划经营处审核"),
	MANAGE(3,"3","待单位领导讨论"),
	EXCUTE(4,"4","待计划经营处下文执行"),
	END(5,"5","已执行"),
	CANCAL(6,"6","作废"),
	REJECT(7,"7","驳回到生产运行单位");
	
	private int id;
	
	private String code;
	
	private String name;
	
	ScienceTechnologyPlanStatusEnum(Integer id, String code, String name){
		this.id = id;
		this.code = code;
		this.name = name;
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
