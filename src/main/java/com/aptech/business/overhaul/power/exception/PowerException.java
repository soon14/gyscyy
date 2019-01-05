package com.aptech.business.overhaul.power.exception;

import com.aptech.framework.exception.request.BadRequestException;

public class PowerException extends BadRequestException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PowerException(PowerExceptionType exceptionType){
		this(exceptionType.getErrorMsg(),exceptionType.getErrorCode());
	}

	public PowerException(String message) {
		super(message);
	}

	public PowerException(String message, String errorCode, Throwable cause) {
		super(message, errorCode, cause);
	}

	public PowerException(String message, String errorCode) {
		super(message, errorCode);
	}

	public PowerException(String errorCode, Throwable cause) {
		super(errorCode, cause);
	}
}
