package com.aptech.business.component.dictionary;

import com.aptech.common.system.dictionary.domain.BaseCodeEnum;

public enum WorkSpResultEnum implements BaseCodeEnum {
	/**
	 * 5 工作负责人变更
	 */
	WORKPICCHANGE(5, "5", "是"),
	/**
	 * 6工作人员变动
	 */
	PERSONCHANGE(6, "6", "否"),
	/**
	 * 7 延期
	 */
	EXTENSION(7, "7", "否"),
	/**
	 * 8 终结
	 */
	THEEND(8, "8", "否"),
	/**
	 * 9  申请试运
	 */
	APPLYRUN(9, "9", "否"),
	/**
	 * 10  试运恢复
	 */
	STOPRESTORE(10, "10", "否");

	private Integer id;
	
	private String code;
	
	private String name;
	
	WorkSpResultEnum(Integer id, String code, String name){
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
