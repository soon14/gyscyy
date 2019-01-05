package com.aptech.business.technical.summary.exception;

import com.aptech.framework.exception.request.BadRequestException;

public class SummaryException extends BadRequestException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4086980797104872418L;

	public SummaryException(SummaryExceptionType exceptionType){
		this(exceptionType.getErrorMsg(),exceptionType.getErrorCode());
	}

	public SummaryException(String message) {
		super(message);
	}

	public SummaryException(String message, String errorCode, Throwable cause) {
		super(message, errorCode, cause);
	}

	public SummaryException(String message, String errorCode) {
		super(message, errorCode);
	}

	public SummaryException(String errorCode, Throwable cause) {
		super(errorCode, cause);
	}

}
