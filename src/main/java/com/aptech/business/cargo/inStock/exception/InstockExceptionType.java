package com.aptech.business.cargo.inStock.exception;



public class InstockExceptionType {
	
	private String errorCode ;
	private String errorMsg ;	
	
	public static InstockExceptionType INSTOCK_CODE_NULL = new InstockExceptionType("50001", "该记录已删除！");
	public static InstockExceptionType INSTOCK_CODE_REPEAT  = new InstockExceptionType("50002", "状态已改变，请刷新！");
	public static InstockExceptionType INSTOCK_CODE_STATUS  = new InstockExceptionType("50003", "该记录不是待提交或驳回状态，不能该操作！");
	public static InstockExceptionType INSTOCK_CODE_REJECT  = new InstockExceptionType("50004", "该记录驳回状态，请在流程中提交！");
	private InstockExceptionType(String errorCode, String errorMsg) {
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
