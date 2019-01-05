package com.aptech.business.overhaul.overhaulLog.exception;



public class OverhaulLogExceptionType {
	
	private String errorCode ;
	private String errorMsg ;	
	
	public static OverhaulLogExceptionType DEFECT_CODE_NULL = new OverhaulLogExceptionType("70031", "该记录已删除！");
	public static OverhaulLogExceptionType DEFECT_CODE_REPEAT  = new OverhaulLogExceptionType("70032", "状态已改变，请刷新！");
	public static OverhaulLogExceptionType DEFECT_CODE_STATUS  = new OverhaulLogExceptionType("70033", "该记录不是待提交或驳回状态，不能该操作！");
	public static OverhaulLogExceptionType DEFECT_CODE_REJECT  = new OverhaulLogExceptionType("70034", "该记录驳回状态，请在流程中提交！");
	public static OverhaulLogExceptionType ADD_DATE_REPEAT  = new OverhaulLogExceptionType("70035", "每天只能新增一条检修日志！");
	private OverhaulLogExceptionType(String errorCode, String errorMsg) {
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
