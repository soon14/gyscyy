package com.aptech.business.component.dictionary;

import com.aptech.common.system.dictionary.domain.BaseCodeEnum;

/** @ClassName:    SerialNumberEnum.java 
 * @author         wangjw
 * @version        V1.0  
 * @Date           2017年6月15日 下午3:30:22 
 */
public enum SerialNumberEnum implements BaseCodeEnum{
	/**
	 * 缺陷票
	 */
	DEFECTCODE(1,"DefectCode", "缺陷票编号");
	

	private Integer id;
	
	private String code;
	
	private String name;
	
	SerialNumberEnum(Integer id, String code, String name){
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
