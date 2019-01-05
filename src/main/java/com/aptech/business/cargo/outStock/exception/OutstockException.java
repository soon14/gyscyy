package com.aptech.business.cargo.outStock.exception;

import com.aptech.framework.exception.request.BadRequestException;

public class OutstockException extends BadRequestException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4086980797104872418L;

	public OutstockException(OutstockExceptionType exceptionType){
		this(exceptionType.getErrorMsg(),exceptionType.getErrorCode());
	}

	public OutstockException(String message) {
		super(message);
	}

	public OutstockException(String message, String errorCode, Throwable cause) {
		super(message, errorCode, cause);
	}

	public OutstockException(String message, String errorCode) {
		super(message, errorCode);
	}

	public OutstockException(String errorCode, Throwable cause) {
		super(errorCode, cause);
	}

}
