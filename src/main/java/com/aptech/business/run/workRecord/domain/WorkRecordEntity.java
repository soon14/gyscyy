package com.aptech.business.run.workRecord.domain;

import java.util.Date;

import org.apache.ibatis.type.Alias;

import com.aptech.business.component.dictionary.ProtectStatusEnum;
import com.aptech.framework.json.JsonDateTime1Deserializer;
import com.aptech.framework.json.JsonDateTime1Serializer;
import com.aptech.framework.orm.BaseEntity;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * 
 * 定期工作记录实体类
 *
 * @author 
 * @created 2017-06-01 15:08:52
 * @lastModified 
 * @history
 *
 */
@Alias("workRecordEntity")
public class WorkRecordEntity extends BaseEntity{
		/**
    * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
    */
    private static final long serialVersionUID = -8934843526045942882L;
        /**
		 * 责任人ID
		 */
    	private Integer dutyPersonId;
		/**
		 * 工作时间
		 */
    	private Date workTime;
		/**
		 * 工作类型
		 */
    	private String workType;
		/**
		 * 工作结论
		 */
    	private String conclusion;
		/**
		 *  编码
		 */
    	private String code;
		/**
		 * 工作计划ID
		 */
    	private Integer planId;
		/**
		 * 班次
		 */
    	private String team;
		/**
		 * 存在问题及处理意见
		 */
    	private String question;
		/**
		 * 是否延期
		 */
    	private Integer delayStates;
		/**
		 * 设备ID
		 */
    	private Integer deviceId;
		/**
		 * 单位ID
		 */
    	private Integer unitId;
		/**
		 * 参加人员ID
		 */
    	private String joinPersonIds;
        /**
         * 参加人员ID
         */
        private String unitName;
        /**
         * 参加人员ID
         */
        private String dutyPersonName;
        /**
         * 审批状态
         */
        private String  checkState;
        /**
         * 班次名称
         */
        private String teamName;
        /**
         * 计划编号
         */
        private String planCode;
        private String equipName;

        private String userList;
        private String taskId;

        private String procInstId;
        
        public String getEquipName()
        {
            return equipName;
        }

        public void setEquipName(String equipName)
        {
            this.equipName = equipName;
        }

        public String getUserList()
        {
            return userList;
        }

        public void setUserList(String userList)
        {
            this.userList = userList;
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
		public String getPlanCode()
        {
            return planCode;
        }
        public void setPlanCode(String planCode)
        {
            this.planCode = planCode;
        }
        public String getTeamName()
        {
            return teamName;
        }
        public void setTeamName(String teamName)
        {
            this.teamName = teamName;
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
        public String getDutyPersonName()
        {
            return dutyPersonName;
        }
        public void setDutyPersonName(String dutyPersonName)
        {
            this.dutyPersonName = dutyPersonName;
        }
        public static long getSerialversionuid()
        {
            return serialVersionUID;
        }
        @JsonSerialize(using = JsonDateTime1Serializer.class)  

        public Date getWorkTime()
        {
            return workTime;
        }
        @JsonDeserialize(using = JsonDateTime1Deserializer.class)

        public void setWorkTime(Date workTime)
        {
            this.workTime = workTime;
        }

        public Integer getDutyPersonId(){
			return dutyPersonId;
		}
		public void setDutyPersonId(Integer dutyPersonId){
			this.dutyPersonId = dutyPersonId;
		}
		public String getWorkType(){
			return workType;
		}
		public void setWorkType(String workType){
			this.workType = workType;
		}
		public String getConclusion(){
			return conclusion;
		}
		public void setConclusion(String conclusion){
			this.conclusion = conclusion;
		}
		public String getCode(){
			return code;
		}
		public void setCode(String code){
			this.code = code;
		}
		public Integer getPlanId(){
			return planId;
		}
		public void setPlanId(Integer planId){
			this.planId = planId;
		}
		public String getTeam(){
			return team;
		}
		public void setTeam(String team){
			this.team = team;
		}
		public String getQuestion(){
			return question;
		}
		public void setQuestion(String question){
			this.question = question;
		}
		public Integer getDelayStates(){
			return delayStates;
		}
		public void setDelayStates(Integer delayStates){
			this.delayStates = delayStates;
		}
		public Integer getDeviceId(){
			return deviceId;
		}
		public void setDeviceId(Integer deviceId){
			this.deviceId = deviceId;
		}
		public Integer getUnitId(){
			return unitId;
		}
		public void setUnitId(Integer unitId){
			this.unitId = unitId;
		}
		public String getJoinPersonIds(){
			return joinPersonIds;
		}
		public void setJoinPersonIds(String joinPersonIds){
			this.joinPersonIds = joinPersonIds;
		}
}