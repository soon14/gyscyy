package com.aptech.business.equip.equipModel.exception;
/**
 * @Description:   
 * @author         wangcc
 * @version        V1.0  
 * @Date           2017年6月29日 上午10:50:21 
 */
public class EquipModelExceptionType {
	
	private String errorCode ;
	private String errorMsg ;	
	
	public static EquipModelExceptionType BASE_PARAMETER_REPEAT = new EquipModelExceptionType("80021", "基础参数重复，保存失败");
	public static EquipModelExceptionType THCHNOLOGY_PARAMETER_REPEAT  = new EquipModelExceptionType("80022", "技术参数重复，保存失败");
	public static EquipModelExceptionType BASE_THCHNOLOGY_PARAMETER_REPEAT = new EquipModelExceptionType("80023", "基础、技术参数重复，保存失败");
	public static EquipModelExceptionType MODEL_ALREADY = new EquipModelExceptionType("80024", "模版已存在，保存失败");
	
	private EquipModelExceptionType(String errorCode, String errorMsg) {
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
