package com.aptech.business.run.protect.domain;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.ibatis.type.Alias;

import com.aptech.business.component.dictionary.ApplyTypeEnum;
import com.aptech.business.component.dictionary.ProtectStatusEnum;
import com.aptech.framework.json.JsonDateTime1Deserializer;
import com.aptech.framework.json.JsonDateTime1Serializer;
import com.aptech.framework.orm.BaseEntity;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * 
 * 保护投退实体类
 *
 * @author 
 * @created 2017-06-02 14:38:25
 * @lastModified 
 * @history
 *
 */
@Alias("protectEntity")
public class ProtectEntity extends BaseEntity{
		/**
    * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
    */
    private static final long serialVersionUID = -397008437263917942L;
        /**
		 * 执行结束时间
		 */
    	private Date executeEndtime;
    	
    	private String executeEndtimeString;
		/**
		 * 申请时间
		 */
    	private Date applyTime;
    	
    	private String applyTimeString;
		/**
		 * 编码
		 */
    	private String code;
		/**
		 * 保护变动方式
		 */
    	private String protectWay;
		/**
		 * 监护人ID
		 */
    	private Integer wardPersonId;
		/**
		 * 保护变动内容
		 */
    	private String protectContent;
		/**
		 * 保护变动原因
		 */
    	private String protectReason;
		/**
		 * 申请人ID
		 */
    	private Integer applyPersonId;
		/**
		 * 完成情况
		 */
    	private String finishSituation;
		/**
		 * 设备ID
		 */
    	private String deviceId;
		/**
		 * 单位ID
		 */
    	private Integer unitId;
		/**
		 * 执行人ID
		 */
    	private Integer executePersonId;
    	/**
         * 完成情况
         */
        private String unitName;
        /**
         * 完成情况
         */
        private String applyPersonName;
        /**
         * 审批状态
         */
        private String checkState;
        /**
         * 保护变动方式
         */
        private String protectWayName;
        /**
         * 执行人名称
         */
        private String executePersonName;
        /**
         * 申请类别
         */
        private Integer applyType;
        
		@SuppressWarnings("unused")
		private String applyTypeName;
        /**
         * 调度命令
         */
        private Integer dispatchCommandId;
        /**
         * 设备电压
         */
        private Integer equipVoltage;
        /**
         * 备注
         */
        private String remarks;
        /**
         * 附件
         */
        private String fileId;
        /**
         * 备注
         */
        private String executeRemarks;
        /**
         * 附件
         */
        private String executeFileId;
        
        private String flag;
      //（审批意见）
        private String approveIdea;
        private String equipName;
        private String userList;
        private String taskId;
        private String equipNumber;
        private String procInstId;
        private String equipCode;
        /**
    	 * 发文标题
    	 */
    	private String dispatchTitle;
    	
    	/**
    	 * 发文字号
    	 */
    	private String dispatchNumber;
    	
    	private Long dispatchId;
        private String copyUserIds;
        
        
        
        
        
        public String getCopyUserIds() {
			return copyUserIds;
		}

		public void setCopyUserIds(String copyUserIds) {
			this.copyUserIds = copyUserIds;
		}

		public String getDispatchTitle() {
			return dispatchTitle;
		}

		public void setDispatchTitle(String dispatchTitle) {
			this.dispatchTitle = dispatchTitle;
		}

		public String getDispatchNumber() {
			return dispatchNumber;
		}

		public void setDispatchNumber(String dispatchNumber) {
			this.dispatchNumber = dispatchNumber;
		}

		public Long getDispatchId() {
			return dispatchId;
		}

		public void setDispatchId(Long dispatchId) {
			this.dispatchId = dispatchId;
		}

		public String getEquipCode() {
			return equipCode;
		}

		public void setEquipCode(String equipCode) {
			this.equipCode = equipCode;
		}

		public String getApplyTimeString() {
			return applyTimeString;
		}

		public void setApplyTimeString(String applyTimeString) {
			this.applyTimeString = applyTimeString;
		}

		public String getExecuteEndtimeString() {
			return executeEndtimeString;
		}

		public void setExecuteEndtimeString(String executeEndtimeString) {
			this.executeEndtimeString = executeEndtimeString;
		}

		public void setApplyTypeName(String applyTypeName) {
			this.applyTypeName = applyTypeName;
		}

		public String getFlag() {
			return flag;
		}

		public void setFlag(String flag) {
			this.flag = flag;
		}

		public String getExecuteRemarks() {
			return executeRemarks;
		}

		public void setExecuteRemarks(String executeRemarks) {
			this.executeRemarks = executeRemarks;
		}

		public String getExecuteFileId() {
			return executeFileId;
		}

		public void setExecuteFileId(String executeFileId) {
			this.executeFileId = executeFileId;
		}

		public String getApplyTypeName() {
        	for (ApplyTypeEnum applyTypeEnum : ApplyTypeEnum.values()) {
                if (applyTypeEnum.getCode().equals(this.applyType.toString())) {
                    return applyTypeEnum.getName();
                }
            }
            return "";
		}
        
