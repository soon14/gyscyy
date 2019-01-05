package com.aptech.business.component.dictionary;

import com.aptech.common.system.dictionary.domain.BaseCodeEnum;

/** @ClassName:    PowerStatusEnum.java 
 * @author         
 * @version        V1.0  
 * @Date           2017年7月31日 下午13:19:22 
 */
public enum PlanCycleEnum implements BaseCodeEnum{
	/**
	 * 0：单年
	 */
	ONEYYYY(0, "0", "单年"),
	/**
	 * 1多年
	 */
	DOUYYYY(1, "1", "多年");

	private Integer id;
	
	private String code;
	
	private String name;
	
	PlanCycleEnum(Integer id, String code, String name){
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
