package com.aptech.business.defectManage.defect.domain;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.type.Alias;

import com.aptech.business.component.dictionary.DefectStatusEnum;
import com.aptech.common.system.dictionary.util.DictionaryUtil;
import com.aptech.common.system.dictionary.web.SysDictionaryVO;
import com.aptech.framework.json.JsonDateTimeDeserializer;
import com.aptech.framework.json.JsonDateTimeSerializer;
import com.aptech.framework.orm.BaseEntity;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * 
 * ȱ�ݹ���ʵ����
 * 
 * @author
 * @created 2017-06-02 13:18:00
 * @lastModified
 * @history
 * 
 */
@Alias("defectEntity")
public class DefectEntity extends BaseEntity {
	/**
	 * 缺陷管理
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * �豸ȱ������
	 */
	private String depict;
	/**
	 * ����״̬
	 */
	private String processStatus;

	@SuppressWarnings("unused")
	private String processStatusName;
	/**
	 * ��ע
	 */
	private String remark;
	/**
	 * ���鴦�?��
	 */
	private Long solveUnitId;

	private String solveUnitName;
	/**
	 * �豸����
	 */
	private String equipId;
	/**
	 * 设备名称
	 */
	private String equipName;
	/**
	 * 设备编码
	 */
	private String equipCode;
	private String equipNumber;
	/**
	 * ����
	 */
	private String code;
	/**
	 * ������
	 */
	private Long findUserId;

	private String findUserName;
	/**
	 * ȱ������
	 */
	private String type;
	/**
	 * ȱ������
	 */
	private String typeName;
	/**
	 * �޸�ʱ��
	 */
	private Date updateDate;
	/**
	 * ����id
	 */
	private String fileId;

	private String[] fileName;

	private String[] fileUrl;
	/**
	 * ����
	 */
	private Long id;
	/**
	 * �޸���
	 */
	private String updateUserId;
	/**
	 * ����ʱ��
	 */
	private Date hangTime;
	/**
	 * ����ʱ��
	 */
	private Date findTime;
	/**
	 * ����ʱ��
	 */
	private Date createDate;
	/**
	 * ����������id
	 */
	private Long hangUserId;

	private String hangUserName;
	/**
	 * ��λid
	 */
	private Long unitId;

	private String unitName;
	/**
	 * ������
	 */
	private Long createUserId;
	/**
	 * 鉴定人id
	 */
	private Long appraisalUserId;

	private String appraisalUserName;

	private String taskId;

	private String procInstId;
	
	private String procDefId;
	
	private String userList;
	
	private Integer number;
	
	private Long solveUserId;

	private String solveUserName;
	
	private Date defectTime;
	
	private int lossElectricity;
	
	private String equipType;
	private String equipManageType;
	private String equipManageTypeName;
	private String equipTypeName;
	//处理意见
	private String handlingSuggestion;
	//抄送人
	private String copyUserIds;
	//是否发送信息标识
	private String sendMessage;
	
	
	
	
	public String getSendMessage() {
		return sendMessage;
	}

	public void setSendMessage(String sendMessage) {
		this.sendMessage = sendMessage;
	}

	public String getCopyUserIds() {
		return copyUserIds;
	}

	public void setCopyUserIds(String copyUserIds) {
		this.copyUserIds = copyUserIds;
	}

	public String getEquipCode() {
		return equipCode;
	}

	public void setEquipCode(String equipCode) {
		this.equipCode = equipCode;
	}
	public String getEquipTypeName() {
		return equipTypeName;
	}

	public void setEquipManageTypeName(String equipManageTypeName) {
		this.equipManageTypeName = equipManageTypeName;
	}

	public String getHandlingSuggestion() {
		return handlingSuggestion;
	}

	public void setHandlingSuggestion(String handlingSuggestion) {
		this.handlingSuggestion = handlingSuggestion;
	}

//	public String getEquipTypeName() {
//		Map<String, SysDictionaryVO> equipTypeMap = DictionaryUtil
//				.getDictionaries("DEVICE_TYPE");
//		for(String key :  equipTypeMap.keySet()){
//        	SysDictionaryVO sysDictionaryVO = equipTypeMap.get(key);
//        	if(equipType!=null&&sysDictionaryVO.getCode().equals(equipType.toString())){
//        		return sysDictionaryVO.getName();
//        	}
//        }	
//		return "";
//	}
	
	public String getProcDefId() {
		return procDefId;
	}

	public void setProcDefId(String procDefId) {
		this.procDefId = procDefId;
	}

	
	

	public void setEquipTypeName(String equipTypeName) {
		this.equipTypeName = equipTypeName;
	}

	public String getEquipType() {
		return equipType;
	}

	public void setEquipType(String equipType) {
		this.equipType = equipType;
	}

	public Long getSolveUserId() {
		return solveUserId;
	}

	public void setSolveUserId(Long solveUserId) {
		this.solveUserId = solveUserId;
	}

	public String getSolveUserName() {
		return solveUserName;
	}

