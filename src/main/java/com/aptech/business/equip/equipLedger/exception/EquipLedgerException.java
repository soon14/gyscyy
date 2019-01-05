package com.aptech.business.equip.equipLedger.exception;

import com.aptech.business.equip.equipTree.exception.EquipTreeExceptionType;
import com.aptech.framework.exception.request.BadRequestException;

/**
 * @Description:   
 * @author         wangcc
 * @version        V1.0  
 * @Date           2017年7月5日 上午9:17:04 
 */
public class EquipLedgerException extends BadRequestException {


	private static final long serialVersionUID = -264899510956577349L;

	
	public EquipLedgerException(EquipLedgerExceptionType exceptionType){
		this(exceptionType.getErrorMsg(),exceptionType.getErrorCode());
	}

	public EquipLedgerException(String message) {
		super(message);
	}

	public EquipLedgerException(String message, String errorCode, Throwable cause) {
		super(message, errorCode, cause);
	}

	public EquipLedgerException(String message, String errorCode) {
		super(message, errorCode);
	}

	public EquipLedgerException(String errorCode, Throwable cause) {
		super(errorCode, cause);
	}
}
