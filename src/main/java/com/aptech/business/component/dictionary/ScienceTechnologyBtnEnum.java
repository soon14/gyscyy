package com.aptech.business.component.dictionary;

import com.aptech.common.system.dictionary.domain.BaseCodeEnum;

public enum ScienceTechnologyBtnEnum implements BaseCodeEnum{
	/**
	 * "1", "生产技术处审核"
	 */
	DWFZR(1, "1", "生产技术处审核"),
	/**
	 * "2", "计划经营处审核"
	 */
	ZGFZR(2,"2","计划经营处审核"),
	/**
	 * "3", "单位领导讨论"
	 */
	AQJCB(3, "3", "单位领导讨论"),
	/**
	 * "4", "计划经营处下文执行"
	 */
	FZJL(4, "4", "计划经营处下文执行"),
	/**
	 * 6", "作废"
	 */
	ZF(6, "6", "作废"),
	/**
	 * "7", "再提交
	 */
	ZTJ(7, "7", "再提交");

	private Integer id;
	
	private String code;
	
	private String name;
	
	ScienceTechnologyBtnEnum(Integer id, String code, String name){
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
