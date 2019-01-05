package com.aptech.business.technical.technical.exception;

import com.aptech.framework.exception.request.BadRequestException;

public class TechnicalException extends BadRequestException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4086980797104872418L;

	public TechnicalException(TechnicalExceptionType exceptionType){
		this(exceptionType.getErrorMsg(),exceptionType.getErrorCode());
	}

	public TechnicalException(String message) {
		super(message);
	}

	public TechnicalException(String message, String errorCode, Throwable cause) {
		super(message, errorCode, cause);
	}

	public TechnicalException(String message, String errorCode) {
		super(message, errorCode);
	}

	public TechnicalException(String errorCode, Throwable cause) {
		super(errorCode, cause);
	}

}
