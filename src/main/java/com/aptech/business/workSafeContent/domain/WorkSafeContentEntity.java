package com.aptech.business.workSafeContent.domain;

import java.util.Date;

import org.apache.ibatis.type.Alias;

import com.aptech.framework.json.JsonDateTimeDeserializer;
import com.aptech.framework.json.JsonDateTimeSerializer;
import com.aptech.framework.orm.BaseEntity;
import com.aptech.framework.util.DateFormatUtil;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;


/**
 * 
 * 安全措施信息
 *
 * @author 
 * @created 2018-11-16
 * @lastModified 
 * @history
 *
 */
@Alias("WorkSafeContentEntity")
public class WorkSafeContentEntity extends BaseEntity{
		/**
		 * 主键
		 */
    	private Long id;
		/**
		 * 名称
		 */
    	private String content;
		/**
		 * 创建人
		 */
    	private String createUserId;
		/**
		 * 创建时间
		 */
    	private Date createDate;
    	
    	private String createDateString;
    	private String userName;
    	
    	private Long unitId;//单位名称Id
    	
    	private String unitName;
    	
    	
		public String getCreateDateString() {
			
			if (createDateString !=null && createDateString !="") {
				return createDateString;
			}
			DateFormatUtil df= DateFormatUtil.getInstance("yyyy-MM-dd HH:mm");
			if (this.createDate==null) {
				return createDateString;
			}
			return df.format(this.createDate);
		}
		
		public void setCreateDateString(String createDateString) {
			this.createDateString = createDateString;
		}
		public String getUserName() {
			return userName;
		}
		public void setUserName(String userName) {
			this.userName = userName;
		}
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public String getContent() {
			return content;
		}
		public void setContent(String content) {
			this.content = content;
		}
		public String getCreateUserId() {
			return createUserId;
		}
		public void setCreateUserId(String createUserId) {
			this.createUserId = createUserId;
		}
		@JsonSerialize(using = JsonDateTimeSerializer.class)
		public Date getCreateDate() {
			return createDate;
		}
	    @JsonDeserialize(using = JsonDateTimeDeserializer.class)
		public void setCreateDate(Date createDate) {
			this.createDate = createDate;
		}

		public Long getUnitId() {
			return unitId;
		}

		public void setUnitId(Long unitId) {
			this.unitId = unitId;
		}

		public String getUnitName() {
			return unitName;
		}

		public void setUnitName(String unitName) {
			this.unitName = unitName;
		}
    	
    	
}	
		
		
		
		