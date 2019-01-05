package com.aptech.business.overhaul.overhaulLog.exception;

import com.aptech.framework.exception.request.BadRequestException;

public class OverhaulLogException extends BadRequestException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4086980797104872418L;

	public OverhaulLogException(OverhaulLogExceptionType exceptionType){
		this(exceptionType.getErrorMsg(),exceptionType.getErrorCode());
	}

	public OverhaulLogException(String message) {
		super(message);
	}

	public OverhaulLogException(String message, String errorCode, Throwable cause) {
		super(message, errorCode, cause);
	}

	public OverhaulLogException(String message, String errorCode) {
		super(message, errorCode);
	}

	public OverhaulLogException(String errorCode, Throwable cause) {
		super(errorCode, cause);
	}

}
