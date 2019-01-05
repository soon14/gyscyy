package com.aptech.business.ticketManage.workTicketFire.exception;



public class WorkTicketFireExceptionType {
	
	private String errorCode ;
	private String errorMsg ;	
	
	public static WorkTicketFireExceptionType WORKTICKET_ISEXECUTE = new WorkTicketFireExceptionType("100101", "您有未执行的安全措施，请执行!");
	public static WorkTicketFireExceptionType WORKTICKET_BATHDELETE = new WorkTicketFireExceptionType("100102", "只有待提交和废票状态的可以删除!");
	public static WorkTicketFireExceptionType WORKTICKET_UPDATEDELETE = new WorkTicketFireExceptionType("100103", "该记录已被删除，请刷新列表!");
	public static WorkTicketFireExceptionType WORKTICKET_UPDATESTATUS = new WorkTicketFireExceptionType("100104", "只有待提交和驳回状态的记录可以修改!");
	public static WorkTicketFireExceptionType WORKTICKET_DELETESTATUS = new WorkTicketFireExceptionType("100105", "只有待提交和废票状态的记录可以删除!");
	public static WorkTicketFireExceptionType WORKTICKET_SUBMITSTATUS = new WorkTicketFireExceptionType("100106", "只有待提交状态的记录可以提交!");
	public static WorkTicketFireExceptionType WORKTICKET_JIANDINGSTATUS = new WorkTicketFireExceptionType("100107", "只有驳回和已执行状态的记录可以鉴定!");
	public static WorkTicketFireExceptionType WORKTICKET_ENDSTATUS = new WorkTicketFireExceptionType("100108", "只有已执行状态的记录可以设置为标准票!");
	public static WorkTicketFireExceptionType WORKTICKET_ONLYSELF = new WorkTicketFireExceptionType("100109", "只可以提交自己的工作票!");
	public static WorkTicketFireExceptionType WORKTICKET_NOTZEROQFR = new WorkTicketFireExceptionType("100110", "签发人应填写的安全措施为空!");
//	public static WorkTicketFireExceptionType WORKTICKET_NOTOUTTHREEQFR = new WorkTicketFireExceptionType("100111", "签发人应填写的安全措施不能大于3条!");
//	public static WorkTicketFireExceptionType WORKTICKET_NOTZEROXKR = new WorkTicketFireExceptionType("100112", "许可人应填写的安全措施为空!");
//	public static WorkTicketFireExceptionType WORKTICKET_NOTOUTTHREEXKR = new WorkTicketFireExceptionType("100113", "许可人应填写的安全措施不能大于3条!");
	private WorkTicketFireExceptionType(String errorCode, String errorMsg) {
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
