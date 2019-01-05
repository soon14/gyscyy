package com.aptech.business.OAManagement.dispatchManagement.domain;

import com.aptech.common.system.dictionary.domain.BaseCodeEnum;

/**
 * 发文管理流程
 * @author Administrator
 *
 */
public enum DispatchProcessEnum implements BaseCodeEnum {
	
	/**
	 * 部门领导审核：领导审核
	 */
	REVIEW_LEADER_APPROVAL(1, "1", "领导审核"),
	/**
	 * 部门领导审核：会签
	 */
	REVIEW_JOINLTY_SIGN(2, "2", "会签"),
	
	/**
	 * 部门领导审核：驳回
	 */
	REVIEW_REJECT (3, "3", "驳回"),
	
	/**
	 * 会签： 同意
	 */
	JOINLTY_SING_AGREE(4, "1", "同意"),
	/**
	 * 会签： 驳回部门负责人审核
	 */
	JOINLTY_SING_REJECT_REVIEW(5, "2", "驳回"),
	/**
	 * 会签： 驳回发起人
	 */
	JOINLTY_SING_REJECT_START(6, "3", "驳回"),
	
	/**
	 * 领导审核：同意
	 */
	LEADER_APPROVAL_AGREE(7, "1", "同意"),
	
	/**
	 * 领导审核：驳回会签
	 */
	LEADER_APPROVAL_REJECT_JOINLTY_SING(8, "2", "驳回"),
	
	/**
	 * 领导审核：驳回部门领导审核
	 */
	LEADER_APPROVAL_REJECT_REVIEW(9, "3", "驳回"),
	/**
	 * 领导审核：驳回发起人
	 */
	LEADER_APPROVAL_REJECT_START(10, "4", "驳回");
	

	private Integer id;
	
	private String code;
	
	private String name;
	
	DispatchProcessEnum(Integer id, String code, String name){
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
