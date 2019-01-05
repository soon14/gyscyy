package com.aptech.business.ticketManage.workLineTwo.exception;



public class WorkLineTwoExceptionType {
	/**
	 * 
	 * 电气工作票应用管理服务接口
	 *
	 * @author zzq
	 * @created 2017-10-18 17:12:46
	 * @lastModified 
	 * @history
	 *
	 */
	private String errorCode ;
	private String errorMsg ;	
	
	public static WorkLineTwoExceptionType DEFECT_CODE_NULL = new WorkLineTwoExceptionType("40001", "您有未执行的安全措施，请执行!");
	private WorkLineTwoExceptionType(String errorCode, String errorMsg) {
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
