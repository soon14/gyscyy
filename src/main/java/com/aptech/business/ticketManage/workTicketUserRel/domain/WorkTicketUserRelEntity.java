package com.aptech.business.ticketManage.workTicketUserRel.domain;

import java.util.Date;

import org.apache.ibatis.type.Alias;

import com.aptech.framework.json.JsonDateTime1Deserializer;
import com.aptech.framework.json.JsonDateTime1Serializer;
import com.aptech.framework.orm.BaseEntity;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * 
 * 电气工作票实体类
 *
 * @author 
 * @created 2017-06-05 17:12:46
 * @lastModified 
 * @history
 *
 */
@Alias("workTicketUserRelEntity")
public class WorkTicketUserRelEntity extends BaseEntity{
		/**
	 * 
	 */
	private static final long serialVersionUID = -311418022093056155L;
	/**
	 * 两票ID
	 */
	private Long workTicketId;
	/**
	 * 用户Id 
	 */
	private Long userId;
	/**
	 * 两票三种人类型1 签发人 2 工作负责人 3 
	 */
	private int type;
	public Long getWorkTicketId() {
		return workTicketId;
	}
	public void setWorkTicketId(Long workTicketId) {
		this.workTicketId = workTicketId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
		
}