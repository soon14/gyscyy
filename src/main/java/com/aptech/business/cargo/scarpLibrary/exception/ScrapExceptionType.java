package com.aptech.business.cargo.scarpLibrary.exception;



public class ScrapExceptionType {
	
	private String errorCode ;
	private String errorMsg ;	
	
	public static ScrapExceptionType SCRAP_CODE_NULL = new ScrapExceptionType("50001", "该记录已删除！");
	public static ScrapExceptionType SCRAP_CODE_REPEAT  = new ScrapExceptionType("50002", "状态已改变，请刷新！");
	public static ScrapExceptionType SCRAP_CODE_STATUS  = new ScrapExceptionType("50003", "该记录不是待提交或驳回状态，不能该操作！");
	public static ScrapExceptionType SCRAP_CODE_REJECT  = new ScrapExceptionType("50004", "该记录驳回状态，请在流程中提交！");
	private ScrapExceptionType(String errorCode, String errorMsg) {
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
