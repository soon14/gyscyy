package com.aptech.business.component.dictionary;

import com.aptech.common.system.dictionary.domain.BaseCodeEnum;

/** @ClassName:    PowerStatusEnum.java 
 * @author         
 * @version        V1.0  
 * @Date           2017年7月31日 下午13:19:22 
 */
public enum IsPowerEnum implements BaseCodeEnum{
	/**
	 * 0：是
	 */
	yesPower(0, "0", "是"),
	/**
	 * 1 否
	 */
	noPower(1, "1", "否");

	private Integer id;
	
	private String code;
	
	private String name;
	
	IsPowerEnum(Integer id, String code, String name){
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
