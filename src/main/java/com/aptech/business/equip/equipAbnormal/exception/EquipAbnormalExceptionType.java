package com.aptech.business.equip.equipAbnormal.exception;



public class EquipAbnormalExceptionType {
	
	private String errorCode ;
	private String errorMsg ;	
	
	public static EquipAbnormalExceptionType DEFECT_CODE_NULL = new EquipAbnormalExceptionType("80031", "该记录已删除！");
	public static EquipAbnormalExceptionType DEFECT_CODE_REPEAT  = new EquipAbnormalExceptionType("80032", "状态已改变，请刷新！");
	public static EquipAbnormalExceptionType DEFECT_CODE_STATUS  = new EquipAbnormalExceptionType("80033", "该记录不是待提交或驳回状态，不能该操作！");
	public static EquipAbnormalExceptionType DEFECT_CODE_REJECT  = new EquipAbnormalExceptionType("80034", "该记录驳回状态，请在流程中提交！");
	private EquipAbnormalExceptionType(String errorCode, String errorMsg) {
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
