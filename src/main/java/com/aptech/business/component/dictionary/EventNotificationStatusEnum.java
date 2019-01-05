package com.aptech.business.component.dictionary;

import com.aptech.common.system.dictionary.domain.BaseCodeEnum;

public enum EventNotificationStatusEnum  implements BaseCodeEnum{
	/**
	 * 待提交1
	 */
	DTJ(1, "1", "待提交"),
	/**
	 * 待运行检修专工、集控检修中心主任审核2
	 */
	ZGZRSH(2, "2", "待专工、主任审核"),
	/**
	 * 待生技部审核3
	 */
	SJBSH(3, "3", "待生技部审核"),
	/**
	 * 待上级领导审核4
	 */
	SJLDSH(4, "4", "待上级领导审核"),
	/**
	 * 审批通过5
	 */
	END(5, "5", "审批通过"),
	/**
	 * 再提交6
	 */
	ZTJ(6, "6", "再提交"),
	/**
	 * 驳回5
	 */
	REJECT(7, "7", "驳回"),
	/**
	 * 驳回到申请人8
	 */
	REJECTOWNER(8, "8", "驳回到申请人"),
	/**
	 * 作废8
	 */
	ZF(9, "9", "作废");

	
	EventNotificationStatusEnum(Integer id, String code, String name){
		this.id = id;
		this.code = code;
		this.name = name;
	}
	
	
	private Integer id;
	
	private String code;
	
	private String name;
	@Override
	public String getCode() {
		// TODO Auto-generated method stub
		return code;
	}

	@Override
	public Integer getId() {
		// TODO Auto-generated method stub
		return id;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}

}
