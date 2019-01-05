package com.aptech.business.OAManagement.dispatchManagement.domain;

import com.aptech.common.system.dictionary.domain.BaseCodeEnum;

/**
 * 审核类型
 * @author Administrator
 *
 */
public enum ReviewTypeEnum implements BaseCodeEnum {
	/**
	 * 部门负责人审核
	 */
	REVIEW(1, "1", "部门负责人审核"),
	
	/**
	 * 会签
	 */
	JOINTLY_SIGN(2, "2", "会签"),
	
	/**
	 * 领导审批
	 */
	LEADER(3, "3", "领导审批"),
	
	/**
	 * 领导审批
	 */
	RECEIVING_HANDLE(4, "4", "接收人处理"),
	/**
	 * 外部部门会签
	 */
	OUT_DEPARTMENT(5, "5", "外部部门会签");
	

	private Integer id;
	
	private String code;
	
	private String name;
	
	ReviewTypeEnum(Integer id, String code, String name){
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
