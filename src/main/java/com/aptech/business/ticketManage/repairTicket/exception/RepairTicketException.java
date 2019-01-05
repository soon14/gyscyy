package com.aptech.business.ticketManage.repairTicket.exception;

import com.aptech.framework.exception.request.BadRequestException;

public class RepairTicketException extends BadRequestException {

	/** zzq
	 * * @created 2017-06-29 18:50:39
	 */
	private static final long serialVersionUID = 4086980797104872418L;

	public RepairTicketException(RepairTicketExceptionType exceptionType){
		this(exceptionType.getErrorMsg(),exceptionType.getErrorCode());
	}

	public RepairTicketException(String message) {
		super(message);
	}

	public RepairTicketException(String message, String errorCode, Throwable cause) {
		super(message, errorCode, cause);
	}

	public RepairTicketException(String message, String errorCode) {
		super(message, errorCode);
	}

	public RepairTicketException(String errorCode, Throwable cause) {
		super(errorCode, cause);
	}

}
