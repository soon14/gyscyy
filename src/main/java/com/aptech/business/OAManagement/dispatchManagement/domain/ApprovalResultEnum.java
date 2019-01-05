package com.aptech.business.OAManagement.dispatchManagement.domain;

import com.aptech.common.system.dictionary.domain.BaseCodeEnum;

/**
 * 密级
 * @author Administrator
 *
 */
public enum ApprovalResultEnum implements BaseCodeEnum {
	/**
	 * 同意
	 */
	AGREE(1, "1", "同意"),
	
	/**
	 * 驳回
	 */
	REJECT(2, "2", "驳回");
	

	private Integer id;
	
	private String code;
	
	private String name;
	
	ApprovalResultEnum(Integer id, String code, String name){
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
