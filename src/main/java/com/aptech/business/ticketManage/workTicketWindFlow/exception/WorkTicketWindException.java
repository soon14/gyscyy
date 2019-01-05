package com.aptech.business.ticketManage.workTicketWindFlow.exception;

import com.aptech.framework.exception.request.BadRequestException;

public class WorkTicketWindException extends BadRequestException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4086980797104872418L;

	public WorkTicketWindException(WorkTicketWindExceptionType exceptionType){
		this(exceptionType.getErrorMsg(),exceptionType.getErrorCode());
	}

	public WorkTicketWindException(String message) {
		super(message);
	}

	public WorkTicketWindException(String message, String errorCode, Throwable cause) {
		super(message, errorCode, cause);
	}

	public WorkTicketWindException(String message, String errorCode) {
		super(message, errorCode);
	}

	public WorkTicketWindException(String errorCode, Throwable cause) {
		super(errorCode, cause);
	}

}
