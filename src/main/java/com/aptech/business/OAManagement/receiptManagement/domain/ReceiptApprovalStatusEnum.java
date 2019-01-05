package com.aptech.business.OAManagement.receiptManagement.domain;

import com.aptech.common.system.dictionary.domain.BaseCodeEnum;

/**
 * 发文管理审批状态
 * @author Administrator
 *
 */
public enum ReceiptApprovalStatusEnum implements BaseCodeEnum {
	/**
	 * 待提交1
	 */
	DTJ(1, "1", "待提交"),
	
	/**
	 * 待生产单位负责人审核2
	 */
	REVIEW (2, "2", "待生产单位负责人审核"),
	
	/**
	 * 处室办理人审核
	 */
	RECEIVING_UNIT_HANDLE(3, "3", "待处室办理人审核"),
	
	/**
	 * 待处室负责人审核
	 */
	JOINLTY_SING(4, "4", "待处室负责人审核"),
	
	/**
	 * 不需要会签时，待领导审核4
	 */
	LEADER_APPROVAL_NO_SIGN(5, "5", "待领导审核"),
	
	/**
	 * 生产单位负责人驳回
	 */
	REVIEW_REJECT(6, "6", "生产单位负责人驳回"),
	
	/**
	 * 处室办理人驳回发起人
	 */
	RECEIVING_REJECT_START(7, "7", "处室办理人驳回"),
	/**
	 * 处室办理人驳回发起人
	 */
	RECEIVING_REJECT_REVIEW(8, "8", "处室办理人驳回"),
	/**
	 * 处室办理人驳回发起人
	 */
	RECEIVING_REJECT_JOINLTY(9, "9", "处室办理人驳回"),
	
	/**
	 * 需要会签 待领导审核
	 */
	LEADER_APPROVAL_SIGN(10, "10", "待领导审核"),
	
	/**
	 * 会签驳回部门领导审核
	 */
	JOINLTY_SING_REJECT_REVIEW(11, "11", "处室负责人审核驳回"),
	/**
	 * 会签驳回处室办理人处理
	 */
	JOINLTY_SING_REJECT_RECEIVING(12, "12", "处室负责人审核驳回"),
	/**
	 * 会签驳回发起人
	 */
	JOINLTY_SING_REJECT_START(13, "13", "处室负责人审核驳回"),
	
	/**
	 * 领导审核驳回发起人
	 */
	LEADER_APPROVAL_REJECT_START(14, "14", "领导审核驳回"),
	/**
	 * 领导审核驳回处室办理人处理
	 */
	LEADER_APPROVAL_REJECT_RECEIVING(15, "15", "领导审核驳回"),
	
	/**
	 * 领导审核驳回会签
	 */
	LEADER_APPROVAL_REJECT_SIGN(16, "16", "领导审核驳回"),
	
	/**
	 * 领导审核驳回部门领导审核
	 */
	LEADER_APPROVAL_REJECT_REVIEW(17, "17", "领导审核驳回"),
	
	
	RESULTS(18, "18", "审核通过"),
	
	/**
	 * 废弃
	 */
	DISCARDED(19, "19", "废弃"),
	
	/**
	 * 外部部门会签
	 */
	OUT_DEPARTMENT(20, "20", "待外部部门会签"),
	/**
	 * 外部部门会签驳回会签
	 */
	OUT_DEPARTMENT_REJECT_SIGN(21, "21", "外部部门会签驳回"),
	/**
	 * 外部部门会签->领导审核
	 */
	OUT_DEPARTMENT_LEADER(22, "22", "待领导审核"),
	/**
	 * 待生产部门会签
	 */
	PRODUCTION_UNITD_JOINLTY(23, "23", "待生产部门会签"),
	/**
	 * 生产部门会签驳回
	 */
	PRODUCTION_UNITD_JOINLTY_REJECT(24, "24", "生产部门会签驳回"),
	/**
	 * 待部门内部会签
	 */
	DEPARTMETNT_INSIDE_JOINLTY(25, "25", "待部门内部会签"),
	/**
	 * 部门内部会签驳回
	 */
	DEPARTMETNT_INSIDE_JOINLTY_REJECT(26, "26", "部门内部会签驳回");

	private Integer id;
	
	private String code;
	
	private String name;
	
	ReceiptApprovalStatusEnum(Integer id, String code, String name){
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