	public void setSolveUserName(String solveUserName) {
		this.solveUserName = solveUserName;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public String getEquipNumber() {
		return equipNumber;
	}

	public void setEquipNumber(String equipNumber) {
		this.equipNumber = equipNumber;
	}

	public String getUserList() {
		return userList;
	}

	public void setUserList(String userList) {
		this.userList = userList;
	}

	public String[] getFileName() {
		return fileName;
	}

	public void setFileName(String[] fileName) {
		this.fileName = fileName;
	}

	public String[] getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String[] fileUrl) {
		this.fileUrl = fileUrl;
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

	public String getProcessStatusName() {
		for (DefectStatusEnum defectStatusEnum : DefectStatusEnum.values()) {
			if (defectStatusEnum.getCode().equals(this.getProcessStatus())) {
				return defectStatusEnum.getName();
			}
		}
		return "";
	}

	public void setProcessStatusName(String processStatusName) {
		this.processStatusName = processStatusName;
	}

	public String getTypeName() {
		Map<String, SysDictionaryVO> typeMap = DictionaryUtil.getDictionaries("DEFECT_TYPE");
		for(String key : typeMap.keySet()){
			SysDictionaryVO dispatchTypeVO = typeMap.get(key);
			if (dispatchTypeVO.getCode().equals(type)){
				typeName = dispatchTypeVO.getName();
				break;
			}
		}
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getEquipName() {
		return equipName;
	}

	public void setEquipName(String equipName) {
		this.equipName = equipName;
	}

	public String getSolveUnitName() {
		return solveUnitName;
	}

	public void setSolveUnitName(String solveUnitName) {
		this.solveUnitName = solveUnitName;
	}

	public String getFindUserName() {
		return findUserName;
	}

	public void setFindUserName(String findUserName) {
		this.findUserName = findUserName;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getHangUserName() {
		return hangUserName;
	}

	public void setHangUserName(String hangUserName) {
		this.hangUserName = hangUserName;
	}

	public String getAppraisalUserName() {
		return appraisalUserName;
	}

	public void setAppraisalUserName(String appraisalUserName) {
		this.appraisalUserName = appraisalUserName;
	}

	public Long getAppraisalUserId() {
		return appraisalUserId;
	}

	public void setAppraisalUserId(Long appraisalUserId) {
		this.appraisalUserId = appraisalUserId;
	}

	public String getDepict() {
		return depict;
	}

	public void setDepict(String depict) {
		this.depict = depict;
	}

	public String getProcessStatus() {
		return processStatus;
	}

	public void setProcessStatus(String processStatus) {
		this.processStatus = processStatus;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getSolveUnitId() {
		return solveUnitId;
	}

	public void setSolveUnitId(Long solveUnitId) {
		this.solveUnitId = solveUnitId;
	}

	public String getEquipId() {
		return equipId;
	}

	public void setEquipId(String equipId) {
		this.equipId = equipId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Long getFindUserId() {
		return findUserId;
	}

	public void setFindUserId(Long findUserId) {
		this.findUserId = findUserId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@JsonSerialize(using = JsonDateTimeSerializer.class)
	public Date getUpdateDate() {
		return updateDate;
	}

	@JsonDeserialize(using = JsonDateTimeDeserializer.class)
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUpdateUserId() {
		return updateUserId;
	}

	public void setUpdateUserId(String updateUserId) {
		this.updateUserId = updateUserId;
	}

	@JsonSerialize(using = JsonDateTimeSerializer.class)
	public Date getHangTime() {
		return hangTime;
	}

	@JsonDeserialize(using = JsonDateTimeDeserializer.class)
	public void setHangTime(Date hangTime) {
		this.hangTime = hangTime;
	}

	@JsonSerialize(using = JsonDateTimeSerializer.class)
	public Date getFindTime() {
		return findTime;
	}
	public String getFindTimeString() {
		if(findTime!=null){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return  sdf.format(findTime);
		}
		return "";
	}
	public String getDefectTimeString() {
		if(defectTime!=null){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return  sdf.format(defectTime);
		}
		return "";
	}
	@JsonDeserialize(using = JsonDateTimeDeserializer.class)
	public void setFindTime(Date findTime) {
		this.findTime = findTime;
	}

	@JsonSerialize(using = JsonDateTimeSerializer.class)
	public Date getCreateDate() {
		return createDate;
	}

	@JsonDeserialize(using = JsonDateTimeDeserializer.class)
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Long getHangUserId() {
		return hangUserId;
	}

	public void setHangUserId(Long hangUserId) {
		this.hangUserId = hangUserId;
	}

	public Long getUnitId() {
		return unitId;
	}

	public void setUnitId(Long unitId) {
		this.unitId = unitId;
	}

	public Long getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(Long createUserId) {
		this.createUserId = createUserId;
	}
	@JsonSerialize(using = JsonDateTimeSerializer.class)
	public Date getDefectTime() {
		return defectTime;
	}
	@JsonDeserialize(using = JsonDateTimeDeserializer.class)
	public void setDefectTime(Date defectTime) {
		this.defectTime = defectTime;
	}


	public int getLossElectricity() {
		return lossElectricity;
	}

	public void setLossElectricity(int lossElectricity) {
		this.lossElectricity = lossElectricity;
	}

	public String getEquipManageType() {
		return equipManageType;
	}

	public void setEquipManageType(String equipManageType) {
		this.equipManageType = equipManageType;
	}

	public String getEquipManageTypeName() {
		return equipManageTypeName;
	}

//	public String getEquipManageTypeName() {
//		Map<String, SysDictionaryVO> specialCodeMap  =  DictionaryUtil.getDictionaries("EQUIPMANAGETYPE");
//		Map<String,String> specialCodeEnumMap = new HashMap<String, String>();
//		for(String key : specialCodeMap.keySet()){
//			SysDictionaryVO sysDictionaryVO = specialCodeMap.get(key);
//			specialCodeEnumMap.put(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
//		}
//		return specialCodeEnumMap.get(this.equipManageType);
//	}
	
}