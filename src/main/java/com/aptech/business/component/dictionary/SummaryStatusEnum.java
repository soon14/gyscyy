package com.aptech.business.component.dictionary;

import com.aptech.common.system.dictionary.domain.BaseCodeEnum;

/** @ClassName:    WorkHelpStatusEnum.java   技术监督
 * @author         zhangzq
 * @version        V1.0  
 * @Date           20171026
 */
public enum SummaryStatusEnum implements BaseCodeEnum{
	/**
	 * 待提交1
	 */
	DTJ(1, "1", "待提交"),
	/**
	 * 待生技部主任审核2
	 */
	DSJBZRSH(2, "2", "待生技部主任审核"),
	/**
	 * 待上级领导审核3
	 */
	DSJLDSH(3, "3", "待上级领导审核"),
	/**
	 * 驳回到工作负责人7
	 */
	BHGZFZR(7, "7", "驳回"),
	/**
	 * 审核通过21
	 */
	END(21, "21", "审核通过");
	
	private Integer id;
	
	private String code;
	
	private String name;
	
	SummaryStatusEnum(Integer id, String code, String name){
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
