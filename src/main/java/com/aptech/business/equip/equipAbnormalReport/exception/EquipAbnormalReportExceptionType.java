package com.aptech.business.equip.equipAbnormalReport.exception;



public class EquipAbnormalReportExceptionType {
	
	private String errorCode ;
	private String errorMsg ;	
	
	public static EquipAbnormalReportExceptionType DEFECT_CODE_NULL = new EquipAbnormalReportExceptionType("80031", "该记录已删除！");
	public static EquipAbnormalReportExceptionType DEFECT_CODE_REPEAT  = new EquipAbnormalReportExceptionType("80032", "状态已改变，请刷新！");
	public static EquipAbnormalReportExceptionType DEFECT_CODE_STATUS  = new EquipAbnormalReportExceptionType("80033", "该记录不是待提交或驳回状态，不能该操作！");
	public static EquipAbnormalReportExceptionType DEFECT_CODE_REJECT  = new EquipAbnormalReportExceptionType("80034", "该记录驳回状态，请在流程中提交！");
	private EquipAbnormalReportExceptionType(String errorCode, String errorMsg) {
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
