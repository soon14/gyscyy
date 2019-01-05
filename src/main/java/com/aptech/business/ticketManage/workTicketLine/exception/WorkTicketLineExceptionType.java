package com.aptech.business.ticketManage.workTicketLine.exception;



public class WorkTicketLineExceptionType {
	
	private String errorCode ;
	private String errorMsg ;	
	
	public static WorkTicketLineExceptionType WORKTICKET_ISEXECUTE = new WorkTicketLineExceptionType("300101", "您有未执行的安全措施，请执行!");
	public static WorkTicketLineExceptionType WORKTICKET_BATHDELETE = new WorkTicketLineExceptionType("300102", "只有待提交和废票状态的可以删除!");
	public static WorkTicketLineExceptionType WORKTICKET_UPDATEDELETE = new WorkTicketLineExceptionType("300103", "该记录已被删除，请刷新列表!");
	public static WorkTicketLineExceptionType WORKTICKET_UPDATESTATUS = new WorkTicketLineExceptionType("300104", "只有待提交和驳回状态的记录可以修改!");
	public static WorkTicketLineExceptionType WORKTICKET_DELETESTATUS = new WorkTicketLineExceptionType("300105", "只有待提交和废票状态的记录可以删除!");
	public static WorkTicketLineExceptionType WORKTICKET_SUBMITSTATUS = new WorkTicketLineExceptionType("300106", "只有待提交状态的记录可以提交!");
	public static WorkTicketLineExceptionType WORKTICKET_JIANDINGSTATUS = new WorkTicketLineExceptionType("300107", "只有驳回和已执行状态的记录可以鉴定!");
	public static WorkTicketLineExceptionType WORKTICKET_ENDSTATUS = new WorkTicketLineExceptionType("300108", "只有已执行状态的记录可以设置为标准票!");
	public static WorkTicketLineExceptionType WORKTICKET_ONLYSELF = new WorkTicketLineExceptionType("300109", "只可以提交自己的工作票!");
	public static WorkTicketLineExceptionType WORKTICKET_NOTZEROQFR = new WorkTicketLineExceptionType("300110", "签发人应填写的安全措施为空!");
//	public static WorkTicketLineExceptionType WORKTICKET_NOTOUTTHREEQFR = new WorkTicketLineExceptionType("300111", "签发人应填写的安全措施不能大于3条!");
//	public static WorkTicketLineExceptionType WORKTICKET_NOTZEROXKR = new WorkTicketLineExceptionType("300112", "许可人应填写的安全措施为空!");
//	public static WorkTicketLineExceptionType WORKTICKET_NOTOUTTHREEXKR = new WorkTicketLineExceptionType("300113", "许可人应填写的安全措施不能大于3条!");
	public static WorkTicketLineExceptionType WORKTICKET_NOCONTROLCARD = new WorkTicketLineExceptionType("300114", "安全风险控制卡，未填写完整!");
	
	public static WorkTicketLineExceptionType WORKTICKET_SAFEONE = new WorkTicketLineExceptionType("300115", "安全措施，应拉开的断路器未填写!");
	public static WorkTicketLineExceptionType WORKTICKET_SAFETWO = new WorkTicketLineExceptionType("300116", "安全措施，应拉开的隔离开关未填写!");
	public static WorkTicketLineExceptionType WORKTICKET_SAFETHREE = new WorkTicketLineExceptionType("300117", "安全措施，应投切的相关电源未填写!");
	public static WorkTicketLineExceptionType WORKTICKET_SAFEFOUR = new WorkTicketLineExceptionType("300118", "安全措施，应和上的接地刀闸未填写!");
	public static WorkTicketLineExceptionType WORKTICKET_SAFEFIVE = new WorkTicketLineExceptionType("300119", "安全措施，应设遮挡、应挂标示牌未填写!");
	public static WorkTicketLineExceptionType WORKTICKET_SAFESIX = new WorkTicketLineExceptionType("3001110", "安全措施，其他安全措施和注意事项未填写!");
	public static WorkTicketLineExceptionType WORKTICKET_SAFESEVEN = new WorkTicketLineExceptionType("3001111", "安全措施，应拉开箱变断路器未填写!");
	private WorkTicketLineExceptionType(String errorCode, String errorMsg) {
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
