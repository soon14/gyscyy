package com.aptech.business.train.trainManagement.domain;

import com.aptech.common.system.dictionary.domain.BaseCodeEnum;

/**
 * 发文管理审批状态
 * @author Administrator
 *
 */
public enum TrainApprovalStatusEnum implements BaseCodeEnum {
	/**
	 * 待提交1
	 */
	DTJ(1, "1", "待提交"),
	
	/**
	 * 待审核2
	 */
	REVIEW (2, "2", "待审核"),
	
	/**
	 * 审核通过3
	 */
	RESULTS(3, "3", "审核通过"),
	/**
	 * 再提交
	 */
	RESUBMIT(4, "4", "再提交"),;

	

	private Integer id;
	
	private String code;
	
	private String name;
	
	TrainApprovalStatusEnum(Integer id, String code, String name){
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
