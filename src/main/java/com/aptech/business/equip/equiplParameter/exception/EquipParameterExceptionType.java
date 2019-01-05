package com.aptech.business.equip.equiplParameter.exception;
/**
 * @Description:   
 * @author         wangcc
 * @version        V1.0  
 * @Date           2017年6月29日 上午10:50:21 
 */
public class EquipParameterExceptionType {
	
	private String errorCode ;
	private String errorMsg ;	
	
	public static EquipParameterExceptionType FourCode_CODE_NULL = new EquipParameterExceptionType("80000", "编码为空，保存失败...");
	public static EquipParameterExceptionType FourCode_CODE_REPEAT  = new EquipParameterExceptionType("80001", "编码重复，保存失败...");
	public static EquipParameterExceptionType FourCode_NAME_NULL = new EquipParameterExceptionType("80002", "名称为空，保存失败...");
	
	private EquipParameterExceptionType(String errorCode, String errorMsg) {
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
