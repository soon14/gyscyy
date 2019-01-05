package com.aptech.business.technical.technical.exception;



public class TechnicalExceptionType {
	
	private String errorCode ;
	private String errorMsg ;	
	
	public static TechnicalExceptionType WORKTICKET_BATHDELETE = new TechnicalExceptionType("800102", "只有待提交状态的可以删除!");
	public static TechnicalExceptionType WORKTICKET_UPDATEDELETE = new TechnicalExceptionType("800103", "该记录已被删除，请刷新列表!");
	public static TechnicalExceptionType WORKTICKET_UPDATESTATUS = new TechnicalExceptionType("800104", "只有待提交和驳回状态的记录可以修改!");
	public static TechnicalExceptionType WORKTICKET_DELETESTATUS = new TechnicalExceptionType("800105", "只有待提交状态的记录可以删除!");
	public static TechnicalExceptionType WORKTICKET_SUBMITSTATUS = new TechnicalExceptionType("800106", "只有待提交状态的记录可以提交!");
	public static TechnicalExceptionType WORKTICKET_JIANDINGSTATUS = new TechnicalExceptionType("800107", "只有待负责人填写完成情况状态可以撤销!");
	public static TechnicalExceptionType WORKTICKET_ENDSTATUS = new TechnicalExceptionType("800108", "只有已执行状态的记录可以设置为标准票!");
	public static TechnicalExceptionType WORKTICKET_ONLYSELF = new TechnicalExceptionType("800109", "只可以提交自己的记录!");
	public static TechnicalExceptionType WORKTICKET_NOTZEROQFR = new TechnicalExceptionType("800110", "定期工作至少要有一条记录!");
	public static TechnicalExceptionType WORKTICKET_NOTOUTTHREEQFR = new TechnicalExceptionType("800111", "定期工作计划完成情况未填写!");
//	public static TechnicalExceptionType WORKTICKET_NOTZEROXKR = new TechnicalExceptionType("800112", "临时工作至少要有一条记录!");
//	public static TechnicalExceptionType WORKTICKET_NOTOUTTHREEXKR = new TechnicalExceptionType("800113", "临时工作计划完成情况未填写!");
	public static TechnicalExceptionType WORKTICKET_NOCONTROLCARD = new TechnicalExceptionType("800114", "相同单位，监督专业，年份只能填写一条记录!");
	private TechnicalExceptionType(String errorCode, String errorMsg) {
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
