package com.aptech.business.ticketManage.operationTicket.exception;



public class OperationTicketExceptionType {
	
	private String errorCode ;
	private String errorMsg ;	
	
	public static OperationTicketExceptionType OPERATIONTICKET_CODE_NULL = new OperationTicketExceptionType("20001", "该记录已删除！");
	public static OperationTicketExceptionType OPERATIONTICKET_CODE_REPEAT  = new OperationTicketExceptionType("20002", "状态已改变，请刷新！");
	public static OperationTicketExceptionType OPERATIONTICKET_CODE_STATUS  = new OperationTicketExceptionType("20003", "该记录不是待提交或驳回状态，不能该操作！");
	public static OperationTicketExceptionType OPERATIONTICKET_CODE_REJECT  = new OperationTicketExceptionType("20004", "该记录驳回状态，请在流程中提交！");
	public static OperationTicketExceptionType OPERATIONTICKET_CODE_ISSETYES = new OperationTicketExceptionType("20005", "已设置典型票，不能在设置！");
	public static OperationTicketExceptionType OPERATIONTICKET_CODE_RESULTS= new OperationTicketExceptionType("20006", "流程未通过，不能设置操作票！");
	public static OperationTicketExceptionType OPERATIONTICKET_ITEM_NULL  = new OperationTicketExceptionType("20007", "请填写操作项目！");
	public static OperationTicketExceptionType WORKTICKET_ISHAVE  = new OperationTicketExceptionType("20008", "当前工作负责人已有一张正在进行的操作票！");
	public static OperationTicketExceptionType SFKYYYDXP = new OperationTicketExceptionType("20009", "您的职务没有引用典型票的权限！");
	private OperationTicketExceptionType(String errorCode, String errorMsg) {
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
