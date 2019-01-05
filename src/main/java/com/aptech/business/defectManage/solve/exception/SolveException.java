package com.aptech.business.defectManage.solve.exception;

import com.aptech.framework.exception.request.BadRequestException;

public class SolveException extends BadRequestException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4086980797104872418L;

	public SolveException(SolveExceptionType exceptionType){
		this(exceptionType.getErrorMsg(),exceptionType.getErrorCode());
	}

	public SolveException(String message) {
		super(message);
	}

	public SolveException(String message, String errorCode, Throwable cause) {
		super(message, errorCode, cause);
	}

	public SolveException(String message, String errorCode) {
		super(message, errorCode);
	}

	public SolveException(String errorCode, Throwable cause) {
		super(errorCode, cause);
	}

}
