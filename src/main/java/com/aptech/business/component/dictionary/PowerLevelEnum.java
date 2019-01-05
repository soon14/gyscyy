package com.aptech.business.component.dictionary;

import com.aptech.common.system.dictionary.domain.BaseCodeEnum;

/** @ClassName:    PowerStatusEnum.java 
 * @author         
 * @version        V1.0  
 * @Date           2017年7月31日 下午13:19:22 
 */
public enum PowerLevelEnum implements BaseCodeEnum{
	/**
	 * 0：是
	 */
	level(0, "0", "110KV"),
	/**
	 * 1 否
	 */
	level2(1, "1", "35KV及以下");

	private Integer id;
	
	private String code;
	
	private String name;
	
	PowerLevelEnum(Integer id, String code, String name){
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
