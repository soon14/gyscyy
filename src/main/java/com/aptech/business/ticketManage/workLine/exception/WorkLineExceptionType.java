package com.aptech.business.ticketManage.workLine.exception;



public class WorkLineExceptionType {
	
	private String errorCode ;
	private String errorMsg ;	
	
	public static WorkLineExceptionType DEFECT_CODE_NULL = new WorkLineExceptionType("30001", "您有未执行的安全措施，请执行!");
	private WorkLineExceptionType(String errorCode, String errorMsg) {
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
