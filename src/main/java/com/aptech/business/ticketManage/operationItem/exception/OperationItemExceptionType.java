package com.aptech.business.ticketManage.operationItem.exception;



public class OperationItemExceptionType {
	
	private String errorCode ;
	private String errorMsg ;	
	
	public static OperationItemExceptionType OPERATIONITEM_CODE_NULL = new OperationItemExceptionType("20001", "该记录已删除！");
	public static OperationItemExceptionType OPERATIONITEM_CODE_REPEAT  = new OperationItemExceptionType("20002", "状态已改变，请刷新！");
	public static OperationItemExceptionType OPERATIONITEM_CODE_STATUS  = new OperationItemExceptionType("20003", "该记录不是待提交或驳回状态，不能该操作！");
	public static OperationItemExceptionType OPERATIONITEM_CODE_REJECT  = new OperationItemExceptionType("20004", "该记录驳回状态，请在流程中提交！");
	public static OperationItemExceptionType OPERATIONITEM_REASON_NULL  = new OperationItemExceptionType("20005", "终止原因不能为空");
	private OperationItemExceptionType(String errorCode, String errorMsg) {
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
