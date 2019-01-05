package com.aptech.business.equip.equiplParameter.exception;

import com.aptech.framework.exception.request.BadRequestException;

/**
 * @Description:   
 * @author         wangcc
 * @version        V1.0  
 * @Date           2017年7月5日 上午9:17:04 
 */
public class EquipParameterException extends BadRequestException {


	private static final long serialVersionUID = -264899510956577349L;

	
	public EquipParameterException(EquipParameterExceptionType exceptionType){
		this(exceptionType.getErrorMsg(),exceptionType.getErrorCode());
	}

	public EquipParameterException(String message) {
		super(message);
	}

	public EquipParameterException(String message, String errorCode, Throwable cause) {
		super(message, errorCode, cause);
	}

	public EquipParameterException(String message, String errorCode) {
		super(message, errorCode);
	}

	public EquipParameterException(String errorCode, Throwable cause) {
		super(errorCode, cause);
	}
}
