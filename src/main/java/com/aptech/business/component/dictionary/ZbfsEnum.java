package com.aptech.business.component.dictionary;

import com.aptech.common.system.dictionary.domain.BaseCodeEnum;

/** @ClassName:    WorkStatusEnum.java 
 * @author         zhangzq
 * @version        V1.0  
 * @Date           2017年6月5日 下午3:30:22 
 */
public enum ZbfsEnum implements BaseCodeEnum{
	GK(1, "1", "公开招标"),
	YQ(2, "2", "邀请招标"),
	YB(3, "3", "议标");
	private Integer id;
	
	private String code;
	
	private String name;
	
	ZbfsEnum(Integer id, String code, String name){
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
