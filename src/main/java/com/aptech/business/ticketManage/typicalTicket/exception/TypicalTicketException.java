package com.aptech.business.ticketManage.typicalTicket.exception;

import com.aptech.framework.exception.request.BadRequestException;

public class TypicalTicketException extends BadRequestException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4086980797104872418L;

	public TypicalTicketException(TypicalTicketExceptionType exceptionType){
		this(exceptionType.getErrorMsg(),exceptionType.getErrorCode());
	}

	public TypicalTicketException(String message) {
		super(message);
	}

	public TypicalTicketException(String message, String errorCode, Throwable cause) {
		super(message, errorCode, cause);
	}

	public TypicalTicketException(String message, String errorCode) {
		super(message, errorCode);
	}

	public TypicalTicketException(String errorCode, Throwable cause) {
		super(errorCode, cause);
	}

}
