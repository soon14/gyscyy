package com.aptech.business.equip.equipAbnormal.exception;

import com.aptech.framework.exception.request.BadRequestException;

public class EquipAbnormalException extends BadRequestException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4086980797104872418L;

	public EquipAbnormalException(EquipAbnormalExceptionType exceptionType){
		this(exceptionType.getErrorMsg(),exceptionType.getErrorCode());
	}

	public EquipAbnormalException(String message) {
		super(message);
	}

	public EquipAbnormalException(String message, String errorCode, Throwable cause) {
		super(message, errorCode, cause);
	}

	public EquipAbnormalException(String message, String errorCode) {
		super(message, errorCode);
	}

	public EquipAbnormalException(String errorCode, Throwable cause) {
		super(errorCode, cause);
	}

}
