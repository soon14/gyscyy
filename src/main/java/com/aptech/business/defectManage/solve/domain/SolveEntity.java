package com.aptech.business.defectManage.solve.domain;

import java.util.Date;

import org.apache.ibatis.type.Alias;

import com.aptech.framework.json.JsonDateTimeDeserializer;
import com.aptech.framework.json.JsonDateTimeSerializer;
import com.aptech.framework.orm.BaseEntity;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * 
 * ȱ�ݴ���ʵ����
 * 
 * @author
 * @created 2017-06-05 15:58:55
 * @lastModified
 * @history
 * 
 */
@Alias("solveEntity")
public class SolveEntity extends BaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6100999740606961481L;
	/**
	 * ����
	 */
	private Long id;
	/**
	 * ȱ��id
	 */
	private String defectId;
	/**
	 * ��ȱ������id
	 */
	private String userId;
	/**
	 * ��ȱʱ��
	 */
	private Date solveTime;
	/**
	 * ��ȱ���
	 */
	private String solveResult;
	/**
	 * ��ȱ���
	 */
	private String solveResultName;
	/**
	 * ��������
	 */
	private String solveProblem;
	/**
	 * �������˵��
	 */
	private String solveExplain;

	private String taskId;

	private String procInstId;

	private String processStatus;

	private String userList;
	
	private Date planTime;
	
	private String planEndTime;
	
	private String fileId;
	
	private String endSolve;
	private Date solveDate;
	private String approveIdea;
	
	
	
	
	public String getApproveIdea() {
		return approveIdea;
	}
	public void setApproveIdea(String approveIdea) {
		this.approveIdea = approveIdea;
	}
	@JsonSerialize(using = JsonDateTimeSerializer.class)
	public Date getSolveDate() {
		return solveDate;
	}
	@JsonDeserialize(using = JsonDateTimeDeserializer.class)
	public void setSolveDate(Date solveDate) {
		this.solveDate = solveDate;
	}
	public String getEndSolve() {
		return endSolve;
	}
	public void setEndSolve(String endSolve) {
		this.endSolve = endSolve;
	}
	@JsonSerialize(using = JsonDateTimeSerializer.class)
	public Date getPlanTime() {
		return planTime;
	}
	@JsonDeserialize(using = JsonDateTimeDeserializer.class)
	public void setPlanTime(Date planTime) {
		this.planTime = planTime;
	}

	public String getPlanEndTime() {
		return planEndTime;
	}

	public void setPlanEndTime(String planEndTime) {
		this.planEndTime = planEndTime;
	}

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
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

	/**
	 * ����id
	 */
	private String userName;

	public String getSolveResultName() {
		return solveResultName;
	}

	public void setSolveResultName(String solveResultName) {
		this.solveResultName = solveResultName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserName() {
		return userName;
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

	@JsonSerialize(using = JsonDateTimeSerializer.class)
	public Date getSolveTime() {
		return solveTime;
	}

	@JsonDeserialize(using = JsonDateTimeDeserializer.class)
	public void setSolveTime(Date solveTime) {
		this.solveTime = solveTime;
	}

	public String getSolveResult() {
		return solveResult;
	}

	public void setSolveResult(String solveResult) {
		this.solveResult = solveResult;
	}

	public String getSolveProblem() {
		return solveProblem;
	}

	public void setSolveProblem(String solveProblem) {
		this.solveProblem = solveProblem;
	}

	public String getSolveExplain() {
		return solveExplain;
	}

	public void setSolveExplain(String solveExplain) {
		this.solveExplain = solveExplain;
	}
}