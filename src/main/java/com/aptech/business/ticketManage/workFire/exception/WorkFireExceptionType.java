package com.aptech.business.ticketManage.workFire.exception;



public class WorkFireExceptionType {
	
	private String errorCode ;
	private String errorMsg ;	
	
	public static WorkFireExceptionType DEFECT_CODE_NULL = new WorkFireExceptionType("10001", "您有未执行的安全措施，请执行!");
	private WorkFireExceptionType(String errorCode, String errorMsg) {
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
