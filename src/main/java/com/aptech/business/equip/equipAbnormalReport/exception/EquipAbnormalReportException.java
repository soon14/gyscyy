package com.aptech.business.equip.equipAbnormalReport.exception;

import com.aptech.framework.exception.request.BadRequestException;

public class EquipAbnormalReportException extends BadRequestException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4086980797104872418L;

	public EquipAbnormalReportException(EquipAbnormalReportExceptionType exceptionType){
		this(exceptionType.getErrorMsg(),exceptionType.getErrorCode());
	}

	public EquipAbnormalReportException(String message) {
		super(message);
	}

	public EquipAbnormalReportException(String message, String errorCode, Throwable cause) {
		super(message, errorCode, cause);
	}

	public EquipAbnormalReportException(String message, String errorCode) {
		super(message, errorCode);
	}

	public EquipAbnormalReportException(String errorCode, Throwable cause) {
		super(errorCode, cause);
	}

}
