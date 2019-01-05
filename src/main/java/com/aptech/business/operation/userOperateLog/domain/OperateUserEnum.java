package com.aptech.business.operation.userOperateLog.domain;

public enum OperateUserEnum {
	ADD(0,"增加"),
	EDIT(1,"修改"),
	DELETE(2,"删除"),
	LOCK(3,"锁定"),
	UNLOCK(4,"解锁"),
	LOGIN(5,"登录"),
	LOGOUT(6,"登出"),
	ADDRUN(7,"填报");
	
	Integer id;
	String name;
	
	private OperateUserEnum(Integer id,String name) {
		this.id = id;
		this.name = name;
	}
	
	public String getName(){
		return this.name;
	}
	
	public Integer getId(){
		return this.id;
	}

}
