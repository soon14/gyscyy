package com.aptech.business.technical.summary.exception;



public class SummaryExceptionType {
	
	private String errorCode ;
	private String errorMsg ;	
	
	public static SummaryExceptionType WORKTICKET_BATHDELETE = new SummaryExceptionType("1000102", "只有待提交状态的记录可以删除!");
	public static SummaryExceptionType WORKTICKET_UPDATEDELETE = new SummaryExceptionType("1000103", "该记录已被删除，请刷新列表!");
	public static SummaryExceptionType WORKTICKET_UPDATESTATUS = new SummaryExceptionType("1000104", "只有待提交和驳回状态的记录可以修改!");
	public static SummaryExceptionType WORKTICKET_SUBMITSTATUS = new SummaryExceptionType("1000106", "只有待提交状态的记录可以提交!");
	public static SummaryExceptionType WORKTICKET_JIANDINGSTATUS = new SummaryExceptionType("800107", "只有待负责人填写完成情况状态可以撤销!");
	public static SummaryExceptionType WORKTICKET_ENDSTATUS = new SummaryExceptionType("800108", "只有已执行状态的记录可以设置为标准票!");
	public static SummaryExceptionType WORKTICKET_ONLYSELF = new SummaryExceptionType("800109", "只可以提交自己的记录!");
	public static SummaryExceptionType WORKTICKET_NOTZEROQFR = new SummaryExceptionType("800110", "定期工作至少要有一条记录!");
	public static SummaryExceptionType WORKTICKET_NOTOUTTHREEQFR = new SummaryExceptionType("800111", "定期工作计划完成情况未填写!");
	public static SummaryExceptionType WORKTICKET_NOTZEROXKR = new SummaryExceptionType("800112", "临时工作至少要有一条记录!");
	public static SummaryExceptionType WORKTICKET_NOTOUTTHREEXKR = new SummaryExceptionType("800113", "临时工作计划完成情况未填写!");
	public static SummaryExceptionType WORKTICKET_NOCONTROLCARD = new SummaryExceptionType("800114", "相同单位，监督专业，年份只能填写一条记录!");
	private SummaryExceptionType(String errorCode, String errorMsg) {
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
