package com.aptech.business.overhaul.overhaulArrange.exception;

import com.aptech.framework.exception.request.BadRequestException;

public class OverhaulArrangeException extends BadRequestException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4086980797104872418L;

	public OverhaulArrangeException(OverhaulArrangeExceptionType exceptionType){
		this(exceptionType.getErrorMsg(),exceptionType.getErrorCode());
	}

	public OverhaulArrangeException(String message) {
		super(message);
	}

	public OverhaulArrangeException(String message, String errorCode, Throwable cause) {
		super(message, errorCode, cause);
	}

	public OverhaulArrangeException(String message, String errorCode) {
		super(message, errorCode);
	}

	public OverhaulArrangeException(String errorCode, Throwable cause) {
		super(errorCode, cause);
	}

}
