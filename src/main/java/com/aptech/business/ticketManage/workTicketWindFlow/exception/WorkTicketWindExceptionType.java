package com.aptech.business.ticketManage.workTicketWindFlow.exception;



public class WorkTicketWindExceptionType {
	
	private String errorCode ;
	private String errorMsg ;	
	
/*	public static WorkTicketWindExceptionType WORKTICKET_ISEXECUTE = new WorkTicketWindExceptionType("10001", "您有未执行的安全措施，请执行!");
	public static WorkTicketWindExceptionType WORKTICKET_BATHDELETE = new WorkTicketWindExceptionType("10002", "只有待提交和废票状态的可以删除!");
	public static WorkTicketWindExceptionType WORKTICKET_UPDATEDELETE = new WorkTicketWindExceptionType("10003", "该记录已被删除，请刷新列表!");
	public static WorkTicketWindExceptionType WORKTICKET_UPDATESTATUS = new WorkTicketWindExceptionType("10004", "只有待提交和驳回状态的记录可以修改!");
	public static WorkTicketWindExceptionType WORKTICKET_DELETESTATUS = new WorkTicketWindExceptionType("10005", "只有待提交和废票状态的记录可以删除!");
	public static WorkTicketWindExceptionType WORKTICKET_SUBMITSTATUS = new WorkTicketWindExceptionType("10006", "只有待提交状态的记录可以提交!");
	public static WorkTicketWindExceptionType WORKTICKET_JIANDINGSTATUS = new WorkTicketWindExceptionType("10007", "只有驳回和已执行状态的记录可以鉴定!");
	public static WorkTicketWindExceptionType WORKTICKET_ENDSTATUS = new WorkTicketWindExceptionType("10007", "只有已执行状态的记录可以设置为标准票!");*/
	public static WorkTicketWindExceptionType WORKTICKET_DELETEFAIL = new WorkTicketWindExceptionType("1", "删除失败!");
	private WorkTicketWindExceptionType(String errorCode, String errorMsg) {
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
