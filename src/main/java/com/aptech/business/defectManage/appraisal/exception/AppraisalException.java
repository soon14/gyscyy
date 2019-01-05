package com.aptech.business.defectManage.appraisal.exception;

import com.aptech.framework.exception.request.BadRequestException;

public class AppraisalException extends BadRequestException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4086980797104872418L;

	public AppraisalException(AppraisalExceptionType exceptionType){
		this(exceptionType.getErrorMsg(),exceptionType.getErrorCode());
	}

	public AppraisalException(String message) {
		super(message);
	}

	public AppraisalException(String message, String errorCode, Throwable cause) {
		super(message, errorCode, cause);
	}

	public AppraisalException(String message, String errorCode) {
		super(message, errorCode);
	}

	public AppraisalException(String errorCode, Throwable cause) {
		super(errorCode, cause);
	}

}
