package com.aptech.business.run.runLog.exception;

import com.aptech.framework.exception.request.BadRequestException;

public class RunLogException extends BadRequestException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4086980797104872418L;

	public RunLogException(RunLogExceptionType exceptionType){
		this(exceptionType.getErrorMsg(),exceptionType.getErrorCode());
	}

	public RunLogException(String message) {
		super(message);
	}

	public RunLogException(String message, String errorCode, Throwable cause) {
		super(message, errorCode, cause);
	}

	public RunLogException(String message, String errorCode) {
		super(message, errorCode);
	}

	public RunLogException(String errorCode, Throwable cause) {
		super(errorCode, cause);
	}

}
