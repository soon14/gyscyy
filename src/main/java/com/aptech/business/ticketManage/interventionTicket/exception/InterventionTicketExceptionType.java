package com.aptech.business.ticketManage.interventionTicket.exception;



public class InterventionTicketExceptionType {
	
	private String errorCode ;
	private String errorMsg ;	
	
	public static InterventionTicketExceptionType WORKTICKET_ISEXECUTE = new InterventionTicketExceptionType("10001", "您有未执行的安全措施，请执行!");
	public static InterventionTicketExceptionType WORKTICKET_BATHDELETE = new InterventionTicketExceptionType("10002", "只有待提交和废票状态的可以删除!");
	public static InterventionTicketExceptionType WORKTICKET_UPDATEDELETE = new InterventionTicketExceptionType("10003", "该记录已被删除，请刷新列表!");
	public static InterventionTicketExceptionType WORKTICKET_UPDATESTATUS = new InterventionTicketExceptionType("10004", "只有待提交和驳回状态的记录可以修改!");
	public static InterventionTicketExceptionType WORKTICKET_DELETESTATUS = new InterventionTicketExceptionType("10005", "只有待提交和废票状态的记录可以删除!");
	public static InterventionTicketExceptionType WORKTICKET_SUBMITSTATUS = new InterventionTicketExceptionType("10006", "只有待提交状态的记录可以提交!");
	public static InterventionTicketExceptionType WORKTICKET_JIANDINGSTATUS = new InterventionTicketExceptionType("10007", "只有驳回和已执行状态的记录可以鉴定!");
	public static InterventionTicketExceptionType WORKTICKET_ENDSTATUS = new InterventionTicketExceptionType("10008", "只有已执行状态的记录可以设置为标准票!");
	public static InterventionTicketExceptionType WORKTICKET_SAFE_NULL = new InterventionTicketExceptionType("10009", "未填写安全措施，不能提交!");
	public static InterventionTicketExceptionType WORKTICKET_CARDSORT_NULL = new InterventionTicketExceptionType("10010", "未填写安全风险控制卡，不能提交!");
	public static InterventionTicketExceptionType WORKTICKET_CONTROLCARDRISK_NULL = new InterventionTicketExceptionType("10011", "未填写安全风险控制卡，不能提交!");
	private InterventionTicketExceptionType(String errorCode, String errorMsg) {
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
