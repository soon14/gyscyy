package com.aptech.business.safeManage.accident.exception;

//* @created 2018-04-02 09:02:22

public class AccidentExceptionType {
	
	private String errorCode ;
	private String errorMsg ;	
	
	public static AccidentExceptionType WORKTICKET_BATHDELETE = new AccidentExceptionType("1000102", "只有待提交状态的记录可以删除!");
	public static AccidentExceptionType WORKTICKET_UPDATEDELETE = new AccidentExceptionType("1000103", "该记录已被删除，请刷新列表!");
	public static AccidentExceptionType WORKTICKET_UPDATESTATUS = new AccidentExceptionType("1000104", "只有待提交和驳回状态的记录可以修改!");
	public static AccidentExceptionType WORKTICKET_SUBMITSTATUS = new AccidentExceptionType("1000106", "只有待提交状态的记录可以提交!");
	public static AccidentExceptionType WORKTICKET_JIANDINGSTATUS = new AccidentExceptionType("800107", "只有待负责人填写完成情况状态可以撤销!");
	public static AccidentExceptionType WORKTICKET_ENDSTATUS = new AccidentExceptionType("800108", "只有已执行状态的记录可以设置为标准票!");
	public static AccidentExceptionType WORKTICKET_ONLYSELF = new AccidentExceptionType("800109", "只可以提交自己的记录!");
	public static AccidentExceptionType WORKTICKET_NOTZEROQFR = new AccidentExceptionType("800110", "定期工作至少要有一条记录!");
	public static AccidentExceptionType WORKTICKET_NOTOUTTHREEQFR = new AccidentExceptionType("800111", "定期工作计划完成情况未填写!");
	public static AccidentExceptionType WORKTICKET_NOTZEROXKR = new AccidentExceptionType("800112", "临时工作至少要有一条记录!");
	public static AccidentExceptionType WORKTICKET_NOTOUTTHREEXKR = new AccidentExceptionType("800113", "临时工作计划完成情况未填写!");
	public static AccidentExceptionType WORKTICKET_NOCONTROLCARD = new AccidentExceptionType("800114", "相同单位，监督专业，年份只能填写一条记录!");
	private AccidentExceptionType(String errorCode, String errorMsg) {
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
