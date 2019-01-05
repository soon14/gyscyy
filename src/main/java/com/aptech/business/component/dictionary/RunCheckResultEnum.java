package com.aptech.business.component.dictionary;

import com.aptech.common.system.dictionary.domain.BaseCodeEnum;


/**
* @ClassName: RunCheckResultEnum
* @Description: 检查类型枚举类
* @author sunliang
* @date 2017年9月6日 上午11:41:38
*
*/
public enum RunCheckResultEnum implements BaseCodeEnum{
	/**
	 * 1：合格
	 */
    QUALIFIED(1, "1", "合格"),
	/**
	 * 0不合格
	 */
    NOQUALIFIED(0, "0", "不合格");

	private Integer id;
	
	private String code;
	
	private String name;
	
	RunCheckResultEnum(Integer id, String code, String name){
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
