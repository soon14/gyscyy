package com.aptech.business.OAManagement.dispatchManagement.domain;

import com.aptech.common.system.dictionary.domain.BaseCodeEnum;

/**
 * 发文管理审批状态
 * @author Administrator
 *
 */
public enum DispatchApprovalStatusEnum implements BaseCodeEnum {
	/**
	 * 待提交1
	 */
	DTJ(1, "1", "待提交"),
	
	/**
	 * 待部门负责人审核2
	 */
	REVIEW (2, "2", "待部门负责人审核"),
	
	/**
	 * 待会签3
	 */
	JOINLTY_SING(3, "3", "待会签"),
	
	/**
	 * 不需要会签时，待领导审核4
	 */
	LEADER_APPROVAL_NO_SIGN(4, "4", "待领导审核"),
	
	/**
	 * 部门负责人驳回
	 */
	REVIEW_REJECT(5, "5", "部门负责人驳回"),
	
	/**
	 * 需要会签 待领导审核
	 */
	LEADER_APPROVAL_SIGN(6, "6", "待领导审核"),
	
	/**
	 * 会签驳回部门领导审核
	 */
	JOINLTY_SING_REJECT(7, "7", "会签驳回"),
	
	/**
	 * 	
	 */
	LEADER_APPROVAL_REJECT(8, "8", "领导审核驳回"),
	

	/**
	 * 领导审核驳回部门领导审核
	 */
	LEADER_APPROVAL_REJECT_REVIEW(9, "9", "领导审核驳回"),
	
	
	RESULTS(10, "10", "审核通过"),
	/**
	 * 会签驳回发起人
	 */
	JOINLTY_SING_REJECT_START(11, "11", "会签驳回"),
	/**
	 * 领导审核驳回发起人
	 */
	LEADER_APPROVAL_REJECT_START(12, "12", "领导审核驳回"),
	
	/**
	 * 废弃
	 */
	DISCARDED(13, "13", "废弃"),
	/**
	 * 待综合管理处排版
	 */
	COMPOSED(14, "14", "待综合管理处排版");
	

	private Integer id;
	
	private String code;
	
	private String name;
	
	DispatchApprovalStatusEnum(Integer id, String code, String name){
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
