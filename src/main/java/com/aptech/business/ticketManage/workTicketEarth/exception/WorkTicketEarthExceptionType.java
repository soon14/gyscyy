package com.aptech.business.ticketManage.workTicketEarth.exception;

import com.aptech.business.ticketManage.workTicket.exception.WorkTicketExceptionType;

public class WorkTicketEarthExceptionType {
	
	private String errorCode ;
	private String errorMsg ;	
	
	public static WorkTicketEarthExceptionType WORKTICKET_ISEXECUTE = new WorkTicketEarthExceptionType("10001", "您有未执行的安全措施，请执行!");
	public static WorkTicketEarthExceptionType WORKTICKET_BATHDELETE = new WorkTicketEarthExceptionType("10002", "只有待提交和废票状态的可以删除!");
	public static WorkTicketEarthExceptionType WORKTICKET_UPDATEDELETE = new WorkTicketEarthExceptionType("10003", "该记录已被删除，请刷新列表!");
	public static WorkTicketEarthExceptionType WORKTICKET_UPDATESTATUS = new WorkTicketEarthExceptionType("10004", "只有待提交和驳回状态的记录可以修改!");
	public static WorkTicketEarthExceptionType WORKTICKET_DELETESTATUS = new WorkTicketEarthExceptionType("10005", "只有待提交和废票状态的记录可以删除!");
	public static WorkTicketEarthExceptionType WORKTICKET_SUBMITSTATUS = new WorkTicketEarthExceptionType("10006", "只有待提交状态的记录可以提交!");
	public static WorkTicketEarthExceptionType WORKTICKET_JIANDINGSTATUS = new WorkTicketEarthExceptionType("10007", "只有驳回和已执行状态的记录可以鉴定!");
	public static WorkTicketEarthExceptionType WORKTICKET_ENDSTATUS = new WorkTicketEarthExceptionType("10007", "只有已执行状态的记录可以设置为标准票!");
	public static WorkTicketEarthExceptionType WORKTICKET_ONLYSELF = new WorkTicketEarthExceptionType("100109", "只可以提交自己的工作票!");
	private WorkTicketEarthExceptionType(String errorCode, String errorMsg) {
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
