package com.aptech.business.component.dictionary;

import com.aptech.common.system.dictionary.domain.BaseCodeEnum;


/**
* 撤回历史关联招标采购类型
* @author ly
* @date 2018年9月10日 下午1:29:06 
*/
public enum itemTypeEnum implements BaseCodeEnum{
	LX(1, "1", "立项批复"),
	DB(2, "2", "定标请示函"),
	SP(3, "3", "合同审批"),
	QD(4, "4", "合同签订");
	private Integer id;
	
	private String code;
	
	private String name;
	
	itemTypeEnum(Integer id, String code, String name){
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
