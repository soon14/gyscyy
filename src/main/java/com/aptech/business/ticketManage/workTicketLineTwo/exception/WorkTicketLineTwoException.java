package com.aptech.business.ticketManage.workTicketLineTwo.exception;

import com.aptech.framework.exception.request.BadRequestException;

public class WorkTicketLineTwoException extends BadRequestException {

	/**
	 * @author zzq
	 * @created 2017-10-18 11:50:39
	 * @lastModified 
	 * @history
	 *
	 */
	private static final long serialVersionUID = 4086980797104872418L;

	public WorkTicketLineTwoException(WorkTicketLineTwoExceptionType exceptionType){
		this(exceptionType.getErrorMsg(),exceptionType.getErrorCode());
	}

	public WorkTicketLineTwoException(String message) {
		super(message);
	}

	public WorkTicketLineTwoException(String message, String errorCode, Throwable cause) {
		super(message, errorCode, cause);
	}

	public WorkTicketLineTwoException(String message, String errorCode) {
		super(message, errorCode);
	}

	public WorkTicketLineTwoException(String errorCode, Throwable cause) {
		super(errorCode, cause);
	}

}
