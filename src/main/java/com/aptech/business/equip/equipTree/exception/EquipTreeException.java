package com.aptech.business.equip.equipTree.exception;

import com.aptech.framework.exception.request.BadRequestException;

/**
 * @Description:   
 * @author         wangcc
 * @version        V1.0  
 * @Date           2017年6月29日 上午10:49:51 
 */
public class EquipTreeException extends BadRequestException {

	private static final long serialVersionUID = -2135238922330003303L;

	
	public EquipTreeException(EquipTreeExceptionType exceptionType){
		this(exceptionType.getErrorMsg(),exceptionType.getErrorCode());
	}

	public EquipTreeException(String message) {
		super(message);
	}

	public EquipTreeException(String message, String errorCode, Throwable cause) {
		super(message, errorCode, cause);
	}

	public EquipTreeException(String message, String errorCode) {
		super(message, errorCode);
	}

	public EquipTreeException(String errorCode, Throwable cause) {
		super(errorCode, cause);
	}

}
