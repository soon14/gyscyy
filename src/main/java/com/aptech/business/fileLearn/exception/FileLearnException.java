package com.aptech.business.fileLearn.exception;

import com.aptech.framework.exception.request.BadRequestException;

public class FileLearnException extends BadRequestException {


	/**
	 * 
	 */
	private static final long serialVersionUID = 636781167825169245L;

	public FileLearnException(FileLearnExceptionType exceptionType){
		this(exceptionType.getErrorMsg(),exceptionType.getErrorCode());
	}

	public FileLearnException(String message) {
		super(message);
	}

	public FileLearnException(String message, String errorCode, Throwable cause) {
		super(message, errorCode, cause);
	}

	public FileLearnException(String message, String errorCode) {
		super(message, errorCode);
	}

	public FileLearnException(String errorCode, Throwable cause) {
		super(errorCode, cause);
	}

}
