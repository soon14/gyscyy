package com.aptech.business.run.runRecord.domain;

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
 * 运行记事实体类
 *
 * @author 
 * @created 2017-06-05 15:28:10
 * @lastModified 
 * @history
 *
 */
@Alias("runRecordEntity")
public class RunRecordEntity extends BaseEntity{
		/**
    * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
    */
    private static final long serialVersionUID = -8244715041820472405L;
        /**
		 * 运行日志ID
		 */
    	private Integer rlId;
		/**
		 * 记录类型
		 */
    	private String recordType;
		/**
		 * 记录时间
		 */
    	private Date recordTime;
    	private String recordTimeString;
		/**
		 * 单位ID
		 */
    	private Integer unitId;
		/**
		 * 记录内容
		 */
    	private String recordContent;
    	/**
         * 记录类型
         */
        private String recordTypeName;
        /**
         * 运行方式
         */
        private String unitName;
        /**
         * 负责人
         */
        private String fZR;
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
        	if(receptDateString !=null && receptDateString !=""){
				return receptDateString;
			}
			DateFormatUtil df= DateFormatUtil.getInstance("yyyy-MM-dd HH:mm");
			if(this.receptDate ==null){
				return receptDateString;
			}
			return df.format(this.receptDate);
		}
		public int getNumber() {
			return number;
		}
		public void setNumber(int number) {
			this.number = number;
		}
		public String getRecordTimeString() {
        	if(recordTimeString !=null && recordTimeString !=""){
				return recordTimeString;
			}
			DateFormatUtil df= DateFormatUtil.getInstance("yyyy-MM-dd HH:mm");
			if(this.recordTime ==null){
				return recordTimeString;
			}
			return df.format(this.recordTime);
		}
		public void setRecordTimeString(String recordTimeString) {
			this.recordTimeString = recordTimeString;
		}
		public String getfZR()
        {
            return fZR;
        }
        public void setfZR(String fZR)
        {
            this.fZR = fZR;
        }
        public String getUnitName()
        {
            return unitName;
        }
        public void setUnitName(String unitName)
        {
            this.unitName = unitName;
        }
        public String getRecordTypeName()
        {
            return recordTypeName;
        }
        public void setRecordTypeName(String recordTypeName)
        {
            this.recordTypeName = recordTypeName;
        }
        public static long getSerialversionuid()
        {
            return serialVersionUID;
        }
        public Integer getRlId(){
			return rlId;
		}
		public void setRlId(Integer rlId){
			this.rlId = rlId;
		}
		public String getRecordType(){
			return recordType;
		}
		public void setRecordType(String recordType){
			this.recordType = recordType;
		}
		public Integer getUnitId(){
			return unitId;
		}
		public void setUnitId(Integer unitId){
			this.unitId = unitId;
		}
		public String getRecordContent(){
			return recordContent;
		}
		public void setRecordContent(String recordContent){
			this.recordContent = recordContent;
		}
        @JsonSerialize(using = JsonDateTime1Serializer.class) 
        public Date getRecordTime()
        {
            return recordTime;
        }
        @JsonDeserialize(using = JsonDateTime1Deserializer.class)
        public void setRecordTime(Date recordTime)
        {
            this.recordTime = recordTime;
        }
		
}