package com.aptech.business.ticketManage.typicalTicket.exception;



public class TypicalTicketExceptionType {
	
	private String errorCode ;
	private String errorMsg ;	
	
	public static TypicalTicketExceptionType TYPICALTICKET_CODE_NULL = new TypicalTicketExceptionType("20001", "该记录已删除！");
	public static TypicalTicketExceptionType TYPICALTICKET_CODE_REPEAT  = new TypicalTicketExceptionType("20002", "状态已改变，请刷新！");
	public static TypicalTicketExceptionType TYPICALTICKET_CODE_STATUS  = new TypicalTicketExceptionType("20003", "该记录不是待提交或驳回状态，不能该操作！");
	public static TypicalTicketExceptionType TYPICALTICKET_CODE_REJECT  = new TypicalTicketExceptionType("20004", "该记录驳回状态，请在流程中提交！");
	private TypicalTicketExceptionType(String errorCode, String errorMsg) {
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
