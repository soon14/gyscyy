package com.aptech.business.contractManage.exception;

import com.aptech.framework.exception.request.BadRequestException;

public class ContractManageException extends BadRequestException {


	/**
	 * 
	 */
	private static final long serialVersionUID = 636781167825169245L;

	public ContractManageException(ContractManageExceptionType exceptionType){
		this(exceptionType.getErrorMsg(),exceptionType.getErrorCode());
	}

	public ContractManageException(String message) {
		super(message);
	}

	public ContractManageException(String message, String errorCode, Throwable cause) {
		super(message, errorCode, cause);
	}

	public ContractManageException(String message, String errorCode) {
		super(message, errorCode);
	}

	public ContractManageException(String errorCode, Throwable cause) {
		super(errorCode, cause);
	}

}
