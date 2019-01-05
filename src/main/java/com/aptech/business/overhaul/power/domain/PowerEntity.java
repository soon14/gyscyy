package com.aptech.business.overhaul.power.domain;

import java.util.Date;

import org.apache.ibatis.type.Alias;

import com.aptech.business.component.dictionary.IsPowerEnum;
import com.aptech.business.component.dictionary.PowerStatusEnum;
import com.aptech.business.component.dictionary.PowerTypeEnum;
import com.aptech.framework.json.JsonDateTimeDeserializer;
import com.aptech.framework.json.JsonDateTimeSerializer;
import com.aptech.framework.orm.BaseEntity;
import com.aptech.framework.util.DateFormatUtil;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * 
 * 停送电管理实体类
 *
 * @author 
 * @created 2017-07-31 14:17:20
 * @lastModified 
 * @history
 *
 */
@Alias("PowerEntity")
public class PowerEntity extends BaseEntity{
		/**
		 * 主键
		 */
    	private Long id;
		/**
		 * 申请编号
		 */
    	private String requestNumber;
		/**
		 * 单位id
		 */
    	private Integer unitId;
		/**
		 * 设备编号
		 */
    	private String equipNumber;
		/**
		 * 设备名称
		 */
    	private String equipName;
		/**
		 * 单位名称
		 */
    	private String unitName;
		/**
		 * 申请时间
		 */
    	private Date requestDate;
		/**
		 * 申请人ID
		 */
    	private Integer requestUserId;
		/**
		 * 申请人名
		 */
    	private String requestUserName;
		/**
		 * 停送电方式
		 */
    	private String powerStatus;
    	
		/**
		 * 停送电内容
		 */
    	private String powerDec;
		/**
		 * 备注
		 */
    	private String remark;
		/**
		 * 附件
		 */
    	private String attchmentIds;
		/**
		 * 状态
		 */
    	private String status;
		/**
		 * 结束时间
		 */
    	private Date endDate;
		/**
		 * 执行人id
		 */
    	private Integer excuteUserId;
		/**
		 * 执行人名
		 */
    	private String excuteUserName;
		/**
		 * 监护人id
		 */
    	private Integer costodyUserId;
		/**
		 * 监护人名
		 */
    	private String costodyUserName;
		/**
		 * 完成情况
		 */
    	private String measur;
		/**
		 * 结果附件
		 */
    	private String attchmentResultIds;
    	private String powerStatusText;
    	private String statusText;
    	private String endDateString;
    	private String requestDateString;
    	private String userList;
    	private String taskId;
    	private String auditMsg;
    	
    	private Long overhaulPlanId;//检修计划ID
    	
    	
    	private String isPower;//是否停送电
    	
    	private String equipLocalName;//设备所在地及名称
    	
    	private String powerRange;//停送电范围
    	
    	private String powerLevel;//电压等级
    	
    	private String planName;//检修计划名称
    	
    	private String endEquipLocalName;//最终设备所在地及名称
    	
    	private String  endPowerRange;// 最终停电范围
    	
    	
    	public String getEndEquipLocalName() {
			return endEquipLocalName;
		}
		public void setEndEquipLocalName(String endEquipLocalName) {
			this.endEquipLocalName = endEquipLocalName;
		}
		public String getEndPowerRange() {
			return endPowerRange;
		}
		public void setEndPowerRange(String endPowerRange) {
			this.endPowerRange = endPowerRange;
		}
		public String getPlanName() {
			return planName;
		}
		public void setPlanName(String planName) {
			this.planName = planName;
		}
		public String getPowerLevel() {
			return powerLevel;
		}
		public void setPowerLevel(String powerLevel) {
			this.powerLevel = powerLevel;
		}
		public String getIsPower() {
			return isPower;
		}
		public void setIsPower(String isPower) {
			this.isPower = isPower;
		}
		public String getEquipLocalName() {
			return equipLocalName;
		}
		public void setEquipLocalName(String equipLocalName) {
			this.equipLocalName = equipLocalName;
		}
		public String getPowerRange() {
			return powerRange;
		}
		public void setPowerRange(String powerRange) {
			this.powerRange = powerRange;
		}
		
		
		public Long getOverhaulPlanId() {
			return overhaulPlanId;
		}
		public void setOverhaulPlanId(Long overhaulPlanId) {
			this.overhaulPlanId = overhaulPlanId;
		}


