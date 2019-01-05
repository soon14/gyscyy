package com.aptech.business.OAManagement.receiptManagement.domain;

import com.aptech.common.system.dictionary.domain.BaseCodeEnum;

/**
 * 审核类型
 * @author Administrator
 *
 */
public enum ReceiptReviewTypeEnum implements BaseCodeEnum {
	/**
	 * 部门负责人审核
	 */
	REVIEW(1, "1", "生产单位负责人审核"),
	
	/**
	 * 会签
	 */
	JOINTLY_SIGN(2, "2", "处室负责人审核"),
	
	/**
	 * 领导审批
	 */
	LEADER(3, "3", "领导审批"),
	
	/**
	 * 领导审批
	 */
	RECEIVING_HANDLE(4, "4", "处室办理人审核"),
	
	/**
	 * 外部部门会签
	 */
	OUT_DEPARTMENT(5, "5", "外部部门会签"),
	/**
	 * 生产部门会签
	 */
	PRODUCTION_UNIT_JOINLTY_SING(6, "6", "生产部门会签"),
	/**
	 * 部门内部会签
	 */
	DEPARTMETNT_INSIDE_JOINLTY_SING(7, "7", "部门内部会签");
	

	private Integer id;
	
	private String code;
	
	private String name;
	
	ReceiptReviewTypeEnum(Integer id, String code, String name){
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
