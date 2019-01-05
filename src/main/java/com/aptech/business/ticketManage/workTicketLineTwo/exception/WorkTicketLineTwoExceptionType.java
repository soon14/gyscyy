package com.aptech.business.ticketManage.workTicketLineTwo.exception;



public class WorkTicketLineTwoExceptionType {
	/**
	 * @author zzq
	 * @created 2017-10-18 11:50:39
	 * @lastModified 
	 * @history
	 *
	 */
	private String errorCode ;
	private String errorMsg ;	
	
	public static WorkTicketLineTwoExceptionType WORKTICKET_ISEXECUTE = new WorkTicketLineTwoExceptionType("400101", "您有未执行的安全措施，请执行!");
	public static WorkTicketLineTwoExceptionType WORKTICKET_BATHDELETE = new WorkTicketLineTwoExceptionType("400102", "只有待提交和废票状态的可以删除!");
	public static WorkTicketLineTwoExceptionType WORKTICKET_UPDATEDELETE = new WorkTicketLineTwoExceptionType("400103", "该记录已被删除，请刷新列表!");
	public static WorkTicketLineTwoExceptionType WORKTICKET_UPDATESTATUS = new WorkTicketLineTwoExceptionType("400104", "只有待提交和驳回状态的记录可以修改!");
	public static WorkTicketLineTwoExceptionType WORKTICKET_DELETESTATUS = new WorkTicketLineTwoExceptionType("400105", "只有待提交和废票状态的记录可以删除!");
	public static WorkTicketLineTwoExceptionType WORKTICKET_SUBMITSTATUS = new WorkTicketLineTwoExceptionType("400106", "只有待提交状态的记录可以提交!");
	public static WorkTicketLineTwoExceptionType WORKTICKET_JIANDINGSTATUS = new WorkTicketLineTwoExceptionType("400107", "只有驳回和已执行状态的记录可以鉴定!");
	public static WorkTicketLineTwoExceptionType WORKTICKET_ENDSTATUS = new WorkTicketLineTwoExceptionType("400108", "只有已执行状态的记录可以设置为标准票!");
	public static WorkTicketLineTwoExceptionType WORKTICKET_ONLYSELF = new WorkTicketLineTwoExceptionType("400109", "只可以提交自己的工作票!");
	public static WorkTicketLineTwoExceptionType WORKTICKET_NOTZEROQFR = new WorkTicketLineTwoExceptionType("400110", "签发人应填写的安全措施为空!");
//	public static WorkTicketLineTwoExceptionType WORKTICKET_NOTOUTTHREEQFR = new WorkTicketLineTwoExceptionType("400111", "签发人应填写的安全措施不能大于3条!");
//	public static WorkTicketLineTwoExceptionType WORKTICKET_NOTZEROXKR = new WorkTicketLineTwoExceptionType("400112", "许可人应填写的安全措施为空!");
//	public static WorkTicketLineTwoExceptionType WORKTICKET_NOTOUTTHREEXKR = new WorkTicketLineTwoExceptionType("400113", "许可人应填写的安全措施不能大于3条!");
	public static WorkTicketLineTwoExceptionType WORKTICKET_NOCONTROLCARD = new WorkTicketLineTwoExceptionType("400114", "安全风险控制卡，未填写完整!");
	
	public static WorkTicketLineTwoExceptionType WORKTICKET_SAFEONE = new WorkTicketLineTwoExceptionType("400115", "安全措施，应采取的安全措施未填写!");
	public static WorkTicketLineTwoExceptionType WORKTICKET_SAFETWO = new WorkTicketLineTwoExceptionType("400116", "安全措施，其他安全措施和注意事项未填写!");
	private WorkTicketLineTwoExceptionType(String errorCode, String errorMsg) {
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
