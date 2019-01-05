package com.aptech.business.companyTrends.exception;

public class CompanyTrendsExceptionType {
	
	private String errorCode ;
	private String errorMsg ;	
	
	public static CompanyTrendsExceptionType COMPANYTRENDS_BATHDELETE = new CompanyTrendsExceptionType("100001", "只有待提交状态的可以删除!");
//	public static CompanyTrendsExceptionType COMPANYTRENDS_BATHDELETE = new CompanyTrendsExceptionType("100102", "只可以提交自己的公司动态!");
	public static CompanyTrendsExceptionType COMPANYTRENDS_CODE_NULL = new CompanyTrendsExceptionType("100002", "该记录已删除！");
	public static CompanyTrendsExceptionType DEFECT_CODE_REPEAT  = new CompanyTrendsExceptionType("100003", "状态已改变，请刷新！");
	private CompanyTrendsExceptionType(String errorCode, String errorMsg) {
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
