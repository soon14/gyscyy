package com.aptech.business.component.dictionary;

import com.aptech.common.system.dictionary.domain.BaseCodeEnum;

/** @ClassName:    WorkHelpStatusEnum.java   技术监督
 * @author         zhangzq
 * @version        V1.0  
 * @Date           20171026
 */
public enum TechnicalStatusEnum implements BaseCodeEnum{
	/**
	 * 待提交1
	 */
	DTJ(1, "1", "待提交"),
	/**
	 * 待生技处审核2
	 */
	DSJCSH(2, "2", "待生技处审核"),
	/**
	 * 待生技部主任审核3
	 */
	DSJBZRSH(3, "3", "待生技部主任审核"),
	/**
	 * 待上级领导审核4
	 */
	DSJLDSH(4, "4", "待上级领导审核"),
	/**
	 * 驳回到检修专工5
	 */
	BHGZFZR(5, "5", "驳回到检修专工"),
	/**
	 * 作废6
	 */
	GZPXQ(6, "6", "作废"),
	/**
	 * 审核通过7
	 */
	END(7, "7", "审核通过");
	
	private Integer id;
	
	private String code;
	
	private String name;
	
	TechnicalStatusEnum(Integer id, String code, String name){
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
