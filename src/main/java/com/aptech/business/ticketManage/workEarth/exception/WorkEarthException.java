package com.aptech.business.ticketManage.workEarth.exception;

import com.aptech.framework.exception.request.BadRequestException;

public class WorkEarthException extends BadRequestException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4086980797104872418L;

	public WorkEarthException(WorkEarthExceptionType exceptionType){
		this(exceptionType.getErrorMsg(),exceptionType.getErrorCode());
	}

	public WorkEarthException(String message) {
		super(message);
	}

	public WorkEarthException(String message, String errorCode, Throwable cause) {
		super(message, errorCode, cause);
	}

	public WorkEarthException(String message, String errorCode) {
		super(message, errorCode);
	}

	public WorkEarthException(String errorCode, Throwable cause) {
		super(errorCode, cause);
	}

}
