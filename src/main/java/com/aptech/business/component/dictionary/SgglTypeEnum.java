package com.aptech.business.component.dictionary;

import com.aptech.common.system.dictionary.domain.BaseCodeEnum;

/** @ClassName:    WorkStatusEnum.java 
 * @author         zhangzq
 * @version        V1.0  
 * @Date           2017年6月5日 下午3:30:22 
 */
public enum SgglTypeEnum implements BaseCodeEnum{
	/**
	 * 安全生产事故1
	 */
	AQSCSG(1, "1", "安全生产事故"),
	/**
	 * 火灾事故2
	 */
	HZSG(2, "2", "火灾事故"),
	/**
	 * 交通事故3
	 */
	JTSG(3, "3", "交通事故");
	
	private Integer id;
	
	private String code;
	
	private String name;
	
	SgglTypeEnum(Integer id, String code, String name){
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
