package com.aptech.business.OAManagement.receiptManagement.domain;

import com.aptech.common.system.dictionary.domain.BaseCodeEnum;

/**
 * 发文管理流程
 * @author Administrator
 *
 */
public enum ReceiptProcessEnum implements BaseCodeEnum {
	
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
	 * 接收人处理：领导审核
	 */
	RECEIVING_PERSION_LEADER_APPROVAL(4, "1", "领导审核"),
	/**
	 * 接收人处理： 会签
	 */
	RECEIVING_PERSION_JOINLTY_SIGN(5, "2", "会签"),
	
	/**
	 *  接收人处理： 驳回部门负责人审查
	 */
	RECEIVING_PERSION_REJECT_REVIEW(6, "3", "驳回"),
	/**
	 *  接收人处理： 驳回发起人
	 */
	RECEIVING_PERSION_REJECT_START(7, "4", "驳回"),
	
	/**
	 * 会签： 同意
	 */
	JOINLTY_SING_AGREE(8, "1", "同意"),
	/**
	 * 会签： 驳回接收人
	 */
	JOINLTY_SING_REJECT_RECEIVING_PERSION(9, "2", "驳回"),
	/**
	 * 会签： 驳回部门负责人审核
	 */
	JOINLTY_SING_REJECT_REVIEW(10, "3", "驳回"),
	/**
	 * 会签： 驳回发起人
	 */
	JOINLTY_SING_REJECT_START(11, "4", "驳回"),
	
	/**
	 * 领导审核：同意
	 */
	LEADER_APPROVAL_AGREE(12, "1", "同意"),
	
	/**
	 * 领导审核：驳回部门领导审核
	 */
	LEADER_APPROVAL_REJECT_REVIEW(13, "2", "驳回"),
	/**
	 * 领导审核：驳回发起人
	 */
	LEADER_APPROVAL_REJECT_START(14, "3", "驳回"),
	/**
	 * 领导审核：驳回会签
	 */
	LEADER_APPROVAL_REJECT_JOINLTY_SING(15, "4", "驳回"),
	/**
	 * 领导审核：驳回接收人
	 */
	LEADER_APPROVAL_REJECT_RECEIVING_PERSION(16, "5", "驳回"),
	/**
	 * 领导审核：驳回接收人 带会签
	 */
	LEADER_APPROVAL_REJECT_RECEIVING_SING(17, "6", "驳回"),
	/**
	 * 会签：外部部门会签
	 */
	JOINLTY_SING_OUT_DEPARTMENT(18, "5", "外部部门审核"),
	/**
	 * 外部部门会签：同意
	 */
	OUT_DEPARTMENT_AGREE(19, "1", "同意"),
	/**
	 * 外部部门会签：驳回
	 */
	OUT_DEPARTMENT_REJECT(20, "2", "驳回"),
	/**
	 * 会签：生产部门会签
	 */
	PRODUCTION_UNIT_JOINLTY_SING(21, "5", "生产部门会签"),
	/**
	 * 生产部门会签：同意
	 */
	PRODUCTION_UNIT_AGREE(22, "1", "同意"),
	/**
	 * 生产部门会签：驳回
	 */
	PRODUCTION_UNIT_REJECT(23, "2", "驳回"),
	/**
	 * 会签：部门内部会签
	 */
	DEPARTMETNT_INSIDE_JOINLTY_SING(24, "5", "部门内部会签"),
	/**
	 * 部门内部会签：同意
	 */
	DEPARTMETNT_INSIDE_JOINLTY_SING_AGREE(26, "1", "同意"),
	/**
	 * 部门内部会签：驳回
	 */
	DEPARTMETNT_INSIDE_JOINLTY_SING_REJECT(27, "2", "驳回");
	
	

	private Integer id;
	
	private String code;
	
	private String name;
	
	ReceiptProcessEnum(Integer id, String code, String name){
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
