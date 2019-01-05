package com.aptech.business.fileLearn.exception;



public class FileLearnExceptionType {
	
	private String errorCode ;
	private String errorMsg ;	
	
	public static FileLearnExceptionType FILE_LEARN_CODE_NULL = new FileLearnExceptionType("50001", "该记录已删除！");
	public static FileLearnExceptionType FILE_LEARN_CODE_REPEAT  = new FileLearnExceptionType("50002", "状态已改变，请刷新！");
	public static FileLearnExceptionType FILE_LEARN_CODE_STATUS  = new FileLearnExceptionType("50003", "该记录不是待提交或驳回状态，不能该操作！");
	public static FileLearnExceptionType FILE_LEARN_CODE_REJECT  = new FileLearnExceptionType("50004", "该记录驳回状态，请在流程中提交！");
	private FileLearnExceptionType(String errorCode, String errorMsg) {
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
