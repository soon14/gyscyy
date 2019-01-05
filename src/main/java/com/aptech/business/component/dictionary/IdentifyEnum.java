package com.aptech.business.component.dictionary;

import com.aptech.common.system.dictionary.domain.BaseCodeEnum;

/** @ClassName:    WorkStatusEnum.java 
 * @author         zhangzq
 * @version        V1.0  
 * @Date           2017年6月5日 下午3:30:22 
 */
public enum IdentifyEnum implements BaseCodeEnum{
	
	IDENTIFYYES(1, "1", "合格"),
	IDENTIFYNO(2, "2", "不合格");
	

	private Integer id;
	
	private String code;
	
	private String name;
	
	IdentifyEnum(Integer id, String code, String name){
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
