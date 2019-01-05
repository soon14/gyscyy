package com.aptech.business.ticketManage.workHelpSafe.exception;



public class WorkHelpSafeExceptionType {
	
	private String errorCode ;
	private String errorMsg ;	
	
	public static WorkHelpSafeExceptionType WORKTICKET_ISEXECUTE = new WorkHelpSafeExceptionType("700101", "您有未执行的操作项目，请执行!");
	public static WorkHelpSafeExceptionType WORKTICKET_BATHDELETE = new WorkHelpSafeExceptionType("700102", "只有待提交和废票状态的可以删除!");
	public static WorkHelpSafeExceptionType WORKTICKET_UPDATEDELETE = new WorkHelpSafeExceptionType("700103", "该记录已被删除，请刷新列表!");
	public static WorkHelpSafeExceptionType WORKTICKET_UPDATESTATUS = new WorkHelpSafeExceptionType("700104", "只有待提交和驳回状态的记录可以修改!");
	public static WorkHelpSafeExceptionType WORKTICKET_DELETESTATUS = new WorkHelpSafeExceptionType("700105", "只有待提交和废票状态的记录可以删除!");
	public static WorkHelpSafeExceptionType WORKTICKET_SUBMITSTATUS = new WorkHelpSafeExceptionType("700106", "只有待提交状态的记录可以提交!");
	public static WorkHelpSafeExceptionType WORKTICKET_JIANDINGSTATUS = new WorkHelpSafeExceptionType("700107", "只有驳回和已执行状态的记录可以鉴定!");
	public static WorkHelpSafeExceptionType WORKTICKET_ENDSTATUS = new WorkHelpSafeExceptionType("700108", "只有已执行状态的记录可以设置为标准票!");
	public static WorkHelpSafeExceptionType WORKTICKET_ONLYSELF = new WorkHelpSafeExceptionType("700109", "只可以提交自己的工作票!");
	public static WorkHelpSafeExceptionType WORKTICKET_NOTZEROQFR = new WorkHelpSafeExceptionType("700110", "签发人应填写的安全措施为空!");
//	public static WorkHelpSafeExceptionType WORKTICKET_NOTOUTTHREEQFR = new WorkHelpSafeExceptionType("700111", "签发人应填写的安全措施不能大于3条!");
//	public static WorkHelpSafeExceptionType WORKTICKET_NOTZEROXKR = new WorkHelpSafeExceptionType("700112", "许可人应填写的安全措施为空!");
//	public static WorkHelpSafeExceptionType WORKTICKET_NOTOUTTHREEXKR = new WorkHelpSafeExceptionType("700113", "许可人应填写的安全措施不能大于3条!");
	public static WorkHelpSafeExceptionType WORKTICKET_NOCONTROLCARD = new WorkHelpSafeExceptionType("700114", "安全风险控制卡，未填写完整!");
	
	public static WorkHelpSafeExceptionType WORKTICKET_SAFEONE = new WorkHelpSafeExceptionType("700115", "操作项目未填写!");
	private WorkHelpSafeExceptionType(String errorCode, String errorMsg) {
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
