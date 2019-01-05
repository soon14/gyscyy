package com.aptech.business.component.dictionary;

import com.aptech.common.system.dictionary.domain.BaseCodeEnum;

/** @ClassName:    PowerStatusEnum.java 
 * @author         
 * @version        V1.0  
 * @Date           2017年7月31日 下午13:19:22 
 */
public enum YearPurchaseStatusEnum implements BaseCodeEnum{
	/**
	 * 0 待提交
	 */
	PENDING(0, "0", "待提交"),
	/**
	 * 1综合管理处审核
	 */
	OVERHAUL(1, "1", "综合管理处审核"),
	/**
	 * 2生产技术处审核
	 */
	MONITOR(2, "2", "生产技术处审核"),
	/**
	 * 3计划经营处审核
	 */
	PLANEXECUTED(3, "3", "计划经营处审核"),
	/**
	 * 4 单位领导审核
	 */
	LEADER(4, "4", "单位领导审核"),
	/**
	 * 4 计划经营处执行
	 */
	EXECUTED(5, "5", "计划经营处执行"),
	/**
	 * 生产运行单位填报
	 */
	REJECT(6,"6","驳回到生产运行单位"),
	
	/**
	 * 已执行
	 */
	FINISH(7, "7", "已执行"),
	/**
	 * 废票
	 */
	UNFINISH(8, "8", "流程取消");



	private Integer id;
	
	private String code;
	
	private String name;
	
	YearPurchaseStatusEnum(Integer id, String code, String name){
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
