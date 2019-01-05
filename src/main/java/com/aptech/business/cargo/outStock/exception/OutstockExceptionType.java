package com.aptech.business.cargo.outStock.exception;



public class OutstockExceptionType {
	
	private String errorCode ;
	private String errorMsg ;	
	
	public static OutstockExceptionType OUTSTOCK_CODE_NULL = new OutstockExceptionType("50201", "该记录已删除！");
	public static OutstockExceptionType OUTSTOCK_CODE_REPEAT  = new OutstockExceptionType("50202", "状态已改变，请刷新！");
	public static OutstockExceptionType OUTSTOCK_CODE_STATUS  = new OutstockExceptionType("50203", "该记录不是待提交或驳回状态，不能该操作！");
	public static OutstockExceptionType OUTSTOCK_CODE_REJECT  = new OutstockExceptionType("50204", "该记录驳回状态，请在流程中提交！");
	private OutstockExceptionType(String errorCode, String errorMsg) {
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
