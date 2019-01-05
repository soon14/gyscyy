package com.aptech.business.ticketManage.workElectric.exception;

import com.aptech.framework.exception.request.BadRequestException;

public class WorkElectricException extends BadRequestException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4086980797104872418L;

	public WorkElectricException(WorkElectricExceptionType exceptionType){
		this(exceptionType.getErrorMsg(),exceptionType.getErrorCode());
	}

	public WorkElectricException(String message) {
		super(message);
	}

	public WorkElectricException(String message, String errorCode, Throwable cause) {
		super(message, errorCode, cause);
	}

	public WorkElectricException(String message, String errorCode) {
		super(message, errorCode);
	}

	public WorkElectricException(String errorCode, Throwable cause) {
		super(errorCode, cause);
	}

}
