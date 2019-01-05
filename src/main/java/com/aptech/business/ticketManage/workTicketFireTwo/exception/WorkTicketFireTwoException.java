package com.aptech.business.ticketManage.workTicketFireTwo.exception;

import com.aptech.framework.exception.request.BadRequestException;

public class WorkTicketFireTwoException extends BadRequestException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4086980797104872418L;

	public WorkTicketFireTwoException(WorkTicketFireTwoExceptionType exceptionType){
		this(exceptionType.getErrorMsg(),exceptionType.getErrorCode());
	}

	public WorkTicketFireTwoException(String message) {
		super(message);
	}

	public WorkTicketFireTwoException(String message, String errorCode, Throwable cause) {
		super(message, errorCode, cause);
	}

	public WorkTicketFireTwoException(String message, String errorCode) {
		super(message, errorCode);
	}

	public WorkTicketFireTwoException(String errorCode, Throwable cause) {
		super(errorCode, cause);
	}

}
