package com.aptech.business.run.workPlan.domain;

import java.util.Date;

import org.apache.ibatis.type.Alias;

import com.aptech.business.component.dictionary.ProtectStatusEnum;
import com.aptech.framework.json.JsonDateTimeDeserializer;
import com.aptech.framework.json.JsonDateTimeSerializer;
import com.aptech.framework.orm.BaseEntity;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * 
 * 定期工作计划实体类
 *
 * @author 
 * @created 2017-05-27 11:40:09
 * @lastModified 
 * @history
 *
 */
@Alias("workPlanEntity")
public class WorkPlanEntity extends BaseEntity{
		/**
    * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
    */
    private static final long serialVersionUID = 1L;
        /**
		 * 时间
		 */
    	private String time;
		/**
		 * 计划指定人ID
		 */
    	private Integer planPersonId;
		/**
		 * 备注
		 */
    	private String remark;
		/**
		 * 编码
		 */
    	private String code;
		/**
		 * 附件URL
		 */
    	private String attachmentUrl;
		/**
		 * 附件名称
		 */
    	private String attachmentName;
		/**
		 * 时间维度
		 */
    	private String timeDimension;
		/**
		 * 单位ID
		 */
    	private Integer unitId;
    	/**
         * 审批状态
         */
    	private String checkState;
    	/**
         * 编码
         */
        private String unitName;
        /**
         * 编码
         */
        private String planPersonName;
        /**
         * 时间维度名称
         */
        private String timeDimensionName;
      //（审批意见）
        private String approveIdea;
        private String userList;
        private String taskId;

        private String procInstId;
        /**
         * 保存还是提交   
         */
        private String saveOrSubmit;
        /**
         * selectUser   审批人
         */
        private String selectUser;
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
        public String getTimeDimensionName()
        {
            return timeDimensionName;
        }
        public void setTimeDimensionName(String timeDimensionName)
        {
            this.timeDimensionName = timeDimensionName;
        }
        public String getUnitName()
        {
            return unitName;
        }
        public void setUnitName(String unitName)
        {
            this.unitName = unitName;
        }
        public String getPlanPersonName()
        {
            return planPersonName;
        }
        public void setPlanPersonName(String planPersonName)
        {
            this.planPersonName = planPersonName;
        }
        
        public String getCheckState()
        {
            return checkState;
        }
        public void setCheckState(String checkState)
        {
            this.checkState = checkState;
        }
        public static long getSerialversionuid()
        {
            return serialVersionUID;
        }
        public String getTime()
        {
            return time;
        }
        public void setTime(String time)
        {
            this.time = time;
        }
        public Integer getPlanPersonId(){
			return planPersonId;
		}
		public void setPlanPersonId(Integer planPersonId){
			this.planPersonId = planPersonId;
		}
		public String getRemark(){
			return remark;
		}
		public void setRemark(String remark){
			this.remark = remark;
		}
		public String getCode(){
			return code;
		}
		public void setCode(String code){
			this.code = code;
		}
		public String getAttachmentUrl(){
			return attachmentUrl;
		}
		public void setAttachmentUrl(String attachmentUrl){
			this.attachmentUrl = attachmentUrl;
		}
		public String getAttachmentName(){
			return attachmentName;
		}
		public void setAttachmentName(String attachmentName){
			this.attachmentName = attachmentName;
		}
		public String getTimeDimension(){
			return timeDimension;
		}
		public void setTimeDimension(String timeDimension){
			this.timeDimension = timeDimension;
		}
		public Integer getUnitId(){
			return unitId;
		}
		public void setUnitId(Integer unitId){
			this.unitId = unitId;
		}

        public String getApproveIdea()
        {
            return approveIdea;
        }

        public void setApproveIdea(String approveIdea)
        {
            this.approveIdea = approveIdea;
        }

        public String getSaveOrSubmit()
        {
            return saveOrSubmit;
        }

        public void setSaveOrSubmit(String saveOrSubmit)
        {
            this.saveOrSubmit = saveOrSubmit;
        }

        public String getSelectUser()
        {
            return selectUser;
        }

        public void setSelectUser(String selectUser)
        {
            this.selectUser = selectUser;
        }
		
}