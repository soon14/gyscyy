package com.aptech.business.run.protect.exception;

import com.aptech.framework.exception.request.BadRequestException;

public class ProtectException extends BadRequestException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4086980797104872418L;

	public ProtectException(ProtectExceptionType exceptionType){
		this(exceptionType.getErrorMsg(),exceptionType.getErrorCode());
	}

	public ProtectException(String message) {
		super(message);
	}

	public ProtectException(String message, String errorCode, Throwable cause) {
		super(message, errorCode, cause);
	}

	public ProtectException(String message, String errorCode) {
		super(message, errorCode);
	}

	public ProtectException(String errorCode, Throwable cause) {
		super(errorCode, cause);
	}

}
