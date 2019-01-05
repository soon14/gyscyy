package com.aptech.business.ticketManage.operationDanger.exception;



public class OperationDangerExceptionType {
	
	private String errorCode ;
	private String errorMsg ;	
	
	public static OperationDangerExceptionType OPERATIONDANGER_CODE_NULL = new OperationDangerExceptionType("20001", "该记录已删除！");
	public static OperationDangerExceptionType OPERATIONDANGER_CODE_REPEAT  = new OperationDangerExceptionType("20002", "状态已改变，请刷新！");
	public static OperationDangerExceptionType OPERATIONDANGER_CODE_STATUS  = new OperationDangerExceptionType("20003", "该记录不是待提交或驳回状态，不能该操作！");
	public static OperationDangerExceptionType OPERATIONDANGER_CODE_REJECT  = new OperationDangerExceptionType("20004", "该记录驳回状态，请在流程中提交！");
	private OperationDangerExceptionType(String errorCode, String errorMsg) {
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
