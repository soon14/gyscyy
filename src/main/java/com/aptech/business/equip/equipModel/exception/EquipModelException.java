package com.aptech.business.equip.equipModel.exception;

import com.aptech.framework.exception.request.BadRequestException;

/**
 * @Description:   
 * @author         wangcc
 * @version        V1.0  
 * @Date           2017年7月5日 上午9:17:04 
 */
public class EquipModelException extends BadRequestException {


	private static final long serialVersionUID = -264899510956577349L;

	
	public EquipModelException(EquipModelExceptionType exceptionType){
		this(exceptionType.getErrorMsg(),exceptionType.getErrorCode());
	}

	public EquipModelException(String message) {
		super(message);
	}

	public EquipModelException(String message, String errorCode, Throwable cause) {
		super(message, errorCode, cause);
	}

	public EquipModelException(String message, String errorCode) {
		super(message, errorCode);
	}

	public EquipModelException(String errorCode, Throwable cause) {
		super(errorCode, cause);
	}
}
