package com.aptech.business.component.dictionary;

import com.aptech.common.system.dictionary.domain.BaseCodeEnum;

/** @ClassName:    PowerStatusEnum.java 
 * @author         
 * @version        V1.0  
 * @Date           2017年7月31日 下午13:19:22 
 */
public enum YearPurchaseBtnTypeEnum implements BaseCodeEnum{

	/**
	 * 1综合管理处审核
	 */
	synmangeBtn(1, "1", "管理技术处审核"),
	/**
	 * 2生产技术处审核
	 */
	technologyBtn(2, "2", "生产技术处审核"),
	/**
	 * 3计划经营处审核
	 */
	planBtn(3, "3", "计划经营处审核"),
	/**
	 * 4 单位领导审核
	 */
	leaderBtn(4, "4", "单位领导审核"),
	/**
	 * 5计划经营处执行
	 */
	planoperationBtn(5, "5", "计划经营处执行"),
	
	/**
	 * "6", "废票
	 */
	FP(6, "6", "废票"),
	/**
	 * "6", "再提交
	 */
	ZTJ(7, "7", "再提交");


	private Integer id;
	
	private String code;
	
	private String name;
	
	YearPurchaseBtnTypeEnum(Integer id, String code, String name){
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
