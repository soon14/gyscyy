package com.aptech.business.ticketManage.workTicketEquip.domain;

import java.util.Date;
import com.aptech.framework.json.JsonDateDeserializer;
import com.aptech.framework.json.JsonDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.apache.ibatis.type.Alias;
import com.aptech.framework.orm.BaseEntity;

/**
 * 
 * 两票设备关系表实体类
 *
 * @author 
 * @created 2018-06-20 14:24:31
 * @lastModified 
 * @history
 *
 */
@Alias("WorkTicketEquipEntity")
public class WorkTicketEquipEntity extends BaseEntity{
		/**
		 * 主键
		 */
    	private Long id;
		/**
		 * 设备Id
		 */
    	private String equipId;
		/**
		 * 设备名称
		 */
    	private String equipName;
		/**
		 * 创建人
		 */
    	private String createUserId;
		/**
		 * 创建时间
		 */
    	private Date createDate;
		/**
		 * 设备编码
		 */
    	private String equipCode;
		/**
		 * 工作票Id
		 */
    	private Long workticketId;
		/**
		 * 状态
		 */
    	private String status;
    	//区分是工作票还是操作票(1、工作票，2、操作票,3、紧急抢修工作票)
    	private String ticketType;
    	
    	
    	
    	
		public String getTicketType() {
			return ticketType;
		}
		public void setTicketType(String ticketType) {
			this.ticketType = ticketType;
		}
		public Long getId(){
			return id;
		}
		public void setId(Long id){
			this.id = id;
		}
		public String getEquipId(){
			return equipId;
		}
		public void setEquipId(String equipId){
			this.equipId = equipId;
		}
		public String getEquipName(){
			return equipName;
		}
		public void setEquipName(String equipName){
			this.equipName = equipName;
		}
		public String getCreateUserId(){
			return createUserId;
		}
		public void setCreateUserId(String createUserId){
			this.createUserId = createUserId;
		}
	    @JsonSerialize(using = JsonDateSerializer.class)
		public Date getCreateDate(){
			return createDate;
		}
	    @JsonDeserialize(using = JsonDateDeserializer.class)
		public void setCreateDate(Date createDate){
			this.createDate = createDate;
		}
		public String getEquipCode(){
			return equipCode;
		}
		public void setEquipCode(String equipCode){
			this.equipCode = equipCode;
		}
		public Long getWorkticketId(){
			return workticketId;
		}
		public void setWorkticketId(Long workticketId){
			this.workticketId = workticketId;
		}
		public String getStatus(){
			return status;
		}
		public void setStatus(String status){
			this.status = status;
		}
}