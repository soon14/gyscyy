package com.aptech.business.component.dictionary;

import com.aptech.common.system.dictionary.domain.BaseCodeEnum;

/** @ClassName:    PowerStatusEnum.java 
 * @author         
 * @version        V1.0  
 * @Date           2017年7月31日 下午13:19:22 
 */
public enum OverhaulPlanStatusEnum implements BaseCodeEnum{
	/**
	 * 0 待提交
	 */
	PENDING(0, "0", "待提交"),
	/**
	 * 1 检修主任审核中
	 */
	OVERHAULDIRECTOR(1, "1", "场长审核中"),
	/**
	 * 2专工审核中
	 */
	PROFESSIONALWORKER (2, "2", "生技部,集控中心,安监处审核中"),
	/**
	 * 3 检修专工流转修改中
	 */
	WORKERMODIFY(3, "3", "检修专工流转修改中"),
	/**
	 * 4 生技部主任审核中
	 */
	TECHNOLOGYDIRECTOR(4, "4", "生技部主任审核中"),
	
	/**
	 * 5 生产分管领导审核中
	 */
	TECHNOLOGYLEADER(5,"5","生产分管领导审核中"),
	
	/**
	 * 已驳回
	 */
	REJECT(6,"6","已驳回"),
	
	/**
	 * 审核通过
	 */
	FINISH(7, "7", "审核通过"),
	/**
	 * 取消流程
	 */
	CANCEL(8,"8","取消流程"),
	
	ZTJ(9,"9","再提交"),
	/**
	 * 审核w未通过
	 */
	UNFINISH(10, "10", "审核未批准"),
	
	/**
	 * 检修专工计划提报
	 */
	MAINTENANCE_REPORTT(11, "11", "检修专工计划提报");
	

	private Integer id;
	
	private String code;
	
	private String name;
	
	OverhaulPlanStatusEnum(Integer id, String code, String name){
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
