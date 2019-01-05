package com.aptech.business.ticketManage.workFireTwo.exception;



public class WorkFireTwoExceptionType {
	
	private String errorCode ;
	private String errorMsg ;	
	
	public static WorkFireTwoExceptionType DEFECT_CODE_NULL = new WorkFireTwoExceptionType("10001", "您有未执行的安全措施，请执行!");
	private WorkFireTwoExceptionType(String errorCode, String errorMsg) {
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
