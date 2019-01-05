package com.aptech.business.ticketManage.workticketRepair.domain;

import java.util.Date;

import org.apache.ibatis.type.Alias;

import com.aptech.framework.json.JsonDateTime1Deserializer;
import com.aptech.framework.json.JsonDateTime1Serializer;
import com.aptech.framework.orm.BaseEntity;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * 
 * 紧急抢修单实体类
 * 
 * @author
 * @created 2017-08-14 15:41:38
 * @lastModified
 * @history
 * 
 */
@Alias("WorkticketRepairEntity")
public class WorkticketRepairEntity extends BaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5996494457743195978L;
	/**
	 * 主键
	 */
	private Long id;
	/**
	 * 工作票表的id
	 */
	private Long workticketId;
	/**
	 * 紧急抢修单填写人id
	 */
	private Long repairPersonId;
	/**
	 * 紧急抢修单填写人
	 */
	private String repairPersonName;
	/**
	 * 工作负责人ID（许可人）
	 */
	private Long allowPicPersonId;
	/**
	 * 工作负责人名字（许可人）
	 */
	private String allowPicPersonName;
	/**
	 * 值长id（许可人）
	 */
	private Long dutyMonitorId;
	/**
	 * 值长名字（许可人）
	 */
	private String dutyMonitorName;
	/**
	 * 终结值长id
	 */
	private Long endDutyMonitorId;
	/**
	 * 终结值长名字
	 */
	private String endDutyMonitorName;
	/**
	 * 终结值长日期
	 */
	private Date endDutyMonitorDate;
	/**
	 * 现场设备状况及保留安全措施
	 */
	private String situation;
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
	/**
	 * 安全措施
	 */
	private String safe;
	/**
	 * 其他安全措施
	 */
	private String otherSafe;
	/**
	 * 保留安全措施
	 */
	private String retainSafe;
	/**
	 * 抢修结果
	 */
	private String repairResult;
	
	private String fileid;
	
	public String getFileid() {
		return fileid;
	}

	public void setFileid(String fileid) {
		this.fileid = fileid;
	}

	public String getRepairResult() {
		return repairResult;
	}

	public void setRepairResult(String repairResult) {
		this.repairResult = repairResult;
	}

	public String getSafe() {
		return safe;
	}

	public void setSafe(String safe) {
		this.safe = safe;
	}

	public String getOtherSafe() {
		return otherSafe;
	}

	public void setOtherSafe(String otherSafe) {
		this.otherSafe = otherSafe;
	}

	public String getRetainSafe() {
		return retainSafe;
	}

	public void setRetainSafe(String retainSafe) {
		this.retainSafe = retainSafe;
	}

	public String getSpFlag() {
		return spFlag;
	}

	public void setSpFlag(String spFlag) {
		this.spFlag = spFlag;
	}

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

	@JsonSerialize(using = JsonDateTime1Serializer.class)
	public Date getQksjZhu() {
		return qksjZhu;
	}

	@JsonDeserialize(using = JsonDateTime1Deserializer.class)
	public void setQksjZhu(Date qksjZhu) {
		this.qksjZhu = qksjZhu;
	}

	public String getApproveIdea() {
		return approveIdea;
	}

	public void setApproveIdea(String approveIdea) {
		this.approveIdea = approveIdea;
	}

	public String getSelectUser() {
		return selectUser;
	}

	public void setSelectUser(String selectUser) {
		this.selectUser = selectUser;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getWorkticketId() {
		return workticketId;
	}

	public void setWorkticketId(Long workticketId) {
		this.workticketId = workticketId;
	}

	public Long getRepairPersonId() {
		return repairPersonId;
	}

	public void setRepairPersonId(Long repairPersonId) {
		this.repairPersonId = repairPersonId;
	}

	public String getRepairPersonName() {
		return repairPersonName;
	}

	public void setRepairPersonName(String repairPersonName) {
		this.repairPersonName = repairPersonName;
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

	public Long getEndDutyMonitorId() {
		return endDutyMonitorId;
	}

	public void setEndDutyMonitorId(Long endDutyMonitorId) {
		this.endDutyMonitorId = endDutyMonitorId;
	}

	public String getEndDutyMonitorName() {
		return endDutyMonitorName;
	}

	public void setEndDutyMonitorName(String endDutyMonitorName) {
		this.endDutyMonitorName = endDutyMonitorName;
	}

	@JsonSerialize(using = JsonDateTime1Serializer.class)
	public Date getEndDutyMonitorDate() {
		return endDutyMonitorDate;
	}

	@JsonDeserialize(using = JsonDateTime1Deserializer.class)
	public void setEndDutyMonitorDate(Date endDutyMonitorDate) {
		this.endDutyMonitorDate = endDutyMonitorDate;
	}

	public String getSituation() {
		return situation;
	}

	public void setSituation(String situation) {
		this.situation = situation;
	}
}