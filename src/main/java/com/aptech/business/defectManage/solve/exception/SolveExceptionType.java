package com.aptech.business.defectManage.solve.exception;



public class SolveExceptionType {
	
	private String errorCode ;
	private String errorMsg ;	
	
	public static SolveExceptionType SOLVE_CODE_NULL = new SolveExceptionType("20001", "该记录已删除！");
	public static SolveExceptionType SOLVE_CODE_REPEAT  = new SolveExceptionType("20002", "状态已改变，请刷新！");
	private SolveExceptionType(String errorCode, String errorMsg) {
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
