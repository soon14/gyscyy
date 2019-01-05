package com.aptech.business.component.dictionary;

import com.aptech.common.system.dictionary.domain.BaseCodeEnum;

/** @ClassName:    PowerStatusEnum.java 
 * @author         
 * @version        V1.0  
 * @Date           2017年7月31日 下午13:19:22 
 */
public enum PowerTypeEnum implements BaseCodeEnum{
	/**
	 * 0：送电
	 */
	PENDING(0, "0", "送电"),
	/**
	 * 1 审核中
	 */
	AUDITING(1, "1", "停电"),
	/**
	 * 2 审核中
	 */
	STOPANDSEND(2, "2", "停送电");
	

	private Integer id;
	
	private String code;
	
	private String name;
	
	PowerTypeEnum(Integer id, String code, String name){
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
