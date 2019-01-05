package com.aptech.business.ticketManage.workTicketTwo.exception;




public class WorkTicketTwoExceptionType {
	
	private String errorCode ;
	private String errorMsg ;	
	
	public static WorkTicketTwoExceptionType WORKTICKET_ISEXECUTE = new WorkTicketTwoExceptionType("100101", "您有未执行的安全措施，请执行!");
	public static WorkTicketTwoExceptionType WORKTICKET_BATHDELETE = new WorkTicketTwoExceptionType("100102", "只有待提交状态的可以删除!");
	public static WorkTicketTwoExceptionType WORKTICKET_UPDATEDELETE = new WorkTicketTwoExceptionType("100103", "该记录已被删除，请刷新列表!");
	public static WorkTicketTwoExceptionType WORKTICKET_UPDATESTATUS = new WorkTicketTwoExceptionType("100104", "只有待提交和驳回状态的记录可以修改!");
	public static WorkTicketTwoExceptionType WORKTICKET_DELETESTATUS = new WorkTicketTwoExceptionType("100105", "只有待提交和废票状态的记录可以删除!");
	public static WorkTicketTwoExceptionType WORKTICKET_SUBMITSTATUS = new WorkTicketTwoExceptionType("100106", "只有待提交状态的记录可以提交!");
	public static WorkTicketTwoExceptionType WORKTICKET_JIANDINGSTATUS = new WorkTicketTwoExceptionType("100107", "只有驳回和已执行状态的记录可以鉴定!");
	public static WorkTicketTwoExceptionType WORKTICKET_ENDSTATUS = new WorkTicketTwoExceptionType("100108", "只有已执行状态的记录可以设置为标准票!");
	public static WorkTicketTwoExceptionType WORKTICKET_ONLYSELF = new WorkTicketTwoExceptionType("100109", "只可以提交自己的工作票!");
	public static WorkTicketTwoExceptionType WORKTICKET_NOTZEROQFR = new WorkTicketTwoExceptionType("100110", "签发人应填写的安全措施为空!");
//	public static WorkTicketTwoExceptionType WORKTICKET_NOTOUTTHREEQFR = new WorkTicketTwoExceptionType("100111", "签发人应填写的安全措施不能大于3条!");
//	public static WorkTicketTwoExceptionType WORKTICKET_NOTZEROXKR = new WorkTicketTwoExceptionType("100112", "许可人应填写的安全措施为空!");
//	public static WorkTicketTwoExceptionType WORKTICKET_NOTOUTTHREEXKR = new WorkTicketTwoExceptionType("100113", "许可人应填写的安全措施不能大于3条!");
	public static WorkTicketTwoExceptionType WORKTICKET_NOCONTROLCARD = new WorkTicketTwoExceptionType("100114", "安全风险控制卡，未填写完整!");
	private WorkTicketTwoExceptionType(String errorCode, String errorMsg) {
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
