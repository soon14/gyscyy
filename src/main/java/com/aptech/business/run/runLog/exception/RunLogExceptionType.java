package com.aptech.business.run.runLog.exception;



public class RunLogExceptionType {
	
	private String errorCode ;
	private String errorMsg ;	
	
	public static RunLogExceptionType RUNLOG_CODE_GRDONE = new RunLogExceptionType("30001", "交接班已完成不可删除！");
	public static RunLogExceptionType RUNLOG_CODE_REPEAT  = new RunLogExceptionType("30002", "状态已改变，请刷新！");
	public static RunLogExceptionType RUNLOG_CODE_GPWD  = new RunLogExceptionType("30003", "交班密码不正确！");
    public static RunLogExceptionType RUNLOG_CODE_RPWD  = new RunLogExceptionType("30004", "接班密码不正确！");	
    public static RunLogExceptionType RUNLOG_CODE_JOIN  = new RunLogExceptionType("30005", "登录人不是交班负责人不可执行此操作！"); 
    public static RunLogExceptionType RUNLOG_CODE_DATE  = new RunLogExceptionType("30006", "该班次当日已接班！"); 
    public static RunLogExceptionType RUNLOG_CODE_GIVEDATE  = new RunLogExceptionType("30007", "该班次当日已交班！"); 

	private RunLogExceptionType(String errorCode, String errorMsg) {
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
