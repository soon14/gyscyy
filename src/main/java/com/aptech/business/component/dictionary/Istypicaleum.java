package com.aptech.business.component.dictionary;

import com.aptech.common.system.dictionary.domain.BaseCodeEnum;

public enum Istypicaleum implements BaseCodeEnum {
	/**
	 * 区分典型票，还是普通票(1本身是工作票，
	 */
	ISSETYES(1, "1", "是"),
	/**
	 *  0存入的是典型票
	 */
	ISSETNO(0, "0", "否");

	private Integer id;
	
	private String code;
	
	private String name;
	
	Istypicaleum(Integer id, String code, String name){
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
