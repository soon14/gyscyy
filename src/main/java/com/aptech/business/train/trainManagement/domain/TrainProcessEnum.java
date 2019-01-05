package com.aptech.business.train.trainManagement.domain;

import com.aptech.common.system.dictionary.domain.BaseCodeEnum;

/**
 * 培训管理流程
 * @author Administrator
 *
 */
public enum TrainProcessEnum implements BaseCodeEnum {
	
	/**
	 * 同意
	 */
	AGREE(1, "1", "领导审核"),
	/**
	 * 驳回
	 */
	REJECT(2, "2", "驳回");
	

	private Integer id;
	
	private String code;
	
	private String name;
	
	TrainProcessEnum(Integer id, String code, String name){
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
