package com.aptech.business.defectManage.appraisal.domain;

import org.apache.ibatis.type.Alias;

import com.aptech.framework.orm.BaseEntity;

/**
 * 
 * ȱ�ݼ�ʵ����
 * 
 * @author
 * @created 2017-06-05 15:58:43
 * @lastModified
 * @history
 * 
 */
@Alias("AppraisalEntity")
public class AppraisalEntity extends BaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7321120815052797268L;
	/**
	 * ����
	 */
	private Long id;
	/**
	 * �����
	 */
	private String appraisalOpinions;
	/**
	 * ����
	 */
	private String appraisalResult;
	/**
	 * ȱ��id
	 */
	private String defectId;
	/**
	 * ����id
	 */
	private String userId;
	/**
	 * ����id
	 */
	private String userName;
	/**
	 * �������
	 */
	private String approvalOpinions;
	/**
	 * ��ʱ��
	 */
	private String appraisalTime;
	/**
	 * ����id
	 */
	private String unitId;
	/**
	 * ����id
	 */
	private String unitName;

	private String taskId;

	private String procInstId;

	private String processStatus;
	
	private String userList;
	
	private String endTime;
	
	private String grade;
	
	private String repeat;
	
	private String reportTime;
	
	
	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getRepeat() {
		return repeat;
	}

	public void setRepeat(String repeat) {
		this.repeat = repeat;
	}

	public String getReportTime() {
		return reportTime;
	}

	public void setReportTime(String reportTime) {
		this.reportTime = reportTime;
	}

	public String getUserList() {
		return userList;
	}

	public void setUserList(String userList) {
		this.userList = userList;
	}
	public String getProcessStatus() {
		return processStatus;
	}

	public void setProcessStatus(String processStatus) {
		this.processStatus = processStatus;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAppraisalOpinions() {
		return appraisalOpinions;
	}

	public void setAppraisalOpinions(String appraisalOpinions) {
		this.appraisalOpinions = appraisalOpinions;
	}

	public String getAppraisalResult() {
		return appraisalResult;
	}

	public void setAppraisalResult(String appraisalResult) {
		this.appraisalResult = appraisalResult;
	}

	public String getDefectId() {
		return defectId;
	}

	public void setDefectId(String defectId) {
		this.defectId = defectId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getApprovalOpinions() {
		return approvalOpinions;
	}

	public void setApprovalOpinions(String approvalOpinions) {
		this.approvalOpinions = approvalOpinions;
	}

	public String getAppraisalTime() {
		return appraisalTime;
	}

	public void setAppraisalTime(String appraisalTime) {
		this.appraisalTime = appraisalTime;
	}

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}
}