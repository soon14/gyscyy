package com.aptech.business.defectManage.appraisal.exception;



public class AppraisalExceptionType {
	
	private String errorCode ;
	private String errorMsg ;	
	
	public static AppraisalExceptionType APPRAISAL_CODE_NULL = new AppraisalExceptionType("20001", "该记录已删除！");
	public static AppraisalExceptionType APPRAISAL_CODE_REPEAT  = new AppraisalExceptionType("20002", "状态已改变，请刷新！");
	public static AppraisalExceptionType APPRAISAL_CODE_ISSETYES = new AppraisalExceptionType("20003", "已设置典型票，不能在设置！");
	public static AppraisalExceptionType APPRAISAL_CODE_RESULTS= new AppraisalExceptionType("20004", "流程未通过，不能设置操作票！");
	private AppraisalExceptionType(String errorCode, String errorMsg) {
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
