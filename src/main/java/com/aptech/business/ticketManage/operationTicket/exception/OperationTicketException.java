package com.aptech.business.ticketManage.operationTicket.exception;

import com.aptech.framework.exception.request.BadRequestException;

public class OperationTicketException extends BadRequestException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4086980797104872418L;

	public OperationTicketException(OperationTicketExceptionType exceptionType){
		this(exceptionType.getErrorMsg(),exceptionType.getErrorCode());
	}

	public OperationTicketException(String message) {
		super(message);
	}

	public OperationTicketException(String message, String errorCode, Throwable cause) {
		super(message, errorCode, cause);
	}

	public OperationTicketException(String message, String errorCode) {
		super(message, errorCode);
	}

	public OperationTicketException(String errorCode, Throwable cause) {
		super(errorCode, cause);
	}

}
