package com.aptech.business.ticketManage.operationItem.exception;

import com.aptech.framework.exception.request.BadRequestException;

public class OperationItemException extends BadRequestException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4086980797104872418L;

	public OperationItemException(OperationItemExceptionType exceptionType){
		this(exceptionType.getErrorMsg(),exceptionType.getErrorCode());
	}

	public OperationItemException(String message) {
		super(message);
	}

	public OperationItemException(String message, String errorCode, Throwable cause) {
		super(message, errorCode, cause);
	}

	public OperationItemException(String message, String errorCode) {
		super(message, errorCode);
	}

	public OperationItemException(String errorCode, Throwable cause) {
		super(errorCode, cause);
	}

}
