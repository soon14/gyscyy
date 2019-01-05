package com.aptech.business.overhaul.overhaulFile.exception;


public class OverhaulFileExceptionType {
	
	
	public static OverhaulFileExceptionType FILE_TYPE = new OverhaulFileExceptionType("600003", "没有找到该文件！");
	
	private String errorCode ;
	private String errorMsg ;
	
	
	public OverhaulFileExceptionType(String errorCode, String errorMsg) {
		super();
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	
	
	
}
