package com.aptech.business.ticketManage.workElectric.exception;



public class WorkElectricExceptionType {
	
	private String errorCode ;
	private String errorMsg ;	
	
	public static WorkElectricExceptionType DEFECT_CODE_NULL = new WorkElectricExceptionType("10001", "您有未执行的安全措施，请执行!");
	private WorkElectricExceptionType(String errorCode, String errorMsg) {
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
