package com.aptech.business.ticketManage.operationDanger.exception;

import com.aptech.framework.exception.request.BadRequestException;

public class OperationDangerException extends BadRequestException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4086980797104872418L;

	public OperationDangerException(OperationDangerExceptionType exceptionType){
		this(exceptionType.getErrorMsg(),exceptionType.getErrorCode());
	}

	public OperationDangerException(String message) {
		super(message);
	}

	public OperationDangerException(String message, String errorCode, Throwable cause) {
		super(message, errorCode, cause);
	}

	public OperationDangerException(String message, String errorCode) {
		super(message, errorCode);
	}

	public OperationDangerException(String errorCode, Throwable cause) {
		super(errorCode, cause);
	}

}
