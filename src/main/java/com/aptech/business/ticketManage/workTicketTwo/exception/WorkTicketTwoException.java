package com.aptech.business.ticketManage.workTicketTwo.exception;

import com.aptech.framework.exception.request.BadRequestException;

public class WorkTicketTwoException extends BadRequestException {

	/** zzq
	 * * @created 2017-06-29 18:50:39
	 */
	private static final long serialVersionUID = 4086980797104872418L;

	public WorkTicketTwoException(WorkTicketTwoExceptionType exceptionType){
		this(exceptionType.getErrorMsg(),exceptionType.getErrorCode());
	}

	public WorkTicketTwoException(String message) {
		super(message);
	}

	public WorkTicketTwoException(String message, String errorCode, Throwable cause) {
		super(message, errorCode, cause);
	}

	public WorkTicketTwoException(String message, String errorCode) {
		super(message, errorCode);
	}

	public WorkTicketTwoException(String errorCode, Throwable cause) {
		super(errorCode, cause);
	}

}
