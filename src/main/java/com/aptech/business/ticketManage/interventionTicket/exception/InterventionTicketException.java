package com.aptech.business.ticketManage.interventionTicket.exception;

import com.aptech.framework.exception.request.BadRequestException;

public class InterventionTicketException extends BadRequestException {

	/** zzq
	 * * @created 2017-06-29 18:50:39
	 */
	private static final long serialVersionUID = 4086980797104872418L;

	public InterventionTicketException(InterventionTicketExceptionType exceptionType){
		this(exceptionType.getErrorMsg(),exceptionType.getErrorCode());
	}

	public InterventionTicketException(String message) {
		super(message);
	}

	public InterventionTicketException(String message, String errorCode, Throwable cause) {
		super(message, errorCode, cause);
	}

	public InterventionTicketException(String message, String errorCode) {
		super(message, errorCode);
	}

	public InterventionTicketException(String errorCode, Throwable cause) {
		super(errorCode, cause);
	}

}
