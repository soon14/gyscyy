package com.aptech.business.ticketManage.workElectricTwo.exception;

import com.aptech.framework.exception.request.BadRequestException;

public class WorkElectricTwoException extends BadRequestException {

	/**
	 *  张志强 20170629
	 */
	private static final long serialVersionUID = 4086980797104872418L;

	public WorkElectricTwoException(WorkElectricTwoExceptionType exceptionType){
		this(exceptionType.getErrorMsg(),exceptionType.getErrorCode());
	}

	public WorkElectricTwoException(String message) {
		super(message);
	}

	public WorkElectricTwoException(String message, String errorCode, Throwable cause) {
		super(message, errorCode, cause);
	}

	public WorkElectricTwoException(String message, String errorCode) {
		super(message, errorCode);
	}

	public WorkElectricTwoException(String errorCode, Throwable cause) {
		super(errorCode, cause);
	}

}
