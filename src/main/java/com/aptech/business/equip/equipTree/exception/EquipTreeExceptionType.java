package com.aptech.business.equip.equipTree.exception;
/**
 * @Description:   
 * @author         wangcc
 * @version        V1.0  
 * @Date           2017年6月29日 上午10:50:21 
 */
public class EquipTreeExceptionType {
	
	private String errorCode ;
	private String errorMsg ;	
	
	public static EquipTreeExceptionType FourCode_CODE_NULL = new EquipTreeExceptionType("80011", "编码为空，保存失败...");
	public static EquipTreeExceptionType FourCode_CODE_REPEAT  = new EquipTreeExceptionType("80012", "编码重复，保存失败...");
	public static EquipTreeExceptionType FourCode_NAME_NULL = new EquipTreeExceptionType("80013", "名称为空，保存失败...");
	
	private EquipTreeExceptionType(String errorCode, String errorMsg) {
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
