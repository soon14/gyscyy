package com.aptech.business.overhaul.overhaulFile.exception;

import com.aptech.framework.exception.request.BadRequestException;

public class OverhaulFileException extends BadRequestException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public OverhaulFileException(OverhaulFileExceptionType exceptionType){
		this(exceptionType.getErrorMsg(),exceptionType.getErrorCode());
	}

	public OverhaulFileException(String message) {
		super(message);
	}

	public OverhaulFileException(String message, String errorCode, Throwable cause) {
		super(message, errorCode, cause);
	}

	public OverhaulFileException(String message, String errorCode) {
		super(message, errorCode);
	}

	public OverhaulFileException(String errorCode, Throwable cause) {
		super(errorCode, cause);
	}
}
