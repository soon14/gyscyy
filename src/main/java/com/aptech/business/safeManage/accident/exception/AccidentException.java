package com.aptech.business.safeManage.accident.exception;

import com.aptech.framework.exception.request.BadRequestException;

public class AccidentException extends BadRequestException {

	/**
	 * * @created 2018-04-02 09:02:22
	 */
	private static final long serialVersionUID = 4086980797104872418L;

	public AccidentException(AccidentExceptionType exceptionType){
		this(exceptionType.getErrorMsg(),exceptionType.getErrorCode());
	}

	public AccidentException(String message) {
		super(message);
	}

	public AccidentException(String message, String errorCode, Throwable cause) {
		super(message, errorCode, cause);
	}

	public AccidentException(String message, String errorCode) {
		super(message, errorCode);
	}

	public AccidentException(String errorCode, Throwable cause) {
		super(errorCode, cause);
	}

}
