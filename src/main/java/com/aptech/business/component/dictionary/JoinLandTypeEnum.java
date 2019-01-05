package com.aptech.business.component.dictionary;

import com.aptech.common.system.dictionary.domain.BaseCodeEnum;


/**
* @ClassName: JoinLandTypeEnum
* @Description: 接地刀闸状态枚举类
* @author sunliang
* @date 2017年9月6日 上午11:41:38
*
*/
public enum JoinLandTypeEnum implements BaseCodeEnum{
	/**
	 * 1：已分
	 */
	OPENING(1, "1", "已分"),
	/**
	 * 1 已合
	 */
	CLOSING(2, "2", "已合");

	private Integer id;
	
	private String code;
	
	private String name;
	
	JoinLandTypeEnum(Integer id, String code, String name){
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
