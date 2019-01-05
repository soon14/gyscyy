package com.aptech.business.ticketManage.workTicketLine.exception;

import com.aptech.framework.exception.request.BadRequestException;

public class WorkTicketLineException extends BadRequestException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4086980797104872418L;

	public WorkTicketLineException(WorkTicketLineExceptionType exceptionType){
		this(exceptionType.getErrorMsg(),exceptionType.getErrorCode());
	}

	public WorkTicketLineException(String message) {
		super(message);
	}

	public WorkTicketLineException(String message, String errorCode, Throwable cause) {
		super(message, errorCode, cause);
	}

	public WorkTicketLineException(String message, String errorCode) {
		super(message, errorCode);
	}

	public WorkTicketLineException(String errorCode, Throwable cause) {
		super(errorCode, cause);
	}

}
