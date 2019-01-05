package com.aptech.business.run.protect.exception;



public class ProtectExceptionType {
	
	private String errorCode ;
	private String errorMsg ;	
	
	public static ProtectExceptionType PROTECT_CODE_WAY = new ProtectExceptionType("20001", "同一设备保护投退方式不能相同！");
	public static ProtectExceptionType PROTECT_CODE_TIME  = new ProtectExceptionType("20002", "同一设备同一时间只允许有一条保护投退申请记录！");
	public static ProtectExceptionType PROTECT_CODE_CHECK  = new ProtectExceptionType("20003", "该设备保护投退流程没有执行完毕，不允许再进行重新申请！");
    public static ProtectExceptionType PROTECT_CODE_DEL  = new ProtectExceptionType("20004", "只有待提交状态以及取消状态可删除！");	
	private ProtectExceptionType(String errorCode, String errorMsg) {
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
	}
	
	public String getErrorCode() {
		return errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

}
