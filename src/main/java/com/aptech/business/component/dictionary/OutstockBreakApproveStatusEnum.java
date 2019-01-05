package com.aptech.business.component.dictionary;

import com.aptech.common.system.dictionary.domain.BaseCodeEnum;

public enum OutstockBreakApproveStatusEnum implements BaseCodeEnum {
	
	PENDING(0, "0", "待提交"),//pending 
	REJECT(1, "1", "驳回"),//reject 
	MONITOR(2,"2","待检修班长审批"),//monitor
	STOREKEEPER(3, "3", "待库管员审批"),//storekeeper 
	DIRECTORAPPROVE(4, "4", "待管理人员审核"),//directorapprove 
	END(5, "5", "审核通过"),//END
	CANCEL(6, "6", "取消流程"),//CANCEL
	PRODUCTAPPROVE(7, "7", "待生技部审批"),//PRODUCTAPPROVE
	LEADERAPPROVE(8, "8", "待生产副总审批"),//PRODUCTAPPROVE
	DRAWOUTFLOW(9, "9", "报废出库流程");//PRODUCTAPPROVE
	private int id;
	
	private String code;
	
	private String name;

	OutstockBreakApproveStatusEnum(Integer id, String code, String name){
		this.id = id;
		this.code = code;
		this.name = name;
	}
	
	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public String getCode() {
		return code;
	}

	@Override
	public String getName() {
		return name;
	}
}
