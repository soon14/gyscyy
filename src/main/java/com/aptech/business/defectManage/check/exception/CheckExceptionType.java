package com.aptech.business.defectManage.check.exception;



public class CheckExceptionType {
	
	private String errorCode ;
	private String errorMsg ;	
	
	public static CheckExceptionType  CHECK_CODE_NULL = new CheckExceptionType("20001", "该记录已删除！");
	public static CheckExceptionType  CHECK_CODE_REPEAT  = new CheckExceptionType("20002", "状态已改变，请刷新！");
	private CheckExceptionType(String errorCode, String errorMsg) {
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
