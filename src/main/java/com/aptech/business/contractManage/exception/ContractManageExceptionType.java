package com.aptech.business.contractManage.exception;



public class ContractManageExceptionType {
	
	private String errorCode ;
	private String errorMsg ;	
	
	public static ContractManageExceptionType CONTRACT_MANAGE_CODE_NULL = new ContractManageExceptionType("50001", "该记录已删除！");
	public static ContractManageExceptionType CONTRACT_MANAGE_CODE_REPEAT  = new ContractManageExceptionType("50002", "状态已改变，请刷新！");
	public static ContractManageExceptionType CONTRACT_MANAGE_CODE_STATUS  = new ContractManageExceptionType("50003", "该记录不是待提交或驳回状态，不能该操作！");
	public static ContractManageExceptionType CONTRACT_MANAGE_CODE_REJECT  = new ContractManageExceptionType("50004", "该记录驳回状态，请在流程中提交！");
	private ContractManageExceptionType(String errorCode, String errorMsg) {
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
