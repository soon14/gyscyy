package com.aptech.business.cargo.inStock.exception;

import com.aptech.framework.exception.request.BadRequestException;

public class InstockException extends BadRequestException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4086980797104872418L;

	public InstockException(InstockExceptionType exceptionType){
		this(exceptionType.getErrorMsg(),exceptionType.getErrorCode());
	}

	public InstockException(String message) {
		super(message);
	}

	public InstockException(String message, String errorCode, Throwable cause) {
		super(message, errorCode, cause);
	}

	public InstockException(String message, String errorCode) {
		super(message, errorCode);
	}

	public InstockException(String errorCode, Throwable cause) {
		super(errorCode, cause);
	}

}
