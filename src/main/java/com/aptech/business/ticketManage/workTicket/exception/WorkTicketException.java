package com.aptech.business.ticketManage.workTicket.exception;

import com.aptech.framework.exception.request.BadRequestException;

public class WorkTicketException extends BadRequestException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4086980797104872418L;

	public WorkTicketException(WorkTicketExceptionType exceptionType){
		this(exceptionType.getErrorMsg(),exceptionType.getErrorCode());
	}

	public WorkTicketException(String message) {
		super(message);
	}

	public WorkTicketException(String message, String errorCode, Throwable cause) {
		super(message, errorCode, cause);
	}

	public WorkTicketException(String message, String errorCode) {
		super(message, errorCode);
	}

	public WorkTicketException(String errorCode, Throwable cause) {
		super(errorCode, cause);
	}

}
