package com.aptech.business.ticketManage.workElectricTwo.exception;



public class WorkElectricTwoExceptionType {
	
	private String errorCode ;
	private String errorMsg ;	
	
	public static WorkElectricTwoExceptionType DEFECT_CODE_NULL = new WorkElectricTwoExceptionType("10001", "您有未执行的安全措施，请执行!");
	private WorkElectricTwoExceptionType(String errorCode, String errorMsg) {
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
