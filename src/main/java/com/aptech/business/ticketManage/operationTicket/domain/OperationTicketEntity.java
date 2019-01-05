package com.aptech.business.ticketManage.operationTicket.domain;

import java.util.Date;

import org.apache.ibatis.type.Alias;

import com.aptech.business.component.dictionary.IdentifyEnum;
import com.aptech.business.component.dictionary.IssetEnum;
import com.aptech.business.component.dictionary.OperationStatusEnum;
import com.aptech.framework.json.JsonDateTime1Deserializer;
import com.aptech.framework.json.JsonDateTime1Serializer;
import com.aptech.framework.json.JsonDateTimeDeserializer;
import com.aptech.framework.json.JsonDateTimeSerializer;
import com.aptech.framework.orm.BaseEntity;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * 
 * 操作票实体类
 * 
 * @author
 * @created 2017-07-12 15:53:44
 * @lastModified
 * @history
 * 
 */
@Alias("OperationTicketEntity")
public class OperationTicketEntity extends BaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6445818545722404759L;

	/**
	 * 主键
	 */
	private Long id;

	private Long oldid;
	/**
	 * 编号
	 */
	private String code;
	/**
	 * 单位id
	 */
	private Long unitId;
	/**
	 * 单位
	 */
	private String unitName;
	/**
	 * 设备编号
	 */
	private String equipmentCode;
	/**
	 * 设备名称
	 */
	private String equipmentName;
	/**
	 * 开始时间
	 */
	private Date startDate;
	/**
	 * 终了时间
	 */
	private Date endDate;
	/**
	 * 开始时间
	 */
	private String startDateString;
	/**
	 * 终了时间
	 */
	private String endDateString;
	/**
	 * 工作票票号
	 */
	private String workticketCode;
	/**
	 * 缺陷单编号
	 */
	private String defectCode;
	/**
	 * 操作内容
	 */
	private String workText;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 附件
	 */
	private String fileId;
	/**
	 * 是否设置为典型票
	 */
	private Long isSet;
	/**
	 * 操作人名字
	 */
	private String operateName;
	/**
	 * 操作人id
	 */
	private Long operateId;
	/**
	 * 监护人名字
	 */
	private String guardianName;
	/**
	 * 监护人ID
	 */
	private Long guardianId;
	/**
	 * 监护人确认时间
	 */
	private Date guardianDate;
	/**
	 * 值班负责人名字
	 */
	private String picName;
	/**
	 * 值班负责人ID
	 */
	private Long picId;
	/**
	 * 值长名字
	 */
	private String workManagerName;
	/**
	 * 值长id
	 */
	private Long workManagerId;
	/**
	 * 状态
	 */
	private Long status;
	
	/**
	 * 鉴定结果 1合格 2不合格
	 */
	private String identify;
	/**
	 * 设备名称
	 */
	private String equipName;
	
	public String getEquipName() {
		return equipName;
	}
	public void setEquipName(String equipName) {
		this.equipName = equipName;
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
		return "";
	}

	/**
	 * 鉴定结果 1合格 2不合格
	 */
	private Date invalidDate;
	/**
	 * 鉴定结果 1合格 2不合格
	 */
	private String invalidId;
	/**
	 * 鉴定结果 1合格 2不合格
	 */
	private String invalidContent;
	/**
	 * 状态
	 */
	@SuppressWarnings("unused")
	private String statusName;
	/**
	 * 终止原因
	 */
	private String reason;
	/**
	 * 是否设置为典型票
	 */
	/**
	 * 操作票创建时间
	 */
	
	private Date operationCreateDate;

	@JsonSerialize(using = JsonDateTimeSerializer.class)
	public Date getInvalidDate() {
		return invalidDate;
	}
	@JsonDeserialize(using = JsonDateTimeDeserializer.class)
	public void setInvalidDate(Date invalidDate) {
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

	@SuppressWarnings("unused")
	private String isSetName;

	private String taskId;

	private String procInstId;

	private String userList;

	private String typicalTicketId;
	/**
	 * 区分典型票，还是普通票(1本身的工作票，0存入的是典型票)
	 */
	private Integer istypical;
	/**
	 * 操作项目当前顺序
	 */
	private Integer operateItemNum;
	
	private Integer groupId;
	private String groupName;
	private Integer flag;
	
	private Integer startUserId;
	
	private String startUserName;
	
	private Integer startUnitId;
	
	private String startUnitName;
	
	private Integer endUserId;
	
	private String endUserName;
	
	private Date 	endTime;
	
    private String typicalName;
    
    private String typicalType;
    
    
    private String typicalFlag;
    
    
    
	public String getTypicalFlag() {
		return typicalFlag;
	}
	public void setTypicalFlag(String typicalFlag) {
		this.typicalFlag = typicalFlag;
	}
	public String getTypicalType() {
		return typicalType;
	}
	public void setTypicalType(String typicalType) {
		this.typicalType = typicalType;
	}
	public String getTypicalName() {
		return typicalName;
	}

	public void setTypicalName(String typicalName) {
		this.typicalName = typicalName;
	}
	
	public Integer getStartUserId() {
		return startUserId;
	}

	public void setStartUserId(Integer startUserId) {
		this.startUserId = startUserId;
	}

	public String getStartUserName() {
		return startUserName;
	}

	public void setStartUserName(String startUserName) {
		this.startUserName = startUserName;
	}

	public Integer getStartUnitId() {
		return startUnitId;
	}

	public void setStartUnitId(Integer startUnitId) {
		this.startUnitId = startUnitId;
	}

	public String getStartUnitName() {
		return startUnitName;
	}

	public void setStartUnitName(String startUnitName) {
		this.startUnitName = startUnitName;
	}

	public Integer getEndUserId() {
		return endUserId;
	}

	public void setEndUserId(Integer endUserId) {
		this.endUserId = endUserId;
	}

	public String getEndUserName() {
		return endUserName;
	}

	public void setEndUserName(String endUserName) {
		this.endUserName = endUserName;
	}
	@JsonSerialize(using = JsonDateTimeSerializer.class)
	public Date getEndTime() {
		return endTime;
	}
	@JsonDeserialize(using = JsonDateTimeDeserializer.class)
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public Integer getFlag() {
		return flag;
	}

	
	@JsonSerialize(using = JsonDateTimeSerializer.class)
	public Date getOperationCreateDate() {
		return operationCreateDate;
	}
	@JsonDeserialize(using = JsonDateTimeDeserializer.class)
	public void setOperationCreateDate(Date operationCreateDate) {
		this.operationCreateDate = operationCreateDate;
	}
	
	
	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	public Integer getOperateItemNum() {
		return operateItemNum;
	}

	public void setOperateItemNum(Integer operateItemNum) {
		this.operateItemNum = operateItemNum;
	}

	public Integer getIstypical() {
		return istypical;
	}

	public void setIstypical(Integer istypical) {
		this.istypical = istypical;
	}

	public String getTypicalTicketId() {
		return typicalTicketId;
	}

	public void setTypicalTicketId(String typicalTicketId) {
		this.typicalTicketId = typicalTicketId;
	}

	public String getUserList() {
		return userList;
	}

	public void setUserList(String userList) {
		this.userList = userList;
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

	public String getStatusName() {
		for (OperationStatusEnum operationStatusEnum : OperationStatusEnum
				.values()) {
			if (operationStatusEnum.getCode().equals(
					String.valueOf(getStatus()))) {
				return operationStatusEnum.getName();
			}
		}
		return "";
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getIsSetName() {
		for (IssetEnum issetEnum : IssetEnum.values()) {
			if (issetEnum.getCode().equals(String.valueOf(getIsSet()))) {
				return issetEnum.getName();
			}
		}
		return "";
	}

	public String getStartDateString() {
		return startDateString;
	}

	public void setStartDateString(String startDateString) {
		this.startDateString = startDateString;
	}

	public String getEndDateString() {
		return endDateString;
	}

	public void setEndDateString(String endDateString) {
		this.endDateString = endDateString;
	}

	public void setIsSetName(String isSetName) {
		this.isSetName = isSetName;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public Long getOldid() {
		return oldid;
	}

	public void setOldid(Long oldid) {
		this.oldid = oldid;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Long getUnitId() {
		return unitId;
	}

	public void setUnitId(Long unitId) {
		this.unitId = unitId;
	}

	public String getEquipmentCode() {
		return equipmentCode;
	}

	public void setEquipmentCode(String equipmentCode) {
		this.equipmentCode = equipmentCode;
	}

	public String getEquipmentName() {
		return equipmentName;
	}

	public void setEquipmentName(String equipmentName) {
		this.equipmentName = equipmentName;
	}

	@JsonSerialize(using = JsonDateTime1Serializer.class)
	public Date getStartDate() {
		return startDate;
	}

	@JsonDeserialize(using = JsonDateTime1Deserializer.class)
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	@JsonSerialize(using = JsonDateTime1Serializer.class)
	public Date getEndDate() {
		return endDate;
	}

	@JsonDeserialize(using = JsonDateTime1Deserializer.class)
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getWorkticketCode() {
		return workticketCode;
	}

	public void setWorkticketCode(String workticketCode) {
		this.workticketCode = workticketCode;
	}

	public String getDefectCode() {
		return defectCode;
	}

	public void setDefectCode(String defectCode) {
		this.defectCode = defectCode;
	}

	public String getWorkText() {
		return workText;
	}

	public void setWorkText(String workText) {
		this.workText = workText;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public Long getIsSet() {
		return isSet;
	}

	public void setIsSet(Long isSet) {
		this.isSet = isSet;
	}

	public String getOperateName() {
		return operateName;
	}

	public void setOperateName(String operateName) {
		this.operateName = operateName;
	}

	public Long getOperateId() {
		return operateId;
	}

	public void setOperateId(Long operateId) {
		this.operateId = operateId;
	}

	public String getGuardianName() {
		return guardianName;
	}

	public void setGuardianName(String guardianName) {
		this.guardianName = guardianName;
	}

	public Long getGuardianId() {
		return guardianId;
	}

	public void setGuardianId(Long guardianId) {
		this.guardianId = guardianId;
	}

	@JsonSerialize(using = JsonDateTimeSerializer.class)
	public Date getGuardianDate() {
		return guardianDate;
	}

	@JsonDeserialize(using = JsonDateTimeDeserializer.class)
	public void setGuardianDate(Date guardianDate) {
		this.guardianDate = guardianDate;
	}

	public String getPicName() {
		return picName;
	}

	public void setPicName(String picName) {
		this.picName = picName;
	}

	public Long getPicId() {
		return picId;
	}

	public void setPicId(Long picId) {
		this.picId = picId;
	}

	public String getWorkManagerName() {
		return workManagerName;
	}

	public void setWorkManagerName(String workManagerName) {
		this.workManagerName = workManagerName;
	}

	public Long getWorkManagerId() {
		return workManagerId;
	}

	public void setWorkManagerId(Long workManagerId) {
		this.workManagerId = workManagerId;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public String getIdentify() {
		return identify;
	}

	public void setIdentify(String identify) {
		this.identify = identify;
	}
}