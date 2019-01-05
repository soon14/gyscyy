package com.aptech.business.ticketManage.workTicket.exception;



public class WorkTicketExceptionType {
	
	private String errorCode ;
	private String errorMsg ;	
	
	public static WorkTicketExceptionType WORKTICKET_ISEXECUTE = new WorkTicketExceptionType("100101", "您有未执行的安全措施，请执行!");
	public static WorkTicketExceptionType WORKTICKET_BATHDELETE = new WorkTicketExceptionType("100102", "只有待提交状态的可以删除!");
	public static WorkTicketExceptionType WORKTICKET_UPDATEDELETE = new WorkTicketExceptionType("100103", "该记录已被删除，请刷新列表!");
	public static WorkTicketExceptionType WORKTICKET_UPDATESTATUS = new WorkTicketExceptionType("100104", "只有待提交和驳回状态的记录可以修改!");
	public static WorkTicketExceptionType WORKTICKET_DELETESTATUS = new WorkTicketExceptionType("100105", "只有待提交和废票状态的记录可以删除!");
	public static WorkTicketExceptionType WORKTICKET_SUBMITSTATUS = new WorkTicketExceptionType("100106", "只有待提交状态的记录可以提交!");
	public static WorkTicketExceptionType WORKTICKET_JIANDINGSTATUS = new WorkTicketExceptionType("100107", "只有驳回和已执行状态的记录可以鉴定!");
	public static WorkTicketExceptionType WORKTICKET_ENDSTATUS = new WorkTicketExceptionType("100108", "只有已执行状态的记录可以设置为标准票!");
	public static WorkTicketExceptionType WORKTICKET_ONLYSELF = new WorkTicketExceptionType("100109", "只可以提交自己的工作票!");
	public static WorkTicketExceptionType WORKTICKET_NOTZEROQFR = new WorkTicketExceptionType("100110", "签发人应填写的安全措施为空!");
//	public static WorkTicketExceptionType WORKTICKET_NOTOUTTHREEQFR = new WorkTicketExceptionType("100111", "签发人应填写的安全措施不能大于3条!");
//	public static WorkTicketExceptionType WORKTICKET_NOTZEROXKR = new WorkTicketExceptionType("100112", "许可人应填写的安全措施为空!");
//	public static WorkTicketExceptionType WORKTICKET_NOTOUTTHREEXKR = new WorkTicketExceptionType("100113", "许可人应填写的安全措施不能大于3条!");
	public static WorkTicketExceptionType WORKTICKET_NOCONTROLCARD = new WorkTicketExceptionType("100114", "安全风险控制卡，未填写完整!");
	public static WorkTicketExceptionType WORKTICKET_ISHAVE = new WorkTicketExceptionType("100115", "当前工作负责人已有一张正在进行的工作票!");
	private WorkTicketExceptionType(String errorCode, String errorMsg) {
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
