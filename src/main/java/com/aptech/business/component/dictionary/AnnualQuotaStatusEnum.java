package com.aptech.business.component.dictionary;

import com.aptech.common.system.dictionary.domain.BaseCodeEnum;

/** @ClassName:    WorkStatusEnum.java 
 * @author         zhangzq
 * @version        V1.0  
 * @Date           2017年6月5日 下午3:30:22 
 */
public enum AnnualQuotaStatusEnum implements BaseCodeEnum{
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
	 * 已收票待值长确认4
	 */
	LONGVALUESURE(4, "4", "已收票待值长确认"),
	/**
	 * 待许可5
	 */
	ALLOW(5, "5", "待许可"),
	/**
	 * 待工作负责人确认签字6
	 */
	PICSURE(6, "6", "待工作负责人确认"),
	
	/**
	 * 待签发-变更9
	 */
	CHANGEISSUED(9, "9", "负责人变更待签发"),
	/**
	 * 已签发待许可-变更10
	 */
	CHANGALLOW(10, "10", "负责人变更待许可"),
	/**
	 * 待值长签字-延期11
	 */
	MANAGERDELAY(11, "11", "延期待值长签字"),
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
	 * 待许可-申请试运15
	 */
	ALLOWAPPLY(15, "15", "申请试运待许可"),
	/**
	 * 待值长签字-申请试运16
	 */
	MANAGERAPPLY(16, "16", "申请试运待值长签字"),
	
	/**
	 * 待许可-试运恢复17
	 */
	ALLOWREGAIN(17, "17", "试运恢复待许可"),
	/**
	 * 待值长签字-试运恢复18
	 */
	MANAGERREGAIN(18, "18", "试运恢复待值长签字"),
	/**
	 * 申请试运19
	 */
	APPLY(19, "19", "已试运待负责人确认"),
	/**
	 * 试运恢复20
	 */
	REGAIN(20, "20", "试运已恢复待负责人确认"),
	/**
	 * 已执行21
	 */
	END(21, "21", "已执行"),
	
	/**
	 * 驳回7
	 */
	WORKSTATUS_TYPE_TURNDOWN(7, "7", "驳回到工作负责人"),
	/**
	 * 作废8
	 */
	WORKSTATUS_TYPE_INVALID(8, "8", "工作票取消"),
	/**
	 * 待许可22
	 */
	WORK_ALLOW(22, "22", "待许可"),
	/**
	 * 待值班长22
	 */
	WORK_PERSON(23, "23", "待工作负责人确定"),
	/**
	 * 待值班长22
	 */
	WORK_MONITOR(24, "24", "待值班长确定"),
	/**
	 * 待许可22
	 */
	WORK_ALLOW_END(25, "25", "终结待许可"),
	/**
	 * 待值班长22
	 */
	WORK_PERSON_END(26, "26", "终结待工作负责人确定"),
	/**
	 * 待值班长22
	 */
	WORK_MONITOR_END(27, "27", "终结待值班长确定"),
	/**
	 * 驳回7
	 */
	WORK_ALLOW_REJECT(28, "28", "驳回到许可人");
	private Integer id;
	
	private String code;
	
	private String name;
	
	AnnualQuotaStatusEnum(Integer id, String code, String name){
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
