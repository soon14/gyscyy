package com.aptech.business.ticketManage.workFireTwo.exception;

import com.aptech.framework.exception.request.BadRequestException;

public class WorkFireTwoException extends BadRequestException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4086980797104872418L;

	public WorkFireTwoException(WorkFireTwoExceptionType exceptionType){
		this(exceptionType.getErrorMsg(),exceptionType.getErrorCode());
	}

	public WorkFireTwoException(String message) {
		super(message);
	}

	public WorkFireTwoException(String message, String errorCode, Throwable cause) {
		super(message, errorCode, cause);
	}

	public WorkFireTwoException(String message, String errorCode) {
		super(message, errorCode);
	}

	public WorkFireTwoException(String errorCode, Throwable cause) {
		super(errorCode, cause);
	}

}
