package com.aptech.business.ticketManage.workTicketFireTwo.exception;



public class WorkTicketFireTwoExceptionType {
	
	private String errorCode ;
	private String errorMsg ;	
	
	public static WorkTicketFireTwoExceptionType WORKTICKET_ISEXECUTE = new WorkTicketFireTwoExceptionType("100101", "您有未执行的安全措施，请执行!");
	public static WorkTicketFireTwoExceptionType WORKTICKET_BATHDELETE = new WorkTicketFireTwoExceptionType("100102", "只有待提交和废票状态的可以删除!");
	public static WorkTicketFireTwoExceptionType WORKTICKET_UPDATEDELETE = new WorkTicketFireTwoExceptionType("100103", "该记录已被删除，请刷新列表!");
	public static WorkTicketFireTwoExceptionType WORKTICKET_UPDATESTATUS = new WorkTicketFireTwoExceptionType("100104", "只有待提交和驳回状态的记录可以修改!");
	public static WorkTicketFireTwoExceptionType WORKTICKET_DELETESTATUS = new WorkTicketFireTwoExceptionType("100105", "只有待提交和废票状态的记录可以删除!");
	public static WorkTicketFireTwoExceptionType WORKTICKET_SUBMITSTATUS = new WorkTicketFireTwoExceptionType("100106", "只有待提交状态的记录可以提交!");
	public static WorkTicketFireTwoExceptionType WORKTICKET_JIANDINGSTATUS = new WorkTicketFireTwoExceptionType("100107", "只有驳回和已执行状态的记录可以鉴定!");
	public static WorkTicketFireTwoExceptionType WORKTICKET_ENDSTATUS = new WorkTicketFireTwoExceptionType("100108", "只有已执行状态的记录可以设置为标准票!");
	public static WorkTicketFireTwoExceptionType WORKTICKET_ONLYSELF = new WorkTicketFireTwoExceptionType("100109", "只可以提交自己的工作票!");
	public static WorkTicketFireTwoExceptionType WORKTICKET_NOTZEROQFR = new WorkTicketFireTwoExceptionType("100110", "签发人应填写的安全措施为空!");
//	public static WorkTicketFireTwoExceptionType WORKTICKET_NOTOUTTHREEQFR = new WorkTicketFireTwoExceptionType("100111", "签发人应填写的安全措施不能大于3条!");
//	public static WorkTicketFireTwoExceptionType WORKTICKET_NOTZEROXKR = new WorkTicketFireTwoExceptionType("100112", "许可人应填写的安全措施为空!");
//	public static WorkTicketFireTwoExceptionType WORKTICKET_NOTOUTTHREEXKR = new WorkTicketFireTwoExceptionType("100113", "许可人应填写的安全措施不能大于3条!");
	private WorkTicketFireTwoExceptionType(String errorCode, String errorMsg) {
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
