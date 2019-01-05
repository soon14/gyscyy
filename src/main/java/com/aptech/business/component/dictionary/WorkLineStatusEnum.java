package com.aptech.business.component.dictionary;

import com.aptech.common.system.dictionary.domain.BaseCodeEnum;

/** @ClassName:    WorkStatusEnum.java 
 * @author         zhangzq
 * @version        V1.0  
 * @Date           2017年11月13日 下午3:30:22 
 */
public enum WorkLineStatusEnum implements BaseCodeEnum{
	/**
	 * 待提交1          
	 */
	TOBESUBMIT(1, "1", "待提交"),
	/**
	 * 已提交待签发2
	 */
	TOBEISSUED(2, "2", "已提交待签发"),
	/**
	 * 已签发待收票3
	 */
	TICKETS(3, "3", "已签发待收票"),
	/**
	 * 待许可5
	 */
	ALLOW(5, "5", "待许可"),
	/**
	 * 待工作负责人确认签字6
	 */
	PICSURE(6, "6", "待工作负责人确认"),
	/**
	 * 驳回7
	 */
	WORKSTATUS_TYPE_TURNDOWN(7, "7", "驳回到工作负责人"),
	/**
	 * 作废8
	 */
	WORKSTATUS_TYPE_INVALID(8, "8", "工作票取消"),
	/**
	 * 待签发-变更9
	 */
	CHANGEISSUED(9, "9", "负责人变更待签发"),
	/**
	 * 已签发待许可-变更10
	 */
	CHANGALLOW(10, "10", "负责人变更待许可"),
	
	/**
	 * 待许可-延期12
	 */
	MANAGERALLOW(12, "12", "延期待许可"),
	/**
	 * 已许可待负责人签字-延期13
	 */
	ALLOWPIC(13, "13", "延期已许可待负责人签字"),
	/**
	 * 待许可-终结14
	 */
	ALLOWEND(14, "14", "终结待许可"),
	
	/**
	 * 已执行21
	 */
	END(21, "21", "已执行");
	
	
	private Integer id;
	
	private String code;
	
	private String name;
	
	WorkLineStatusEnum(Integer id, String code, String name){
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
