package com.aptech.business.ticketManage.controlCardRisk.domain;

import java.util.Date;

import org.apache.ibatis.type.Alias;

import com.aptech.framework.json.JsonDateTimeDeserializer;
import com.aptech.framework.json.JsonDateTimeSerializer;
import com.aptech.framework.orm.BaseEntity;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * 
 * 工作票控制卡风险与措施实体类
 *
 * @author 
 * @created 2017-06-05 17:12:23
 * @lastModified 
 * @history
 *
 */
@Alias("controlCardRiskEntity")
public class ControlCardRiskEntity extends BaseEntity{
		/**
		 * 主键
		 */
    	private Long id;
		/**
		 * 主要控制措施
		 */
    	private String mainControl;
		/**
		 * 危险点分析
		 */
    	private String dangerPoint;
		/**
		 * 创建时间
		 */
    	private Date createDate;
		/**
		 * 工作票危险因素控制卡的id
		 */
    	private Long controlId;
		/**
		 * 修改时间
		 */
    	private Date updateDate;
		/**
		 * 创建人
		 */
    	private String createUserId;
    	
    	private String uuidCode;
    	

		public String getUuidCode() {
			return uuidCode;
		}
		public void setUuidCode(String uuidCode) {
			this.uuidCode = uuidCode;
		}
		public Long getId(){
			return id;
		}
		public void setId(Long id){
			this.id = id;
		}
		public String getMainControl(){
			return mainControl;
		}
		public void setMainControl(String mainControl){
			this.mainControl = mainControl;
		}
		public String getDangerPoint(){
			return dangerPoint;
		}
		public void setDangerPoint(String dangerPoint){
			this.dangerPoint = dangerPoint;
		}
	    @JsonSerialize(using = JsonDateTimeSerializer.class)
		public Date getCreateDate(){
			return createDate;
		}
	    @JsonDeserialize(using = JsonDateTimeDeserializer.class)
		public void setCreateDate(Date createDate){
			this.createDate = createDate;
		}
		public Long getControlId(){
			return controlId;
		}
		public void setControlId(Long controlId){
			this.controlId = controlId;
		}
	    @JsonSerialize(using = JsonDateTimeSerializer.class)
		public Date getUpdateDate(){
			return updateDate;
		}
	    @JsonDeserialize(using = JsonDateTimeDeserializer.class)
		public void setUpdateDate(Date updateDate){
			this.updateDate = updateDate;
		}
		public String getCreateUserId(){
			return createUserId;
		}
		public void setCreateUserId(String createUserId){
			this.createUserId = createUserId;
		}
}