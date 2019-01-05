package com.aptech.business.defectManage.defect.exception;

import com.aptech.framework.exception.request.BadRequestException;

public class DefectException extends BadRequestException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4086980797104872418L;

	public DefectException(DefectExceptionType exceptionType){
		this(exceptionType.getErrorMsg(),exceptionType.getErrorCode());
	}

	public DefectException(String message) {
		super(message);
	}

	public DefectException(String message, String errorCode, Throwable cause) {
		super(message, errorCode, cause);
	}

	public DefectException(String message, String errorCode) {
		super(message, errorCode);
	}

	public DefectException(String errorCode, Throwable cause) {
		super(errorCode, cause);
	}

}
