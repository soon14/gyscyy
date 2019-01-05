package com.aptech.business.ticketManage.typicalTicket.domain;

import java.util.Date;

import org.apache.ibatis.type.Alias;

import com.aptech.business.component.dictionary.IdentifyEnum;
import com.aptech.business.component.dictionary.TypicalTicketApproveStatusEnum;
import com.aptech.business.component.dictionary.TypicalTicketTypeEnum;
import com.aptech.framework.json.JsonDateTimeDeserializer;
import com.aptech.framework.json.JsonDateTimeSerializer;
import com.aptech.framework.orm.BaseEntity;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * 
 * 典型票实体类
 * 
 * @author
 * @created 2017-07-20 15:55:55
 * @lastModified
 * @history
 * 
 */
@Alias("TypicalTicketEntity")
public class TypicalTicketEntity extends BaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4197929097424129579L;
	/**
	 * 主键
	 */
	private Long id;
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 类型
	 */
	private String type;
	/**
	 * 单位id
	 */
	private Long unitId;
	/**
	 * 单位名称
	 */
	private String unitName;
	/**
	 * 设置人
	 */
	private Long createPersonId;
	/**
	 * 设置人
	 */
	private String createPersonName;
	/**
	 * 状态
	 */
	private Long approveStatus;
	/**
	 * 区分是工作票还是操作票
	 */
	private Long ticketType;
	/**
	 * 对应各种票的主键id
	 */
	private Long workticket_id;
	/**
	 * 是否删除
	 */
	private Long status;
	/**
	 * 创建时间
	 */
	private Date createDate;
	/**
	 * 创建时间
	 */
	private String createDateString;

	private String taskId;

	private String procInstId;

	private String userList;
	
	private String istypical;//0  自己新增的典型票，   1  设置的典型票
	
	//鉴定时间
   	private String invalidDate;
    //鉴定人
	private String invalidId;
	//鉴定备注
	private String invalidContent;
	/**
	 * 鉴定结果
	 */
	private String identify;
	
	private String flag;//按钮显示标志
	
	
	
	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getIdentifyName() {
		for (IdentifyEnum identifyEnumEnum : IdentifyEnum.values()) {
			if(this.identify!=null){
				if (identifyEnumEnum.getCode().equals(this.identify.toString())) {
					return identifyEnumEnum.getName();
				}
			}else{
				return "";
			}
		}
		return null;
	}

	public String getInvalidDate() {
		return invalidDate;
	}

	public void setInvalidDate(String invalidDate) {
		this.invalidDate = invalidDate;
	}

	public String getInvalidId() {
		return invalidId;
	}

	public void setInvalidId(String invalidId) {
		this.invalidId = invalidId;
	}

	public String getInvalidContent() {
		return invalidContent;
	}

	public void setInvalidContent(String invalidContent) {
		this.invalidContent = invalidContent;
	}

	public String getIdentify() {
		return identify;
	}

	public void setIdentify(String identify) {
		this.identify = identify;
	}

	public String getIstypical() {
		return istypical;
	}

	public void setIstypical(String istypical) {
		this.istypical = istypical;
	}

	public String getCreateDateString() {
		return createDateString;
	}

	public void setCreateDateString(String createDateString) {
		this.createDateString = createDateString;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getProcInstId() {
		return procInstId;
	}

	public void setProcInstId(String procInstId) {
		this.procInstId = procInstId;
	}

	public String getUserList() {
		return userList;
	}

	public void setUserList(String userList) {
		this.userList = userList;
	}

	public Long getId() {
		return id;
	}

	public String getTypeName() {
		for (TypicalTicketTypeEnum typicalTicketTypeEnum : TypicalTicketTypeEnum
				.values()) {
			if (typicalTicketTypeEnum.getCode().equals(
					String.valueOf(getType()))) {
				return typicalTicketTypeEnum.getName();
			}
		}
		return "";
	}

	public String getApproveStatusName() {
		for (TypicalTicketApproveStatusEnum typicalTicketApproveStatusEnum : TypicalTicketApproveStatusEnum
				.values()) {
			if (typicalTicketApproveStatusEnum.getCode().equals(
					String.valueOf(getApproveStatus()))) {
				return typicalTicketApproveStatusEnum.getName();
			}
		}
		return "";
	}

	@JsonSerialize(using = JsonDateTimeSerializer.class)
	public Date getCreateDate() {
		return createDate;
	}

	@JsonDeserialize(using = JsonDateTimeDeserializer.class)
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

	public Long getCreatePersonId() {
		return createPersonId;
	}

	public void setCreatePersonId(Long createPersonId) {
		this.createPersonId = createPersonId;
	}

	public String getCreatePersonName() {
		return createPersonName;
	}

	public void setCreatePersonName(String createPersonName) {
		this.createPersonName = createPersonName;
	}

	public Long getApproveStatus() {
		return approveStatus;
	}

	public void setApproveStatus(Long approveStatus) {
		this.approveStatus = approveStatus;
	}

	public Long getTicketType() {
		return ticketType;
	}

	public void setTicketType(Long ticketType) {
		this.ticketType = ticketType;
	}

	public Long getWorkticket_id() {
		return workticket_id;
	}

	public void setWorkticket_id(Long workticket_id) {
		this.workticket_id = workticket_id;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}
}