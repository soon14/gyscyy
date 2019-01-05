package com.aptech.business.ticketManage.workLine.exception;

import com.aptech.framework.exception.request.BadRequestException;

public class WorkLineException extends BadRequestException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4086980797104872418L;

	public WorkLineException(WorkLineExceptionType exceptionType){
		this(exceptionType.getErrorMsg(),exceptionType.getErrorCode());
	}

	public WorkLineException(String message) {
		super(message);
	}

	public WorkLineException(String message, String errorCode, Throwable cause) {
		super(message, errorCode, cause);
	}

	public WorkLineException(String message, String errorCode) {
		super(message, errorCode);
	}

	public WorkLineException(String errorCode, Throwable cause) {
		super(errorCode, cause);
	}

}
