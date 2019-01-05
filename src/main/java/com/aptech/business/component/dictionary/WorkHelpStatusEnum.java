package com.aptech.business.component.dictionary;

import com.aptech.common.system.dictionary.domain.BaseCodeEnum;

/** @ClassName:    WorkHelpStatusEnum.java   继点保护业务状态
 * @author         zhangzq
 * @version        V1.0  
 * @Date           20171026
 */
public enum WorkHelpStatusEnum implements BaseCodeEnum{
	/**
	 * 待提交1
	 */
	TOBESUBMIT(1, "1", "待提交"),
	/**
	 * 已提交待执行2
	 */
	TOBEISSUED(2, "2", "已提交待执行"),
	/**
	 * 待执行监护人确认3
	 */
	JHRSURE(3, "3", "待执行监护人确认"),
	/**
	 * 待恢复4
	 */
	DHF(4, "4", "待恢复"),
	/**
	 * 待恢复监护人确认5
	 */
	DHFJKRSURE(5, "5", "待恢复监护人确认"),
	/**
	 * 驳回到工作负责人7
	 */
	BHGZFZR(7, "7", "驳回到工作负责人"),
	/**
	 * 工作票取消8
	 */
	GZPXQ(8, "8", "工作票取消"),
	
	/**
	 * 已执行21
	 */
	END(21, "21", "已执行");
	
	private Integer id;
	
	private String code;
	
	private String name;
	
	WorkHelpStatusEnum(Integer id, String code, String name){
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