		private String procInstId;
		public Long getId(){
			return id;
		}
		public void setId(Long id){
			this.id = id;
		}
		public String getRequestNumber(){
			return requestNumber;
		}
		public void setRequestNumber(String requestNumber){
			this.requestNumber = requestNumber;
		}
		public Integer getUnitId(){
			return unitId;
		}
		public void setUnitId(Integer unitId){
			this.unitId = unitId;
		}
		public String getEquipNumber(){
			return equipNumber;
		}
		public void setEquipNumber(String equipNumber){
			this.equipNumber = equipNumber;
		}
		public String getEquipName(){
			return equipName;
		}
		public void setEquipName(String equipName){
			this.equipName = equipName;
		}
		public String getUnitName(){
			return unitName;
		}
		public void setUnitName(String unitName){
			this.unitName = unitName;
		}
	    @JsonSerialize(using = JsonDateTimeSerializer.class)
		public Date getRequestDate(){
			return requestDate;
		}
	    @JsonDeserialize(using = JsonDateTimeDeserializer.class)
		public void setRequestDate(Date requestDate){
			this.requestDate = requestDate;
		}
		public Integer getRequestUserId(){
			return requestUserId;
		}
		public void setRequestUserId(Integer requestUserId){
			this.requestUserId = requestUserId;
		}
		public String getRequestUserName(){
			return requestUserName;
		}
		public void setRequestUserName(String requestUserName){
			this.requestUserName = requestUserName;
		}
		public String getPowerStatus(){
			return powerStatus;
		}
		public void setPowerStatus(String powerStatus){
			this.powerStatus = powerStatus;
		}
		public String getPowerDec(){
			return powerDec;
		}
		public void setPowerDec(String powerDec){
			this.powerDec = powerDec;
		}
		public String getRemark(){
			return remark;
		}
		public void setRemark(String remark){
			this.remark = remark;
		}
		public String getAttchmentIds(){
			return attchmentIds;
		}
		public void setAttchmentIds(String attchmentIds){
			this.attchmentIds = attchmentIds;
		}
		public String getStatus(){
			return status;
		}
		public void setStatus(String status){
			this.status = status;
		}
	    @JsonSerialize(using = JsonDateTimeSerializer.class)
		public Date getEndDate(){
			return endDate;
		}
	    @JsonDeserialize(using = JsonDateTimeDeserializer.class)
		public void setEndDate(Date endDate){
			this.endDate = endDate;
		}
		public Integer getExcuteUserId(){
			return excuteUserId;
		}
		public void setExcuteUserId(Integer excuteUserId){
			this.excuteUserId = excuteUserId;
		}
		public String getExcuteUserName(){
			return excuteUserName;
		}
		public void setExcuteUserName(String excuteUserName){
			this.excuteUserName = excuteUserName;
		}
		public Integer getCostodyUserId(){
			return costodyUserId;
		}
		public void setCostodyUserId(Integer costodyUserId){
			this.costodyUserId = costodyUserId;
		}
		public String getCostodyUserName(){
			return costodyUserName;
		}
		public void setCostodyUserName(String costodyUserName){
			this.costodyUserName = costodyUserName;
		}
		public String getMeasur(){
			return measur;
		}
		public void setMeasur(String measur){
			this.measur = measur;
		}
		public String getAttchmentResultIds(){
			return attchmentResultIds;
		}
		public void setAttchmentResultIds(String attchmentResultIds){
			this.attchmentResultIds = attchmentResultIds;
		}
		public String getPowerStatusText() {
			for (PowerTypeEnum powerTypeEnum : PowerTypeEnum.values()) {
				if (powerTypeEnum.getCode().equals(this.powerStatus)) {
					return powerTypeEnum.getName();
				}
			}
			return null;
		}
		public String getIsPowerText() {
			for (IsPowerEnum isPowerEnum : IsPowerEnum.values()) {
				if (isPowerEnum.getCode().equals(this.isPower)) {
					return isPowerEnum.getName();
				}
			}
			return null;
		}
		
		public String getStatusText() {
			for (PowerStatusEnum powerStatusEnum : PowerStatusEnum.values()) {
				if (powerStatusEnum.getCode().equals(this.status)) {
					return powerStatusEnum.getName();
				}
			}
			return null;
		}
		public void setStatusText(String statusText) {
			this.statusText = statusText;
		}
		public void setPowerStatusText(String powerStatusText) {
			this.powerStatusText = powerStatusText;
		}
		public String getEndDateString() {
			DateFormatUtil df= DateFormatUtil.getInstance("yyyy-MM-dd HH:mm");
			if(this.endDate ==null){
				return "";
			}
			return df.format(this.endDate);
		}
		public void setEndDateString(String endDateString) {
			this.endDateString = endDateString;
		}
		public String getRequestDateString() {
			DateFormatUtil df= DateFormatUtil.getInstance("yyyy-MM-dd HH:mm");
			if(this.requestDate ==null){
				return "";
			}
			return df.format(this.requestDate);
		}
		public void setRequestDateString(String requestDateString) {
			this.requestDateString = requestDateString;
		}
		public String getUserList() {
			return userList;
		}
		public void setUserList(String userList) {
			this.userList = userList;
		}
		public String getTaskId() {
			return taskId;
		}
		public void setTaskId(String taskId) {
			this.taskId = taskId;
		}
		public String getProcInstId() {
			return procInstId;
		}
		public void setProcInstId(String procInstId) {
			this.procInstId = procInstId;
		}
		public String getAuditMsg() {
			return auditMsg;
		}
		public void setAuditMsg(String auditMsg) {
			this.auditMsg = auditMsg;
		}
}