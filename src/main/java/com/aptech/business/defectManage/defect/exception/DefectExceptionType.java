package com.aptech.business.defectManage.defect.exception;



public class DefectExceptionType {
	
	private String errorCode ;
	private String errorMsg ;	
	
	public static DefectExceptionType DEFECT_CODE_NULL = new DefectExceptionType("20001", "该记录已删除！");
	public static DefectExceptionType DEFECT_CODE_REPEAT  = new DefectExceptionType("20002", "状态已改变，请刷新！");
	public static DefectExceptionType DEFECT_CODE_STATUS  = new DefectExceptionType("20003", "该记录不是待提交或驳回状态，不能该操作！");
	public static DefectExceptionType DEFECT_CODE_REJECT  = new DefectExceptionType("20004", "该记录驳回状态，请在流程中提交！");
	public static DefectExceptionType DEFECT_CODE_STATUS_2  = new DefectExceptionType("20005", "该记录不是待提交状态，不能该操作！");
	public static DefectExceptionType EDITION_STATUS  = new DefectExceptionType("20006", "存在相同版本号！");
	private DefectExceptionType(String errorCode, String errorMsg) {
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
