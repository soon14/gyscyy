package com.aptech.business.overhaul.overhaulArrange.exception;



public class OverhaulArrangeExceptionType {
	
	private String errorCode ;
	private String errorMsg ;	
	
	public static OverhaulArrangeExceptionType DEFECT_CODE_NULL = new OverhaulArrangeExceptionType("70031", "该记录已删除！");
	public static OverhaulArrangeExceptionType DEFECT_CODE_REPEAT  = new OverhaulArrangeExceptionType("70032", "状态已改变，请刷新！");
	public static OverhaulArrangeExceptionType DEFECT_CODE_STATUS  = new OverhaulArrangeExceptionType("70033", "该记录不是待提交状态，不能该操作！");
	public static OverhaulArrangeExceptionType DEFECT_CODE_REJECT  = new OverhaulArrangeExceptionType("70034", "该记录驳回状态，请在流程中提交！");
	private OverhaulArrangeExceptionType(String errorCode, String errorMsg) {
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
