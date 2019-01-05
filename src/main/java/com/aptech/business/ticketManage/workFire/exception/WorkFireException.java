package com.aptech.business.ticketManage.workFire.exception;

import com.aptech.framework.exception.request.BadRequestException;

public class WorkFireException extends BadRequestException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4086980797104872418L;

	public WorkFireException(WorkFireExceptionType exceptionType){
		this(exceptionType.getErrorMsg(),exceptionType.getErrorCode());
	}

	public WorkFireException(String message) {
		super(message);
	}

	public WorkFireException(String message, String errorCode, Throwable cause) {
		super(message, errorCode, cause);
	}

	public WorkFireException(String message, String errorCode) {
		super(message, errorCode);
	}

	public WorkFireException(String errorCode, Throwable cause) {
		super(errorCode, cause);
	}

}
