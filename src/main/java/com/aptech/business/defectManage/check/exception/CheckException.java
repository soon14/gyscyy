package com.aptech.business.defectManage.check.exception;

import com.aptech.framework.exception.request.BadRequestException;

public class CheckException extends BadRequestException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4086980797104872418L;

	public CheckException(CheckExceptionType exceptionType){
		this(exceptionType.getErrorMsg(),exceptionType.getErrorCode());
	}

	public CheckException(String message) {
		super(message);
	}

	public CheckException(String message, String errorCode, Throwable cause) {
		super(message, errorCode, cause);
	}

	public CheckException(String message, String errorCode) {
		super(message, errorCode);
	}

	public CheckException(String errorCode, Throwable cause) {
		super(errorCode, cause);
	}

}
