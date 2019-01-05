package com.aptech.business.component.dictionary;

import com.aptech.common.system.dictionary.domain.BaseCodeEnum;

/** @ClassName:    PowerStatusEnum.java 
 * @author         
 * @version        V1.0  
 * @Date           2017年7月31日 下午13:19:22 
 */
public enum InvestmentPlanStatusEnum implements BaseCodeEnum{
	/**
	 * 0 待提交
	 */
	PENDING(0, "0", "待提交"),
	/**
	 * 1 计划经营处查看
	 */
	OVERHAULDIRECTOR(1, "1", "计划经营处审查"),
	/**
	 * 2计划经营处执行
	 */
	PROFESSIONALWORKER (2, "2", "计划经营处执行"),
	
	/**
	 * 驳回到投资管理处
	 */
	REJECT(3,"3","驳回到投资管理处"),
	
	/**
	 * 审核通过
	 */
	FINISH(4, "4", "审核通过"),
	/**
	 * 取消流程
	 */
	CANCEL(5,"5","取消流程"),
	
	ZTJ(6,"6","再提交");

	private Integer id;
	
	private String code;
	
	private String name;
	
	InvestmentPlanStatusEnum(Integer id, String code, String name){
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
