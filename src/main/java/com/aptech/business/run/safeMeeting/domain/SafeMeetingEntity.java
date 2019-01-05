package com.aptech.business.run.safeMeeting.domain;

import java.util.Date;

import org.apache.ibatis.type.Alias;

import com.aptech.framework.json.JsonDateTime1Deserializer;
import com.aptech.framework.json.JsonDateTime1Serializer;
import com.aptech.framework.orm.BaseEntity;
import com.aptech.framework.util.DateFormatUtil;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * 
 * 安全例会实体类
 *
 * @author 
 * @created 2017-06-07 16:00:28
 * @lastModified 
 * @history
 *
 */
@Alias("safeMeetingEntity")
public class SafeMeetingEntity extends BaseEntity{
		/**
    * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
    */
    private static final long serialVersionUID = -1110266771122760764L;
        /**
		 * 运行日志ID
		 */
    	private Integer rlId;
		/**
		 * 会议内容
		 */
    	private String meetingContent;
		/**
		 * 措施检查状态
		 */
    	private String checkState;
		/**
		 * 会议种类
		 */
    	private Integer meetingFlag;
        /**
         * 措施检查状态
         */
        private String checkStateName;
        /**
         * 负责人
         */
        private String fZR;
        /**
         * 安排时间
         */
        private Date workTime;
        private String workTimeString;
        private int number;
        private Date receptDate;
        private String receptDateString;
        
        
        
        @JsonSerialize(using = JsonDateTime1Serializer.class)	
		public Date getReceptDate() {
			return receptDate;
		}
        @JsonDeserialize(using = JsonDateTime1Deserializer.class)
		public void setReceptDate(Date receptDate) {
			this.receptDate = receptDate;
		}
        public String getReceptDateString() {
			DateFormatUtil format = DateFormatUtil.getInstance("yyyy-MM-dd HH:mm");
			if(this.receptDate!=null){
				return this.receptDateString = format.format(this.receptDate);
			}else{
				return null;
			}
		}
		public int getNumber() {
			return number;
		}
		public void setNumber(int number) {
			this.number = number;
		}
		public String getfZR()
        {
            return fZR;
        }
        public void setfZR(String fZR)
        {
            this.fZR = fZR;
        }
        public String getCheckStateName()
        {
            return checkStateName;
        }
        public void setCheckStateName(String checkStateName)
        {
            this.checkStateName = checkStateName;
        }
        public Integer getRlId(){
			return rlId;
		}
		public void setRlId(Integer rlId){
			this.rlId = rlId;
		}
		public String getMeetingContent(){
			return meetingContent;
		}
		public void setMeetingContent(String meetingContent){
			this.meetingContent = meetingContent;
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
        public Integer getMeetingFlag(){
			return meetingFlag;
		}
		public void setMeetingFlag(Integer meetingFlag){
			this.meetingFlag = meetingFlag;
		}
		 @JsonSerialize(using = JsonDateTime1Serializer.class)	
		public Date getWorkTime() {
			return workTime;
		}
		 @JsonDeserialize(using = JsonDateTime1Deserializer.class)
		public void setWorkTime(Date workTime) {
			this.workTime = workTime;
		}
		public String getWorkTimeString() {
			DateFormatUtil format = DateFormatUtil.getInstance("yyyy-MM-dd HH:mm");
			if(this.workTime!=null){
				return this.workTimeString = format.format(this.workTime);
			}else{
				return null;
			}
		}
		public void setWorkTimeString(String workTimeString) {
			this.workTimeString = workTimeString;
		}
}