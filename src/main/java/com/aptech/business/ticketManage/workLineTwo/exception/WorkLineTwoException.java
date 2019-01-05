package com.aptech.business.ticketManage.workLineTwo.exception;

import com.aptech.framework.exception.request.BadRequestException;

public class WorkLineTwoException extends BadRequestException {

	/**
	 * 
	 * 电气工作票应用管理服务接口
	 *
	 * @author zzq
	 * @created 2017-10-18 17:12:46
	 * @lastModified 
	 * @history
	 *
	 */
	private static final long serialVersionUID = 4086980797104872418L;

	public WorkLineTwoException(WorkLineTwoExceptionType exceptionType){
		this(exceptionType.getErrorMsg(),exceptionType.getErrorCode());
	}

	public WorkLineTwoException(String message) {
		super(message);
	}

	public WorkLineTwoException(String message, String errorCode, Throwable cause) {
		super(message, errorCode, cause);
	}

	public WorkLineTwoException(String message, String errorCode) {
		super(message, errorCode);
	}

	public WorkLineTwoException(String errorCode, Throwable cause) {
		super(errorCode, cause);
	}

}
