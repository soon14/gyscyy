package com.aptech.business.ticketManage.workEarth.exception;

import com.aptech.business.ticketManage.workTicket.exception.WorkTicketExceptionType;

public class WorkEarthExceptionType {
	
	private String errorCode ;
	private String errorMsg ;	
	
	public static WorkEarthExceptionType DEFECT_CODE_NULL = new WorkEarthExceptionType("10001", "您有未执行的安全措施，请执行!");
	   public static WorkEarthExceptionType WORKTICKET_ONLYSELF = new WorkEarthExceptionType("100109", "只可以提交自己的工作票!");

	private WorkEarthExceptionType(String errorCode, String errorMsg) {
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
