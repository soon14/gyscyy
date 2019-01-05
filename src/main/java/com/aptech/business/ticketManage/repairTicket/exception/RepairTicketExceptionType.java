package com.aptech.business.ticketManage.repairTicket.exception;



public class RepairTicketExceptionType {
	
	private String errorCode ;
	private String errorMsg ;	
	
	public static RepairTicketExceptionType WORKTICKET_ISEXECUTE = new RepairTicketExceptionType("10001", "您有未执行的安全措施，请执行!");
	public static RepairTicketExceptionType WORKTICKET_BATHDELETE = new RepairTicketExceptionType("10002", "只有待提交和废票状态的可以删除!");
	public static RepairTicketExceptionType WORKTICKET_UPDATEDELETE = new RepairTicketExceptionType("10003", "该记录已被删除，请刷新列表!");
	public static RepairTicketExceptionType WORKTICKET_UPDATESTATUS = new RepairTicketExceptionType("10004", "只有待提交和驳回状态的记录可以修改!");
	public static RepairTicketExceptionType WORKTICKET_DELETESTATUS = new RepairTicketExceptionType("10005", "只有待提交和废票状态的记录可以删除!");
	public static RepairTicketExceptionType WORKTICKET_SUBMITSTATUS = new RepairTicketExceptionType("10006", "只有待提交状态的记录可以提交!");
	public static RepairTicketExceptionType WORKTICKET_JIANDINGSTATUS = new RepairTicketExceptionType("10007", "只有驳回和已执行状态的记录可以鉴定!");
	public static RepairTicketExceptionType WORKTICKET_ENDSTATUS = new RepairTicketExceptionType("10007", "只有已执行状态的记录可以设置为标准票!");
	private RepairTicketExceptionType(String errorCode, String errorMsg) {
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
