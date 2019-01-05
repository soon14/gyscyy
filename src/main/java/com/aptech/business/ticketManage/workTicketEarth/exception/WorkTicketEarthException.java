package com.aptech.business.ticketManage.workTicketEarth.exception;

import com.aptech.framework.exception.request.BadRequestException;

public class WorkTicketEarthException extends BadRequestException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4086980797104872418L;

	public WorkTicketEarthException(WorkTicketEarthExceptionType exceptionType){
		this(exceptionType.getErrorMsg(),exceptionType.getErrorCode());
	}

	public WorkTicketEarthException(String message) {
		super(message);
	}

	public WorkTicketEarthException(String message, String errorCode, Throwable cause) {
		super(message, errorCode, cause);
	}

	public WorkTicketEarthException(String message, String errorCode) {
		super(message, errorCode);
	}

	public WorkTicketEarthException(String errorCode, Throwable cause) {
		super(errorCode, cause);
	}

}
