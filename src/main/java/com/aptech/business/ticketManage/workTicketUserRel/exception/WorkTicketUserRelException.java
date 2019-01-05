package com.aptech.business.ticketManage.workTicketUserRel.exception;

import com.aptech.framework.exception.request.BadRequestException;

public class WorkTicketUserRelException extends BadRequestException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4086980797104872418L;


	public WorkTicketUserRelException(String message) {
		super(message);
	}

	public WorkTicketUserRelException(String message, String errorCode, Throwable cause) {
		super(message, errorCode, cause);
	}

	public WorkTicketUserRelException(String message, String errorCode) {
		super(message, errorCode);
	}

	public WorkTicketUserRelException(String errorCode, Throwable cause) {
		super(errorCode, cause);
	}

}
