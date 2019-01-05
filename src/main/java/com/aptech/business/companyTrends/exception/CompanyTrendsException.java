package com.aptech.business.companyTrends.exception;

import com.aptech.framework.exception.request.BadRequestException;

/**
* 公司动态流程错误提示信息
* @author ly
* @date 2018年4月11日 上午9:55:20 
*/
public class CompanyTrendsException extends BadRequestException {

	private static final long serialVersionUID = 4086980797104872418L;

	public CompanyTrendsException(CompanyTrendsExceptionType exceptionType){
		this(exceptionType.getErrorMsg(),exceptionType.getErrorCode());
	}

	public CompanyTrendsException(String message) {
		super(message);
	}

	public CompanyTrendsException(String message, String errorCode, Throwable cause) {
		super(message, errorCode, cause);
	}

	public CompanyTrendsException(String message, String errorCode) {
		super(message, errorCode);
	}

	public CompanyTrendsException(String errorCode, Throwable cause) {
		super(errorCode, cause);
	}

}
