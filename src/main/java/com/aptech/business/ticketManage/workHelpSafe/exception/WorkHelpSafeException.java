package com.aptech.business.ticketManage.workHelpSafe.exception;

import com.aptech.framework.exception.request.BadRequestException;

public class WorkHelpSafeException extends BadRequestException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4086980797104872418L;

	public WorkHelpSafeException(WorkHelpSafeExceptionType exceptionType){
		this(exceptionType.getErrorMsg(),exceptionType.getErrorCode());
	}

	public WorkHelpSafeException(String message) {
		super(message);
	}

	public WorkHelpSafeException(String message, String errorCode, Throwable cause) {
		super(message, errorCode, cause);
	}

	public WorkHelpSafeException(String message, String errorCode) {
		super(message, errorCode);
	}

	public WorkHelpSafeException(String errorCode, Throwable cause) {
		super(errorCode, cause);
	}

}
