package com.aptech.business.OAManagement.dispatchManagement.domain;

import java.util.Date;
import java.util.Map;

import org.apache.ibatis.type.Alias;

import com.aptech.common.system.dictionary.util.DictionaryUtil;
import com.aptech.common.system.dictionary.web.SysDictionaryVO;
import com.aptech.framework.orm.BaseEntity;
import com.aptech.framework.util.DateFormatUtil;


@Alias("DispatchManagement")
public class DispatchManagementEntity extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8467613435336050839L;
	// 发文标题
	private String title;
	// 发文类型
	private String type;
	// 发文类型名称
	private String typeCN;
	// 发文部门
	private String departmentID;
	//发文部门名称
	private String departmentName;
	// 紧急程度
	private String emergencyLevel;
	// 紧急程度中文
	private String emergencyLevelCN;
	// 密级
	private String securityLelvel;
	// 密级中文
	private String securityLelvelCN;
	// 拟稿人ID
	private String drafterId;
	// 拟稿人名称
	private String drafterName;
	// 拟稿时间
	private String draftTime;
	// 公文正文
	private String documents;
	// 公文正文名称
	private String documentsName;
	//附件
	private String appendix;
	// 附件名称
	private String appendixName;
	// 备注
	private String remarks;
	// 发文字号
	private String dispatchName;
	// 审查处理：1领导审核/2会签/3驳回
	private String review;
	// 审查意见
	private String reviewComment;
	// 审查人
	private String reviewPersion;
	// 审查人姓名
	private String reviewPersionCN;
	// 审查时间
	private String reviewTime;
	// 会签处理:同意/驳回
	private String jointlySign;
	// 会签意见
	private String jointlySignComment;
	// 会签人
	private String jointlySignPersion;
	// 会签人姓名
	private String jointlySignPersionCN;
	// 会签时间
	private String jointlySignTime;
	// 领导审批: 1同意/2驳回会签/3驳回领导审核/驳回发起人
	private String leaderApproval;
	// 领导审批意见
	private String leaderComment;
	// 领导审批审批人
	private String leaderPersion;
	// 领导审批审批人姓名
	private String leaderPersionCN;
	// 领导审批时间
	private String leaderTime;
	// 创建时间
	private Date createDate;
	// 更新时间
	private Date updateDate;

	/**
	 * 是否需要反馈，1需要，2不需要
	 */
	private String feedBack;
	
	/**
	 * 反馈提醒时间
	 */
	private String feedBackRemindTime;
	
	/**
	 * 发布状态： 全部挂网/指定接收人
	 */
	private String releaseStatus;

	/**
	 * 指定接收人ID
	 */
	private String recipientId;

	/**
	 * 指定接收人姓名
	 */
	private String recipientName;
	
	/**
	 * 审批状态
	 */
	private String approvalStatus;
	
	/**
	 * 审批状态名称
	 */
	private String approvalStatusCN;

	/**
	 * 单位id
	 */
	private String unitId;
	
	/**
	 * 单位名称
	 */
	private String unitName;
	
	/**
	 * 指定接收人类型，1单位，2个人
	 */
	private String recipientType;
	
	/**
	 * 发文字号-文号编码
	 */
	private String dispatchNameSymbolId;
	
	/**
	 * 发文字号-文号名称
	 */
	private String dispatchNameSymbolName;
	
	/**
	 * 发文字号-年号
	 */
	private String dispatchNameYear;
	
	/**
	 *  发文字号-序号
	 */
	private String dispatchNameNum;
	
	private String taskId;
	
	/**
	 * 选择use
	 */
	private String userList;
	
	private String procInstId;
	
	/**
	 * 反馈信息
	 */
	private String feedBackComment;
	
	/**
	 * 反馈文件
	 */
	private String feedBackFile;
	
	/**
	 * 反馈文件名称
	 */
	private String feedBackFileName;

	/**
	 * 反馈人ID
	 */
	private String feedBackPersion;
	
	/**
	 * 反馈人名称
	 */
	private String feedBackPersionName;
	
	/**
	 * 1已发布/0未发布
	 */
	private String isRelease;
	
	/**
	 * 发布人Id
	 */
	private String releasePersionId;
	
	/**
	 * 发布日期
	 */
	private Date releaseTime;
	
	private String releaseTimeStr;
	
	/**
	 * 反馈时间
	 */
	private String feedBackTime;
	
	/**
	 * 选择会签的人
	 */
	private String jiontlySelectedPersion;
	/**
	 *  选择会签的人名称
	 */
	private String jiontlySelectedPersionCn;
	
	/**
	 * 会签选择同意的人
	 */
	private String jointlySignedPersion;
	
	/**
	 * 领导审核选择人
	 */
	private String leaderSelectedPersion; 
	/**
	 *  领导审核选择人名称
	 */
	private String leaderSelectedPersionCN; 
	
	/**
	 * 已读/未读标识 0：未读/1已读
	 */
	private String readFlag;
	
	/**
	 * 部门负责人审查选择人登录名
	 */
	private String reviewSelectedPersion;
	
	/**
	 * 部门负责人审查选择人名称
	 */
	private String reviewSelectedPersionCN;
	
	/**
	 * 当前节点办理人
	 */
	private String currentStepUserList;
	
	/**
	 * 抄送人
	 */
	private String copyUserIds;
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTypeCN() {
		Map<String, SysDictionaryVO> dispatchTypeMap = DictionaryUtil.getDictionaries("DISPATCH_FILE_TYPE");
		for(String key : dispatchTypeMap.keySet()){
			SysDictionaryVO dispatchTypeVO = dispatchTypeMap.get(key);
			if (dispatchTypeVO.getCode().equals(type)){
				typeCN = dispatchTypeVO.getName();
				break;
			}
		}
		return typeCN;
	}

	public void setTypeCN(String typeCN) {
		this.typeCN = typeCN;
	}

	public String getRecipientType() {
		return recipientType;
	}

	public void setRecipientType(String recipientType) {
		this.recipientType = recipientType;
	}

	public String getDepartmentID() {
		return departmentID;
	}

	public void setDepartmentID(String departmentID) {
		this.departmentID = departmentID;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getEmergencyLevel() {
		return emergencyLevel;
	}

	public void setEmergencyLevel(String emergencyLevel) {
		this.emergencyLevel = emergencyLevel;
	}

	public String getEmergencyLevelCN() {
		for (UrgencyEnum urgencyEnum : UrgencyEnum.values()) {
			if (urgencyEnum.getCode().equals(emergencyLevel)) {
				emergencyLevelCN = urgencyEnum.getName();
			}
		}
		return emergencyLevelCN;
	}

	public void setEmergencyLevelCN(String emergencyLevelCN) {
		this.emergencyLevelCN = emergencyLevelCN;
	}

	public String getSecurityLelvel() {
		return securityLelvel;
	}

	public void setSecurityLelvel(String securityLelvel) {
		this.securityLelvel = securityLelvel;
	}

	public String getSecurityLelvelCN() {
		for (DenseEnum denseEnum : DenseEnum.values()) {
			if (denseEnum.getCode().equals(securityLelvel)) {
				securityLelvelCN = denseEnum.getName();
			}
		}
		return securityLelvelCN;
	}

	public void setSecurityLelvelCN(String securityLelvelCN) {
		this.securityLelvelCN = securityLelvelCN;
	}

	public String getDrafterId() {
		return drafterId;
	}

	public void setDrafterId(String drafterId) {
		this.drafterId = drafterId;
	}

	public String getDrafterName() {
		return drafterName;
	}

	public void setDrafterName(String drafterName) {
		this.drafterName = drafterName;
	}

	public String getDraftTime() {
		return draftTime;
	}

	public void setDraftTime(String draftTime) {
		this.draftTime = draftTime;
	}

	public String getDocuments() {
		return documents;
	}

	public void setDocuments(String documents) {
		this.documents = documents;
	}

	public String getDocumentsName() {
		return documentsName;
	}

	public void setDocumentsName(String documentsName) {
		this.documentsName = documentsName;
	}

	public String getAppendix() {
		return appendix;
	}

	public void setAppendix(String appendix) {
		this.appendix = appendix;
	}

	public String getAppendixName() {
		return appendixName;
	}

	public void setAppendixName(String appendixName) {
		this.appendixName = appendixName;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getDispatchName() {
		return dispatchName;
	}

	public void setDispatchName(String dispatchName) {
		this.dispatchName = dispatchName;
	}

	public String getReview() {
		return review;
	}

	public void setReview(String review) {
		this.review = review;
	}

	public String getReviewComment() {
		return reviewComment;
	}

	public void setReviewComment(String reviewComment) {
		this.reviewComment = reviewComment;
	}

	public String getReviewPersion() {
		return reviewPersion;
	}

	public void setReviewPersion(String reviewPersion) {
		this.reviewPersion = reviewPersion;
	}

	public String getReviewTime() {
		return reviewTime;
	}

	public void setReviewTime(String reviewTime) {
		this.reviewTime = reviewTime;
	}

	public String getJointlySign() {
		return jointlySign;
	}

	public void setJointlySign(String jointlySign) {
		this.jointlySign = jointlySign;
	}

	public String getJointlySignComment() {
		return jointlySignComment;
	}

	public void setJointlySignComment(String jointlySignComment) {
		this.jointlySignComment = jointlySignComment;
	}

	public String getJointlySignPersion() {
		return jointlySignPersion;
	}

	public void setJointlySignPersion(String jointlySignPersion) {
		this.jointlySignPersion = jointlySignPersion;
	}

	public String getJointlySignTime() {
		return jointlySignTime;
	}

	public void setJointlySignTime(String jointlySignTime) {
		this.jointlySignTime = jointlySignTime;
	}

	public String getLeaderApproval() {
		return leaderApproval;
	}

	public void setLeaderApproval(String leaderApproval) {
		this.leaderApproval = leaderApproval;
	}

	public String getLeaderComment() {
		return leaderComment;
	}

	public void setLeaderComment(String leaderComment) {
		this.leaderComment = leaderComment;
	}

	public String getLeaderPersion() {
		return leaderPersion;
	}

	public void setLeaderPersion(String leaderPersion) {
		this.leaderPersion = leaderPersion;
	}

	public String getLeaderTime() {
		return leaderTime;
	}

	public void setLeaderTime(String leaderTime) {
		this.leaderTime = leaderTime;
	}
	
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getApprovalStatus() {
		return approvalStatus;
	}

	public void setApprovalStatus(String approvalStatus) {
		this.approvalStatus = approvalStatus;
	}

	public String getApprovalStatusCN() {
		for (DispatchApprovalStatusEnum processEnum : DispatchApprovalStatusEnum.values()) {
			if (processEnum.getCode().equals(approvalStatus)) {
				approvalStatusCN = processEnum.getName();
			}
		}
		return approvalStatusCN;
	}

	public void setApprovalStatusCN(String approvalStatusCN) {
		this.approvalStatusCN = approvalStatusCN;
	}

	public String getFeedBack() {
		return feedBack;
	}

	public void setFeedBack(String feedBack) {
		this.feedBack = feedBack;
	}

	public String getFeedBackRemindTime() {
		return feedBackRemindTime;
	}

	public void setFeedBackRemindTime(String feedBackRemindTime) {
		this.feedBackRemindTime = feedBackRemindTime;
	}

	public String getReleaseStatus() {
		return releaseStatus;
	}

	public void setReleaseStatus(String releaseStatus) {
		this.releaseStatus = releaseStatus;
	}

	public String getRecipientId() {
		return recipientId;
	}

	public void setRecipientId(String recipientId) {
		this.recipientId = recipientId;
	}

	public String getRecipientName() {
		return recipientName;
	}

	public void setRecipientName(String recipientName) {
		this.recipientName = recipientName;
	}

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getDispatchNameSymbolId() {
		return dispatchNameSymbolId;
	}

	public void setDispatchNameSymbolId(String dispatchNameSymbolId) {
		this.dispatchNameSymbolId = dispatchNameSymbolId;
	}

	public String getDispatchNameSymbolName() {
		return dispatchNameSymbolName;
	}

	public void setDispatchNameSymbolName(String dispatchNameSymbolName) {
		this.dispatchNameSymbolName = dispatchNameSymbolName;
	}

	public String getDispatchNameYear() {
		return dispatchNameYear;
	}

	public void setDispatchNameYear(String dispatchNameYear) {
		this.dispatchNameYear = dispatchNameYear;
	}

	public String getDispatchNameNum() {
		return dispatchNameNum;
	}

	public void setDispatchNameNum(String dispatchNameNum) {
		this.dispatchNameNum = dispatchNameNum;
	}

	public String getReviewPersionCN() {
		return reviewPersionCN;
	}

	public void setReviewPersionCN(String reviewPersionCN) {
		this.reviewPersionCN = reviewPersionCN;
	}

	public String getJointlySignPersionCN() {
		return jointlySignPersionCN;
	}

	public void setJointlySignPersionCN(String jointlySignPersionCN) {
		this.jointlySignPersionCN = jointlySignPersionCN;
	}

	public String getLeaderPersionCN() {
		return leaderPersionCN;
	}

	public void setLeaderPersionCN(String leaderPersionCN) {
		this.leaderPersionCN = leaderPersionCN;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getUserList() {
		return userList;
	}

	public void setUserList(String userList) {
		this.userList = userList;
	}

	public String getProcInstId() {
		return procInstId;
	}

	public void setProcInstId(String procInstId) {
		this.procInstId = procInstId;
	}

	public String getFeedBackComment() {
		return feedBackComment;
	}

	public void setFeedBackComment(String feedBackComment) {
		this.feedBackComment = feedBackComment;
	}

	public String getFeedBackFile() {
		return feedBackFile;
	}

	public void setFeedBackFile(String feedBackFile) {
		this.feedBackFile = feedBackFile;
	}

	public String getFeedBackFileName() {
		return feedBackFileName;
	}

	public void setFeedBackFileName(String feedBackFileName) {
		this.feedBackFileName = feedBackFileName;
	}

	public String getFeedBackPersion() {
		return feedBackPersion;
	}

	public void setFeedBackPersion(String feedBackPersion) {
		this.feedBackPersion = feedBackPersion;
	}

	public String getFeedBackPersionName() {
		return feedBackPersionName;
	}

	public void setFeedBackPersionName(String feedBackPersionName) {
		this.feedBackPersionName = feedBackPersionName;
	}

	public String getFeedBackTime() {
		return feedBackTime;
	}

	public void setFeedBackTime(String feedBackTime) {
		this.feedBackTime = feedBackTime;
	}

	public String getIsRelease() {
		return isRelease;
	}

	public void setIsRelease(String isRelease) {
		this.isRelease = isRelease;
	}

	public String getReleasePersionId() {
		return releasePersionId;
	}

	public void setReleasePersionId(String releasePersionId) {
		this.releasePersionId = releasePersionId;
	}

	public Date getReleaseTime() {
		return releaseTime;
	}

	public void setReleaseTime(Date releaseTime) {
		this.releaseTime = releaseTime;
	}

	public String getReleaseTimeStr() {
		DateFormatUtil dfuYMd = DateFormatUtil.getInstance("yyyy-MM-dd HH:mm");
		if (releaseTime != null) {
			releaseTimeStr = dfuYMd.format(releaseTime);
		}
		return releaseTimeStr;
	}

	public void setReleaseTimeStr(String releaseTimeStr) {
		this.releaseTimeStr = releaseTimeStr;
	}

	public String getJiontlySelectedPersion() {
		return jiontlySelectedPersion;
	}

	public void setJiontlySelectedPersion(String jiontlySelectedPersion) {
		this.jiontlySelectedPersion = jiontlySelectedPersion;
	}

	public String getJointlySignedPersion() {
		return jointlySignedPersion;
	}

	public void setJointlySignedPersion(String jointlySignedPersion) {
		this.jointlySignedPersion = jointlySignedPersion;
	}

	public String getLeaderSelectedPersion() {
		return leaderSelectedPersion;
	}

	public void setLeaderSelectedPersion(String leaderSelectedPersion) {
		this.leaderSelectedPersion = leaderSelectedPersion;
	}

	public String getReadFlag() {
		return readFlag;
	}

	public void setReadFlag(String readFlag) {
		this.readFlag = readFlag;
	}

	public String getCurrentStepUserList() {
		return currentStepUserList;
	}

	public void setCurrentStepUserList(String currentStepUserList) {
		this.currentStepUserList = currentStepUserList;
	}

	public String getJiontlySelectedPersionCn() {
		return jiontlySelectedPersionCn;
	}

	public void setJiontlySelectedPersionCn(String jiontlySelectedPersionCn) {
		this.jiontlySelectedPersionCn = jiontlySelectedPersionCn;
	}

	public String getLeaderSelectedPersionCN() {
		return leaderSelectedPersionCN;
	}

	public void setLeaderSelectedPersionCN(String leaderSelectedPersionCN) {
		this.leaderSelectedPersionCN = leaderSelectedPersionCN;
	}

	public String getReviewSelectedPersion() {
		return reviewSelectedPersion;
	}

	public void setReviewSelectedPersion(String reviewSelectedPersion) {
		this.reviewSelectedPersion = reviewSelectedPersion;
	}

	public String getReviewSelectedPersionCN() {
		return reviewSelectedPersionCN;
	}

	public void setReviewSelectedPersionCN(String reviewSelectedPersionCN) {
		this.reviewSelectedPersionCN = reviewSelectedPersionCN;
	}

	public String getCopyUserIds() {
		return copyUserIds;
	}

	public void setCopyUserIds(String copyUserIds) {
		this.copyUserIds = copyUserIds;
	}
}