        public Integer getApplyType() {
			return applyType;
		}
		public void setApplyType(Integer applyType) {
			this.applyType = applyType;
		}
		public Integer getDispatchCommandId() {
			return dispatchCommandId;
		}
		public void setDispatchCommandId(Integer dispatchCommandId) {
			this.dispatchCommandId = dispatchCommandId;
		}
		public Integer getEquipVoltage() {
			return equipVoltage;
		}
		public void setEquipVoltage(Integer equipVoltage) {
			this.equipVoltage = equipVoltage;
		}
		public String getRemarks() {
			return remarks;
		}
		public void setRemarks(String remarks) {
			this.remarks = remarks;
		}
		public String getFileId() {
			return fileId;
		}
		public void setFileId(String fileId) {
			this.fileId = fileId;
		}

		@SuppressWarnings("unused")
        private String processStatusName;
        public String getProcessStatusName() {
            for (ProtectStatusEnum protectStatusEnum : ProtectStatusEnum.values()) {
                if (protectStatusEnum.getCode().equals(this.checkState.toString())) {
                    return protectStatusEnum.getName();
                }
            }
            return "";
        }
        public void setProcessStatusName(String processStatusName) {
            this.processStatusName = processStatusName;
        }
            
        @JsonSerialize(using = JsonDateTime1Serializer.class)   

        public Date getExecuteEndtime()
        {
            return executeEndtime;
        }
        @JsonDeserialize(using = JsonDateTime1Deserializer.class)
        public void setExecuteEndtime(Date executeEndtime)
        {
            this.executeEndtime = executeEndtime;
        }
    	public String getExecuteEndtimeDay() {
    		if(executeEndtime!=null){
    			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    			return  sdf.format(executeEndtime);
    		}
    		return "";
    	}
        @JsonSerialize(using = JsonDateTime1Serializer.class)   

        public Date getApplyTime()
        {
            return applyTime;
        }
        @JsonDeserialize(using = JsonDateTime1Deserializer.class)

        public void setApplyTime(Date applyTime)
        {
            this.applyTime = applyTime;
        }
        public String getApplyTimeDay() {
    		if(applyTime!=null){
    			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    			return  sdf.format(applyTime);
    		}
    		return "";
    	}
        public String getExecutePersonName()
        {
            return executePersonName;
        }

        public void setExecutePersonName(String executePersonName)
        {
            this.executePersonName = executePersonName;
        }

        public static long getSerialversionuid()
        {
            return serialVersionUID;
        }

        public String getTaskId()
        {
            return taskId;
        }
        public void setTaskId(String taskId)
        {
            this.taskId = taskId;
        }
        public String getProcInstId()
        {
            return procInstId;
        }
        public void setProcInstId(String procInstId)
        {
            this.procInstId = procInstId;
        }
        public String getUserList()
        {
            return userList;
        }
        public void setUserList(String userList)
        {
            this.userList = userList;
        }
        public String getProtectWayName()
        {
            return protectWayName;
        }
        public void setProtectWayName(String protectWayName)
        {
            this.protectWayName = protectWayName;
        }
        
        public String getCheckState()
        {
            return checkState;
        }
        public void setCheckState(String checkState)
        {
            this.checkState = checkState;
        }
        public String getUnitName()
        {
            return unitName;
        }
        public void setUnitName(String unitName)
        {
            this.unitName = unitName;
        }
        public String getApplyPersonName()
        {
            return applyPersonName;
        }
        public void setApplyPersonName(String applyPersonName)
        {
            this.applyPersonName = applyPersonName;
        }

        public String getCode(){
			return code;
		}
		public void setCode(String code){
			this.code = code;
		}
		public String getProtectWay(){
			return protectWay;
		}
		public void setProtectWay(String protectWay){
			this.protectWay = protectWay;
		}
		public Integer getWardPersonId(){
			return wardPersonId;
		}
		public void setWardPersonId(Integer wardPersonId){
			this.wardPersonId = wardPersonId;
		}
		public String getProtectContent(){
			return protectContent;
		}
		public void setProtectContent(String protectContent){
			this.protectContent = protectContent;
		}

		public String getProtectReason(){
			return protectReason;
		}
		public void setProtectReason(String protectReason){
			this.protectReason = protectReason;
		}
		public Integer getApplyPersonId(){
			return applyPersonId;
		}
		public void setApplyPersonId(Integer applyPersonId){
			this.applyPersonId = applyPersonId;
		}
		public String getFinishSituation(){
			return finishSituation;
		}
		public void setFinishSituation(String finishSituation){
			this.finishSituation = finishSituation;
		}
		public String getDeviceId(){
			return deviceId;
		}
		public void setDeviceId(String deviceId){
			this.deviceId = deviceId;
		}
		public Integer getUnitId(){
			return unitId;
		}
		public void setUnitId(Integer unitId){
			this.unitId = unitId;
		}
		public Integer getExecutePersonId(){
			return executePersonId;
		}
		public void setExecutePersonId(Integer executePersonId){
			this.executePersonId = executePersonId;
		}

        public String getEquipName()
        {
            return equipName;
        }

        public void setEquipName(String equipName)
        {
            this.equipName = equipName;
        }

        public String getEquipNumber()
        {
            return equipNumber;
        }

        public void setEquipNumber(String equipNumber)
        {
            this.equipNumber = equipNumber;
        }

        public String getApproveIdea()
        {
            return approveIdea;
        }

        public void setApproveIdea(String approveIdea)
        {
            this.approveIdea = approveIdea;
        }
		
}