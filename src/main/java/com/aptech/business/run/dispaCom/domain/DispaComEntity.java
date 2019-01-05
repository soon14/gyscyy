package com.aptech.business.run.dispaCom.domain;

import java.util.Date;

import org.apache.ibatis.type.Alias;

import com.aptech.framework.json.JsonDateTime1Deserializer;
import com.aptech.framework.json.JsonDateTime1Serializer;
import com.aptech.framework.json.JsonDateTimeSerializer;
import com.aptech.framework.orm.BaseEntity;
import com.aptech.framework.util.DateFormatUtil;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * 
 * 调度命令实体类
 *
 * @author 
 * @created 2017-06-07 11:31:01
 * @lastModified 
 * @history
 *
 */
@Alias("dispaComEntity")
public class DispaComEntity extends BaseEntity{
		/**
    * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
    */
    private static final long serialVersionUID = 5999084938605643958L;
        /**
		 * 调度
		 */
    	private String dispath;
		/**
		 * 调度人员
		 */
    	private String dispatchPerson;
		/**
		 * 时间
		 */
    	private Date time;
    	private String timeString;
		/**
		 * 编码
		 */
    	private String code;
		/**
		 * 联系内容
		 */
    	private String contactContent;
		/**
		 * 受令人
		 */
    	private String dutyChiefPerson;
		public void setDutyChiefPerson(String dutyChiefPerson) {
			this.dutyChiefPerson = dutyChiefPerson;
		}
		
		public String getDutyChiefPerson() {
			return dutyChiefPerson;
		}

		/**
		 * 单位ID
		 */
    	private Integer unitId;
    	/**
         * 调度
         */
        private String dispathName;
    	/**
         * 单位名称
         */
        private String unitName;
        /**
         * 受令人姓名
         */
        private String dutyChiefName;
        /**
         * 创建人ID
         */
        private Integer createUserId;
		/**
         * 创建人登陆名
         */
        private String createUserName;
        /**
         * 创建人时间
         */
        private Date createDate;
        /**
         * 更新时间
         */
        private Date updateDate;
        /**
         * 受令人姓名
         */
        private String dutyChiefPersonName;
        private int number;
        private String dutyChiefPersonString;
        
        
        
        
        
        public String getDutyChiefPersonString() {
			return dutyChiefPersonString;
		}

		public void setDutyChiefPersonString(String dutyChiefPersonString) {
			this.dutyChiefPersonString = dutyChiefPersonString;
		}

		public int getNumber() {
			return number;
		}
		public void setNumber(int number) {
			this.number = number;
		}
		public String getDutyChiefPersonName() {
			return dutyChiefPersonName;
		}
		public void setDutyChiefPersonName(String dutyChiefPersonName) {
			this.dutyChiefPersonName = dutyChiefPersonName;
		}
		public String getTimeString() {
        	if(timeString !=null && timeString !=""){
				return timeString;
			}
			if(this.time ==null){
				return timeString;
			}
			DateFormatUtil df= DateFormatUtil.getInstance("yyyy-MM-dd HH:mm");
			return df.format(this.time);
		}
		public void setTimeString(String timeString) {
			this.timeString = timeString;
		}
		public String getUnitName()
        {
            return unitName;
        }
        public void setUnitName(String unitName)
        {
            this.unitName = unitName;
        }
        public String getDutyChiefName()
        {
            return dutyChiefName;
        }
        public void setDutyChiefName(String dutyChiefName)
        {
            this.dutyChiefName = dutyChiefName;
        }
        public String getDispathName()
        {
            return dispathName;
        }
        public void setDispathName(String dispathName)
        {
            this.dispathName = dispathName;
        }
        public static long getSerialversionuid()
        {
            return serialVersionUID;
        }
        public String getDispath(){
			return dispath;
		}
		public void setDispath(String dispath){
			this.dispath = dispath;
		}
		public String getDispatchPerson(){
			return dispatchPerson;
		}
		public void setDispatchPerson(String dispatchPerson){
			this.dispatchPerson = dispatchPerson;
		}
        @JsonSerialize(using = JsonDateTime1Serializer.class)   
        public Date getTime()
        {
            return time;
        }
        @JsonDeserialize(using = JsonDateTime1Deserializer.class)
        public void setTime(Date time)
        {
            this.time = time;
        }
        public String getCode(){
			return code;
		}
		public void setCode(String code){
			this.code = code;
		}
		
		public String getContactContent()
        {
            return contactContent;
        }
        public void setContactContent(String contactContent)
        {
            this.contactContent = contactContent;
        }
		public Integer getUnitId(){
			return unitId;
		}
		public void setUnitId(Integer unitId){
			this.unitId = unitId;
		}
	    public Integer getCreateUserId() {
			return createUserId;
		}
		public void setCreateUserId(Integer createUserId) {
			this.createUserId = createUserId;
		}
		public String getCreateUserName() {
			return createUserName;
		}
		public void setCreateUserName(String createUserName) {
			this.createUserName = createUserName;
		}
		@JsonSerialize(using = JsonDateTimeSerializer.class)
		public Date getCreateDate() {
			return createDate;
		}
		@JsonSerialize(using = JsonDateTimeSerializer.class)
		public void setCreateDate(Date createDate) {
			this.createDate = createDate;
		}
		@JsonSerialize(using = JsonDateTimeSerializer.class)
		public Date getUpdateDate() {
			return updateDate;
		}
		@JsonSerialize(using = JsonDateTimeSerializer.class)
		public void setUpdateDate(Date updateDate) {
			this.updateDate = updateDate;
		}
}