package com.aptech.business.component.dictionary;

import com.aptech.common.system.dictionary.domain.BaseCodeEnum;

public enum ScrapLibraryStatusEnum implements BaseCodeEnum{
	PENDING(0, "0", "待提交"),//pending 
	REJECT(1, "1", "驳回到库管员"),//reject 
//	MONITOR(2,"2","待检修班长审批"),//monitor
//	STOREKEEPER(3, "3", "待库管员审批"),//storekeeper 
	DIRECTORAPPROVE(4, "4", "待场长审核"),//directorapprove 
	END(5, "5", "审核通过"),//END
	CANCEL(6, "6", "取消流程"),//CANCEL
	PRODUCTAPPROVE(7, "7", "待生技部审批"),//PRODUCTAPPROVE
	LEADERAPPROVE(8, "8", "待生产副总审批");//LEADERAPPROVE
//	TECHNOLOGYREPAIR(9, "9", "待技术人员修复"),//TECHNOLOGYREPAIR
//	MANAGERAPPROVE(10, "10", "待库管员处理"),//MANAGERAPPROVE
//	MONEYERAPPROVE(11, "11", "待财务人员入账"),//MONEYERAPPROVE
//	TOMANAGER(12, "12", "已修复待库管员处理"),//MANAGERAPPROVE
//	DISCARDED(14, "14", "已丢弃"),//MANAGERAPPROVE
//	SHOPED(14, "14", "已变卖"),//MANAGERAPPROVE
//	TOSHOPAPPROVE(13, "13", "待变卖");//MONEYERAPPROVE
	private Integer id;
	private String code;
	private String name;
	ScrapLibraryStatusEnum(Integer id,String code,String name){
		this.id=id;
		this.code=code;
		this.name=name;
	}
	public Integer getId() {
		// TODO Auto-generated method stub
		return id;
	}

	@Override
	public String getCode() {
		// TODO Auto-generated method stub
		return code;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}

}
