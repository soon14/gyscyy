package com.aptech.business.overhaul.power.exception;


public class PowerExceptionType {
	
	
	public static PowerExceptionType POWER_CODE_TYPE = new PowerExceptionType("60001", "该设备已存在该停送电方式的记录，请选择其他停送电方式！");
	public static PowerExceptionType POWER_CODE_STATUS = new PowerExceptionType("60002", "该设备存在未完成流程,不能进行该操作！");
	public static PowerExceptionType POWER_DELETE_STATUS = new PowerExceptionType("60003", "只有状态为未提交和已取消的记录可以删除！");
	public static PowerExceptionType POWER_UPDATE_STATUS = new PowerExceptionType("60004", "只有状态为未提交和的已驳回记录可以修改！");
	public static PowerExceptionType LOG_UPDATE_STATUS = new PowerExceptionType("60005", "每个场站每天只能填写一条检修日志！");
	
	private String errorCode ;
	private String errorMsg ;
	
	
	public PowerExceptionType(String errorCode, String errorMsg) {
		super();
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	
	
	
}
