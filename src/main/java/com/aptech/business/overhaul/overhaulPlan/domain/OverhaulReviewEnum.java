package com.aptech.business.overhaul.overhaulPlan.domain;

import com.aptech.common.system.dictionary.domain.BaseCodeEnum;


/**
 * 专工审核枚举
 * @author Administrator
 *
 */
public enum OverhaulReviewEnum implements BaseCodeEnum{
	/**
	 * 生技部检修专工审核
	 */
	OVERHAUL_WORKER_REVIEW(1, "1", "生技部检修专工审核"),
	/**
	 * 2集控中心审核
	 */
	CENTRRALIZE_REVIEW (2, "2", "集控中心审核"),
	/**
	 * 3安监处审核
	 */
	SAFETY_REVIEW(3, "3", "安监处审核");

	private Integer id;
	
	private String code;
	
	private String name;
	
	OverhaulReviewEnum(Integer id, String code, String name){
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
