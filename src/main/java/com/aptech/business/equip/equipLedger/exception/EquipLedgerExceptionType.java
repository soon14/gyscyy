package com.aptech.business.equip.equipLedger.exception;
/**
 * @Description:   
 * @author         wangcc
 * @version        V1.0  
 * @Date           2017年6月29日 上午10:50:21 
 */
public class EquipLedgerExceptionType {
	
	private String errorCode ;
	private String errorMsg ;	
	
	public static EquipLedgerExceptionType FourCode_CODE_NULL = new EquipLedgerExceptionType("80031", "编码为空，保存失败...");
	public static EquipLedgerExceptionType FourCode_CODE_REPEAT  = new EquipLedgerExceptionType("80032", "编码重复，保存失败...");
	public static EquipLedgerExceptionType FourCode_NAME_NULL = new EquipLedgerExceptionType("80033", "名称为空，保存失败...");
	
	private EquipLedgerExceptionType(String errorCode, String errorMsg) {
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
	}
	
	public String getErrorCode() {
		return errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

}
