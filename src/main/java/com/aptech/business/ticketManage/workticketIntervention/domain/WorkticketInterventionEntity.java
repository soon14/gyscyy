package com.aptech.business.ticketManage.workticketIntervention.domain;

import java.util.Date;

import org.apache.ibatis.type.Alias;

import com.aptech.framework.json.JsonDateTime1Deserializer;
import com.aptech.framework.json.JsonDateTime1Serializer;
import com.aptech.framework.orm.BaseEntity;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * 
 * 介入工作票实体类
 * 
 * @author
 * @created 2017-08-02 11:40:17
 * @lastModified
 * @history
 * 
 */
@Alias("WorkticketInterventionEntity")
public class WorkticketInterventionEntity extends BaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7659376857147939491L;
	/**
	 * 工作票表id
	 */
	private Long workticketId;
	/**
	 * 工作票签发人ID（签发）
	 */
	private Long signerId;
	/**
	 * 工作票签发人名字（签发）
	 */
	private String signerName;
	/**
	 * 签发日期（签发）
	 */
	private Date signerDate;
	/**
	 * 收到工作票时间（收票）
	 */
	private Date getticketTime;
	/**
	 * 运行值班人员ID（收票)
	 */
	private Long ondutyId;
	/**
	 * 运行值班人员名字（收票）
	 */
	private String ondutyName;
	/**
	 * 批准工作时间开始(批准人）
	 */
	private Date approveStarttime;
	/**
	 * 批准工作时间结束(批准人）
	 */
	private Date approveEndtime;
	/**
	 * 值班长ID(批准人）
	 */
	private Long dutyMonitorId;
	/**
	 * 值班长姓名(批准人）
	 */
	private String dutyMonitorName;
	/**
	 * 工作负责人ID（许可人）
	 */
	private Long allowPicPersonId;
	/**
	 * 工作负责人名字（许可人）
	 */
	private String allowPicPersonName;
	/**
	 * 原工作负责人ID
	 */
	private Long changeOldPicId;
	/**
	 * 原工作负责人名字
	 */
	private String changeOldPicName;
	/**
	 * 变更后工作负责人ID
	 */
	private Long changeNewPicId;
	/**
	 * 变更后工作负责人名字
	 */
	private String changeNewPicName;
	/**
	 * 工作票签发人ID
	 */
	private Long changeSignerId;
	/**
	 * 工作票签发人名字
	 */
	private String changeSignerName;
	/**
	 * 签发人确认日期
	 */
	private Date changeSignerDate;
	/**
	 * 工作许可人ID
	 */
	private Long changeAllowId;
	/**
	 * 工作许可人名字
	 */
	private String changeAllowName;
	/**
	 * 工作许可人确认日期
	 */
	private Date changeAllowDate;
	/**
	 * 工作人员变动，工作班组人员
	 */
	private String workPersonGroup;
	/**
	 * 工作人员变动，工作负责人ID
	 */
	private Long workPersonPicId;
	/**
	 * 工作人员变动，工作负责人名字
	 */
	private String workPersonPicName;
	/**
	 * 工作票终结时间
	 */
	private Date endTime;
	/**
	 * 工作负责人确认时间(终结）
	 */
	private Date endPicDate;
	/**
	 * 工作许可人确认时间(终结）
	 */
	private Date endAllowDate;
	/**
	 * 备注
	 */
	private String remarkOther;
	/**
	 * 审批按钮的标示
	 */
	private String spFlag;
	/**
	 * 终结的负责人和许可人 为主表服务
	 */
	private Long endPicIdZhu;
	private String endPicNameZhu;
	private Long endAllowIdZhu;
	private String endAllowNameZhu;
	private Date endTimeZhu;
	/**
	 * 许可时间 为主表服务
	 */
	private Date qksjZhu;
	// （审批意见）
	private String approveIdea;

	private String selectUser;

	public Long getEndPicIdZhu() {
		return endPicIdZhu;
	}

	public void setEndPicIdZhu(Long endPicIdZhu) {
		this.endPicIdZhu = endPicIdZhu;
	}

	public String getEndPicNameZhu() {
		return endPicNameZhu;
	}

	public void setEndPicNameZhu(String endPicNameZhu) {
		this.endPicNameZhu = endPicNameZhu;
	}

	public Long getEndAllowIdZhu() {
		return endAllowIdZhu;
	}

	public void setEndAllowIdZhu(Long endAllowIdZhu) {
		this.endAllowIdZhu = endAllowIdZhu;
	}

	public String getEndAllowNameZhu() {
		return endAllowNameZhu;
	}

	public void setEndAllowNameZhu(String endAllowNameZhu) {
		this.endAllowNameZhu = endAllowNameZhu;
	}

	@JsonSerialize(using = JsonDateTime1Serializer.class)
	public Date getEndTimeZhu() {
		return endTimeZhu;
	}

	@JsonDeserialize(using = JsonDateTime1Deserializer.class)
	public void setEndTimeZhu(Date endTimeZhu) {
		this.endTimeZhu = endTimeZhu;
	}

	public String getSelectUser() {
		return selectUser;
	}

	public void setSelectUser(String selectUser) {
		this.selectUser = selectUser;
	}

	public String getApproveIdea() {
		return approveIdea;
	}

	public void setApproveIdea(String approveIdea) {
		this.approveIdea = approveIdea;
	}

	@JsonSerialize(using = JsonDateTime1Serializer.class)
	public Date getQksjZhu() {
		return qksjZhu;
	}

	@JsonDeserialize(using = JsonDateTime1Deserializer.class)
	public void setQksjZhu(Date qksjZhu) {
		this.qksjZhu = qksjZhu;
	}

	public String getSpFlag() {
		return spFlag;
	}

	public void setSpFlag(String spFlag) {
		this.spFlag = spFlag;
	}

	public Long getWorkticketId() {
		return workticketId;
	}

	public void setWorkticketId(Long workticketId) {
		this.workticketId = workticketId;
	}

	public Long getSignerId() {
		return signerId;
	}

	public void setSignerId(Long signerId) {
		this.signerId = signerId;
	}

	public String getSignerName() {
		return signerName;
	}

	public void setSignerName(String signerName) {
		this.signerName = signerName;
	}

	@JsonSerialize(using = JsonDateTime1Serializer.class)
	public Date getSignerDate() {
		return signerDate;
	}

	@JsonDeserialize(using = JsonDateTime1Deserializer.class)
	public void setSignerDate(Date signerDate) {
		this.signerDate = signerDate;
	}

	@JsonSerialize(using = JsonDateTime1Serializer.class)
	public Date getGetticketTime() {
		return getticketTime;
	}

	@JsonDeserialize(using = JsonDateTime1Deserializer.class)
	public void setGetticketTime(Date getticketTime) {
		this.getticketTime = getticketTime;
	}

	public Long getOndutyId() {
		return ondutyId;
	}

	public void setOndutyId(Long ondutyId) {
		this.ondutyId = ondutyId;
	}

	public String getOndutyName() {
		return ondutyName;
	}

	public void setOndutyName(String ondutyName) {
		this.ondutyName = ondutyName;
	}

	@JsonSerialize(using = JsonDateTime1Serializer.class)
	public Date getApproveStarttime() {
		return approveStarttime;
	}

	@JsonDeserialize(using = JsonDateTime1Deserializer.class)
	public void setApproveStarttime(Date approveStarttime) {
		this.approveStarttime = approveStarttime;
	}

	@JsonSerialize(using = JsonDateTime1Serializer.class)
	public Date getApproveEndtime() {
		return approveEndtime;
	}

	@JsonDeserialize(using = JsonDateTime1Deserializer.class)
	public void setApproveEndtime(Date approveEndtime) {
		this.approveEndtime = approveEndtime;
	}

	public Long getDutyMonitorId() {
		return dutyMonitorId;
	}

	public void setDutyMonitorId(Long dutyMonitorId) {
		this.dutyMonitorId = dutyMonitorId;
	}

	public String getDutyMonitorName() {
		return dutyMonitorName;
	}

	public void setDutyMonitorName(String dutyMonitorName) {
		this.dutyMonitorName = dutyMonitorName;
	}

	public Long getAllowPicPersonId() {
		return allowPicPersonId;
	}

	public void setAllowPicPersonId(Long allowPicPersonId) {
		this.allowPicPersonId = allowPicPersonId;
	}

	public String getAllowPicPersonName() {
		return allowPicPersonName;
	}

	public void setAllowPicPersonName(String allowPicPersonName) {
		this.allowPicPersonName = allowPicPersonName;
	}

	public Long getChangeOldPicId() {
		return changeOldPicId;
	}

	public void setChangeOldPicId(Long changeOldPicId) {
		this.changeOldPicId = changeOldPicId;
	}

	public String getChangeOldPicName() {
		return changeOldPicName;
	}

	public void setChangeOldPicName(String changeOldPicName) {
		this.changeOldPicName = changeOldPicName;
	}

	public Long getChangeNewPicId() {
		return changeNewPicId;
	}

	public void setChangeNewPicId(Long changeNewPicId) {
		this.changeNewPicId = changeNewPicId;
	}

	public String getChangeNewPicName() {
		return changeNewPicName;
	}

	public void setChangeNewPicName(String changeNewPicName) {
		this.changeNewPicName = changeNewPicName;
	}

	public Long getChangeSignerId() {
		return changeSignerId;
	}

	public void setChangeSignerId(Long changeSignerId) {
		this.changeSignerId = changeSignerId;
	}

	public String getChangeSignerName() {
		return changeSignerName;
	}

	public void setChangeSignerName(String changeSignerName) {
		this.changeSignerName = changeSignerName;
	}

	@JsonSerialize(using = JsonDateTime1Serializer.class)
	public Date getChangeSignerDate() {
		return changeSignerDate;
	}

	@JsonDeserialize(using = JsonDateTime1Deserializer.class)
	public void setChangeSignerDate(Date changeSignerDate) {
		this.changeSignerDate = changeSignerDate;
	}

	public Long getChangeAllowId() {
		return changeAllowId;
	}

	public void setChangeAllowId(Long changeAllowId) {
		this.changeAllowId = changeAllowId;
	}

	public String getChangeAllowName() {
		return changeAllowName;
	}

	public void setChangeAllowName(String changeAllowName) {
		this.changeAllowName = changeAllowName;
	}

	@JsonSerialize(using = JsonDateTime1Serializer.class)
	public Date getChangeAllowDate() {
		return changeAllowDate;
	}

	@JsonDeserialize(using = JsonDateTime1Deserializer.class)
	public void setChangeAllowDate(Date changeAllowDate) {
		this.changeAllowDate = changeAllowDate;
	}

	public String getWorkPersonGroup() {
		return workPersonGroup;
	}

	public void setWorkPersonGroup(String workPersonGroup) {
		this.workPersonGroup = workPersonGroup;
	}

	public Long getWorkPersonPicId() {
		return workPersonPicId;
	}

	public void setWorkPersonPicId(Long workPersonPicId) {
		this.workPersonPicId = workPersonPicId;
	}

	public String getWorkPersonPicName() {
		return workPersonPicName;
	}

	public void setWorkPersonPicName(String workPersonPicName) {
		this.workPersonPicName = workPersonPicName;
	}

	@JsonSerialize(using = JsonDateTime1Serializer.class)
	public Date getEndTime() {
		return endTime;
	}

	@JsonDeserialize(using = JsonDateTime1Deserializer.class)
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	@JsonSerialize(using = JsonDateTime1Serializer.class)
	public Date getEndPicDate() {
		return endPicDate;
	}

	@JsonDeserialize(using = JsonDateTime1Deserializer.class)
	public void setEndPicDate(Date endPicDate) {
		this.endPicDate = endPicDate;
	}

	@JsonSerialize(using = JsonDateTime1Serializer.class)
	public Date getEndAllowDate() {
		return endAllowDate;
	}

	@JsonDeserialize(using = JsonDateTime1Deserializer.class)
	public void setEndAllowDate(Date endAllowDate) {
		this.endAllowDate = endAllowDate;
	}

	public String getRemarkOther() {
		return remarkOther;
	}

	public void setRemarkOther(String remarkOther) {
		this.remarkOther = remarkOther;
	}
}