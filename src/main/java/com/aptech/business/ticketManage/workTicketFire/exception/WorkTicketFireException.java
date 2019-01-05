package com.aptech.business.ticketManage.workTicketFire.exception;

import com.aptech.framework.exception.request.BadRequestException;

public class WorkTicketFireException extends BadRequestException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4086980797104872418L;

	public WorkTicketFireException(WorkTicketFireExceptionType exceptionType){
		this(exceptionType.getErrorMsg(),exceptionType.getErrorCode());
	}

	public WorkTicketFireException(String message) {
		super(message);
	}

	public WorkTicketFireException(String message, String errorCode, Throwable cause) {
		super(message, errorCode, cause);
	}

	public WorkTicketFireException(String message, String errorCode) {
		super(message, errorCode);
	}

	public WorkTicketFireException(String errorCode, Throwable cause) {
		super(errorCode, cause);
	}

}
