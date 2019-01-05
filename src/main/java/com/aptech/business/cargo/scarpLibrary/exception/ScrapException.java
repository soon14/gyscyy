package com.aptech.business.cargo.scarpLibrary.exception;

import com.aptech.framework.exception.request.BadRequestException;

public class ScrapException extends BadRequestException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4086980797104872418L;

	public ScrapException(ScrapExceptionType exceptionType){
		this(exceptionType.getErrorMsg(),exceptionType.getErrorCode());
	}

	public ScrapException(String message) {
		super(message);
	}

	public ScrapException(String message, String errorCode, Throwable cause) {
		super(message, errorCode, cause);
	}

	public ScrapException(String message, String errorCode) {
		super(message, errorCode);
	}

	public ScrapException(String errorCode, Throwable cause) {
		super(errorCode, cause);
	}

}
