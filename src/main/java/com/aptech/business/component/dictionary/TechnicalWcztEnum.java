package com.aptech.business.component.dictionary;

import com.aptech.common.system.dictionary.domain.BaseCodeEnum;


/**
* @ClassName: TechnicalWcztEnum
* @Description: 
* @author zzq
* @date 
*
*/
public enum TechnicalWcztEnum implements BaseCodeEnum{
	/**
	 * 1：合格
	 */
    QUALIFIED(1, "1", "完成"),
	/**
	 * 0不合格
	 */
    NOQUALIFIED(0, "0", "未完成");

	private Integer id;
	
	private String code;
	
	private String name;
	
	TechnicalWcztEnum(Integer id, String code, String name){
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
