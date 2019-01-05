package com.aptech.business.OAManagement.receiptManagement.domain;

import java.util.Date;
import java.util.Map;

import org.apache.ibatis.type.Alias;

import com.aptech.business.OAManagement.dispatchManagement.domain.DenseEnum;
import com.aptech.business.OAManagement.dispatchManagement.domain.UrgencyEnum;
import com.aptech.common.system.dictionary.util.DictionaryUtil;
import com.aptech.common.system.dictionary.web.SysDictionaryVO;
import com.aptech.framework.orm.BaseEntity;
import com.aptech.framework.util.DateFormatUtil;


@Alias("ReceiptManagement")
public class ReceiptManagementEntity extends BaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4249749843148763944L;
	// 来文标题
	private String title;
	// 收文类型
	private String type;
	// 收文类型
	private String typeCN;
	// 来文单位编码
	private String unitId;
	// 来文单位名称
	private String unitName;
	// 紧急程度
	private String emergencyLevel;
	private String emergencyLevelCN;
	// 密级
	private String securityLevel;
	private String securityLevelCN;
	// 发布人ID
	private String publisherId;
	// 发布人名称
	private String publisherName;
	// 拟稿时间
	private String draftTime;
	// 公文正文
	private String documents;
	// 附件
	private String appendix;
	// 公文正文
	private String documentsCN;
	// 附件
	private String appendixCN;
	// 备注
	private String remarks;
	// 来文文号
	private String receiptNameSymbolId;
	// 来文号名称
	private String receiptNameSymbolName;
	// 来文字号年
	private String receiptNameYear;
	// 来文字号序号
	private String receiptNameNum;
	//来文字号
	private String receiptNumber;
	// 审查处理：1领导审核/2会签/3驳回发起人
	private String review;
	// 审查意见
	private String reviewComment;
	// 审查人
	private String reviewPersion;
	// 审查时间
	private String reviewTime;
	// 接收单位处理意见，1领导审核/2会签/3驳回部门负责人/4驳回发起人
	private String receivingUnit;
	// 接收单位处理人id
	private String receivingUnitPersion;
	// 接收单位处理人名称
	private String receivingUnitPersionName;
	// 接收单位处理备注
	private String receivingUnitComment;
	// 接收单位处理备注
	private String receivingUnitTime;
	//生产部门会签处理:同意/驳回
	private String productionUntijointlySign;
	//生产部门会签意见
	private String productionUntijointlySignComment;
	//生产部门会签人
	private String productionUntijointlySignPersion;
	//生产部门会签人名称
	private String productionUntijointlySignPersionName;
	//生产部门会签时间
	private String productionUntijointlySignTime;
	private String productionUntijointlySelectedPersion;
	private String productionUntijointlySelectedPersionName;
	private String productionUntijointlySignedPersion;
	//部门内部会签处理:同意/驳回
	private String departMentInsidejointlySign;
	//部门内部会签意见
	private String departMentInsidejointlySignComment;
	//部门内部会签人
	private String departMentInsidejointlySignPersion;
	//部门内部会签人名称
	private String departMentInsidejointlySignPersionName;
	//部门内部会签时间
	private String departMentInsidejointlySignTime;
	private String departMentInsidejointlySelectedPersion;
	private String departMentInsidejointlySelectedPersionName;
	private String departMentInsidejointlySignedPersion;
	// 会签处理:同意/驳回
	private String jointlySign;
	// 会签意见
	private String jointlySignComment;
	// 会签人
	private String jointlySignPersion;
	// 会签时间
	private String jointlySignTime;
	// 领导审批: 同意/驳回
	private String leaderApproval;
	// 领导审批意见
	private String leaderComment;
	// 领导审批审批人
	private String leaderPersion;
	// 领导审批时间
	private String leaderTime;
	// 创建时间
	private Date createDate;
	// 修改时间
	private Date updateDate;
	// 审批状态
	private String approvalStatus;
	private String approvalStatusCN;
	// 会签选择人id
	private String jointlySelectedPersion;
	// 已经会签过的人ID
	private String jointlySignedPersion;
	
	private String showTime;
	
	
	private String taskId;
	
	/**
	 * 选择use
	 */
	private String userList;
	
	private String procInstId;
	
	/**
	 * 选择的领导审批的人
	 */
	private String leaderSelectedPersion;
	
	/**
	 *  操作类型 1 公司签报/2外部收文
	 */
	private String operateType;
	private String operateTypeCN;
	
	/**
	 * 部门负责人审查人名称
	 */
	protected String reviewPersionName;
	/**
	 * 会签人名称
	 */
	protected String jointlySignPersionName;
	/**
	 * 领导审核人名称
	 */
	protected String leaderPersionName;
	/**
	 * 会签选择人名称
	 */
	protected String jointlySelectedPersionName;
	/**
	 * 领导审核选择人名称
	 */
	protected String leaderSelectedPersionName;
	/**
	 * 部门负责人审核选择人
	 */
	protected String reviewSelectedPersion;
	/**
	 * 部门负责人审核选择人名称
	 */
	protected String reviewSelectedPersionName;
	/**
	 * 接收人处理选择人
	 */
	protected String recevingSelectedPersion;
	/**
	 * 接收人处理选择人名称
	 */
	protected String recevingSelectedPersionName;
	
	private String currentStepUserList;
	
	/**
	 * 来文编号
	 */
	private String receiptNo;
	
	
	/**
	 *  外部会签处理:同意/驳回
	 */
	private String outJointlySign;

	/**
	 * 外部会签意见
	 */
	private String outJointlySignComment;

	/**
	 * 外部会签人
	 */
	private String outJointlySignPersion;
	/**
	 * 外部会签人名称
	 */
	private String outJointlySignPersionName;
	
	/**
	 * 外部会签时间
	 */
	private String outJointlySignTime;
	

	/**
	 * 外部会签选择人id
	 */
	private String outSelectedPersion;
	
	/**
	 * 外部会签选择人名称
	 */
	private String outSelectedPersionName;

	/**
	 * 外部已经会签过的人ID
	 */
	private String outSignedPersion;
	
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
	public String getEmergencyLevel() {
		return emergencyLevel;
	}
	public void setEmergencyLevel(String emergencyLevel) {
		this.emergencyLevel = emergencyLevel;
	}
	public String getSecurityLevel() {
		return securityLevel;
	}
	public void setSecurityLevel(String securityLevel) {
		this.securityLevel = securityLevel;
	}
	public String getPublisherId() {
		return publisherId;
	}
	public void setPublisherId(String publisherId) {
		this.publisherId = publisherId;
	}
	public String getPublisherName() {
		return publisherName;
	}
	public void setPublisherName(String publisherName) {
		this.publisherName = publisherName;
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
	public String getAppendix() {
		return appendix;
	}
	public void setAppendix(String appendix) {
		this.appendix = appendix;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getReceiptNameSymbolId() {
		return receiptNameSymbolId;
	}
	public void setReceiptNameSymbolId(String receiptNameSymbolId) {
		this.receiptNameSymbolId = receiptNameSymbolId;
	}
	public String getReceiptNameSymbolName() {
		return receiptNameSymbolName;
	}
	public void setReceiptNameSymbolName(String receiptNameSymbolName) {
		this.receiptNameSymbolName = receiptNameSymbolName;
	}
	public String getReceiptNameYear() {
		return receiptNameYear;
	}
	public void setReceiptNameYear(String receiptNameYear) {
		this.receiptNameYear = receiptNameYear;
	}
	public String getReceiptNameNum() {
		return receiptNameNum;
	}
	public void setReceiptNameNum(String receiptNameNum) {
		this.receiptNameNum = receiptNameNum;
	}
	public String getReceiptNumber() {
		return receiptNumber;
	}
	public void setReceiptNumber(String receiptNumber) {
		this.receiptNumber = receiptNumber;
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
	public String getReceivingUnit() {
		return receivingUnit;
	}
	public void setReceivingUnit(String receivingUnit) {
		this.receivingUnit = receivingUnit;
	}
	public String getReceivingUnitPersion() {
		return receivingUnitPersion;
	}
	public void setReceivingUnitPersion(String receivingUnitPersion) {
		this.receivingUnitPersion = receivingUnitPersion;
	}

	public String getReceivingUnitPersionName() {
		return receivingUnitPersionName;
	}
	public void setReceivingUnitPersionName(String receivingUnitPersionName) {
		this.receivingUnitPersionName = receivingUnitPersionName;
	}
	public String getReceivingUnitComment() {
		return receivingUnitComment;
	}
	public void setReceivingUnitComment(String receivingUnitComment) {
		this.receivingUnitComment = receivingUnitComment;
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
	public String getJointlySelectedPersion() {
		return jointlySelectedPersion;
	}
	public void setJointlySelectedPersion(String jointlySelectedPersion) {
		this.jointlySelectedPersion = jointlySelectedPersion;
	}
	public String getJointlySignedPersion() {
		return jointlySignedPersion;
	}
	public void setJointlySignedPersion(String jointlySignedPersion) {
		this.jointlySignedPersion = jointlySignedPersion;
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
	public String getSecurityLevelCN() {
		for (DenseEnum denseEnum : DenseEnum.values()) {
			if (denseEnum.getCode().equals(securityLevel)) {
				securityLevelCN = denseEnum.getName();
			}
		}
		return securityLevelCN;
	}
	public void setSecurityLevelCN(String securityLevelCN) {
		this.securityLevelCN = securityLevelCN;
	}
	public String getApprovalStatusCN() {
		for (ReceiptApprovalStatusEnum processEnum : ReceiptApprovalStatusEnum.values()) {
			if (processEnum.getCode().equals(approvalStatus)) {
				approvalStatusCN = processEnum.getName();
			}
		}
		return approvalStatusCN;
	}
	public void setApprovalStatusCN(String approvalStatusCN) {
		this.approvalStatusCN = approvalStatusCN;
	}
	public String getTypeCN() {
		Map<String, SysDictionaryVO> dispatchTypeMap = DictionaryUtil.getDictionaries("RECEIPT_TYPE");
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

	public String getShowTime() {
		if (createDate != null) {
			DateFormatUtil dfu = DateFormatUtil.getInstance("yyyy-MM-dd HH:mm");
			showTime = dfu.format(this.createDate);
		}
		return showTime;
	}

	public void setShowTime(String showTime) {
		this.showTime = showTime;
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
	public String getDocumentsCN() {
		return documentsCN;
	}
	public void setDocumentsCN(String documentsCN) {
		this.documentsCN = documentsCN;
	}
	public String getAppendixCN() {
		return appendixCN;
	}
	public void setAppendixCN(String appendixCN) {
		this.appendixCN = appendixCN;
	}
	public String getReceivingUnitTime() {
		return receivingUnitTime;
	}
	public void setReceivingUnitTime(String receivingUnitTime) {
		this.receivingUnitTime = receivingUnitTime;
	}
	public String getLeaderSelectedPersion() {
		return leaderSelectedPersion;
	}
	public void setLeaderSelectedPersion(String leaderSelectedPersion) {
		this.leaderSelectedPersion = leaderSelectedPersion;
	}
	public String getOperateType() {
		return operateType;
	}
	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}
	public String getOperateTypeCN() {
		Map<String, SysDictionaryVO> operateTypeMap = DictionaryUtil.getDictionaries("RECEIPT_OPERATE_TYPE");
		for(String key : operateTypeMap.keySet()){
			SysDictionaryVO operateTypeVO = operateTypeMap.get(key);
			if (operateTypeVO.getCode().equals(operateType)) {
				operateTypeCN = operateTypeVO.getName();
				break;
			}
		}
		return operateTypeCN;
	}
	public void setOperateTypeCN(String operateTypeCN) {
		this.operateTypeCN = operateTypeCN;
	}
	public String getReviewPersionName() {
		return reviewPersionName;
	}
	public void setReviewPersionName(String reviewPersionName) {
		this.reviewPersionName = reviewPersionName;
	}
	public String getJointlySignPersionName() {
		return jointlySignPersionName;
	}
	public void setJointlySignPersionName(String jointlySignPersionName) {
		this.jointlySignPersionName = jointlySignPersionName;
	}
	public String getLeaderPersionName() {
		return leaderPersionName;
	}
	public void setLeaderPersionName(String leaderPersionName) {
		this.leaderPersionName = leaderPersionName;
	}
	public String getJointlySelectedPersionName() {
		return jointlySelectedPersionName;
	}
	public void setJointlySelectedPersionName(String jointlySelectedPersionName) {
		this.jointlySelectedPersionName = jointlySelectedPersionName;
	}
	public String getLeaderSelectedPersionName() {
		return leaderSelectedPersionName;
	}
	public void setLeaderSelectedPersionName(String leaderSelectedPersionName) {
		this.leaderSelectedPersionName = leaderSelectedPersionName;
	}
	public String getReviewSelectedPersion() {
		return reviewSelectedPersion;
	}
	public void setReviewSelectedPersion(String reviewSelectedPersion) {
		this.reviewSelectedPersion = reviewSelectedPersion;
	}
	public String getReviewSelectedPersionName() {
		return reviewSelectedPersionName;
	}
	public void setReviewSelectedPersionName(String reviewSelectedPersionName) {
		this.reviewSelectedPersionName = reviewSelectedPersionName;
	}
	public String getRecevingSelectedPersion() {
		return recevingSelectedPersion;
	}
	public void setRecevingSelectedPersion(String recevingSelectedPersion) {
		this.recevingSelectedPersion = recevingSelectedPersion;
	}
	public String getRecevingSelectedPersionName() {
		return recevingSelectedPersionName;
	}
	public void setRecevingSelectedPersionName(String recevingSelectedPersionName) {
		this.recevingSelectedPersionName = recevingSelectedPersionName;
	}
	public String getCurrentStepUserList() {
		return currentStepUserList;
	}
	public void setCurrentStepUserList(String currentStepUserList) {
		this.currentStepUserList = currentStepUserList;
	}
	public String getReceiptNo() {
		return receiptNo;
	}
	public void setReceiptNo(String receiptNo) {
		this.receiptNo = receiptNo;
	}
	public String getOutJointlySign() {
		return outJointlySign;
	}
	public void setOutJointlySign(String outJointlySign) {
		this.outJointlySign = outJointlySign;
	}
	public String getOutJointlySignComment() {
		return outJointlySignComment;
	}
	public void setOutJointlySignComment(String outJointlySignComment) {
		this.outJointlySignComment = outJointlySignComment;
	}
	public String getOutJointlySignPersion() {
		return outJointlySignPersion;
	}
	public void setOutJointlySignPersion(String outJointlySignPersion) {
		this.outJointlySignPersion = outJointlySignPersion;
	}
	public String getOutJointlySignPersionName() {
		return outJointlySignPersionName;
	}
	public void setOutJointlySignPersionName(String outJointlySignPersionName) {
		this.outJointlySignPersionName = outJointlySignPersionName;
	}
	public String getOutJointlySignTime() {
		return outJointlySignTime;
	}
	public void setOutJointlySignTime(String outJointlySignTime) {
		this.outJointlySignTime = outJointlySignTime;
	}
	public String getOutSelectedPersion() {
		return outSelectedPersion;
	}
	public void setOutSelectedPersion(String outSelectedPersion) {
		this.outSelectedPersion = outSelectedPersion;
	}
	public String getOutSelectedPersionName() {
		return outSelectedPersionName;
	}
	public void setOutSelectedPersionName(String outSelectedPersionName) {
		this.outSelectedPersionName = outSelectedPersionName;
	}
	public String getOutSignedPersion() {
		return outSignedPersion;
	}
	public void setOutSignedPersion(String outSignedPersion) {
		this.outSignedPersion = outSignedPersion;
	}
	public String getProductionUntijointlySign() {
		return productionUntijointlySign;
	}
	public void setProductionUntijointlySign(String productionUntijointlySign) {
		this.productionUntijointlySign = productionUntijointlySign;
	}
	public String getProductionUntijointlySignComment() {
		return productionUntijointlySignComment;
	}
	public void setProductionUntijointlySignComment(
			String productionUntijointlySignComment) {
		this.productionUntijointlySignComment = productionUntijointlySignComment;
	}
	public String getProductionUntijointlySignPersion() {
		return productionUntijointlySignPersion;
	}
	public void setProductionUntijointlySignPersion(
			String productionUntijointlySignPersion) {
		this.productionUntijointlySignPersion = productionUntijointlySignPersion;
	}
	public String getProductionUntijointlySignTime() {
		return productionUntijointlySignTime;
	}
	public void setProductionUntijointlySignTime(
			String productionUntijointlySignTime) {
		this.productionUntijointlySignTime = productionUntijointlySignTime;
	}
	public String getProductionUnitjointlySignPersionName() {
		return productionUntijointlySignPersionName;
	}
	public void setProductionUnitjointlySignPersionName(
			String productionUnitjointlySignPersionName) {
		this.productionUntijointlySignPersionName = productionUnitjointlySignPersionName;
	}
	public String getProductionUntijointlySelectedPersion() {
		return productionUntijointlySelectedPersion;
	}
	public void setProductionUntijointlySelectedPersion(
			String productionUntijointlySelectedPersion) {
		this.productionUntijointlySelectedPersion = productionUntijointlySelectedPersion;
	}
	public String getProductionUntijointlySelectedPersionName() {
		return productionUntijointlySelectedPersionName;
	}
	public void setProductionUntijointlySelectedPersionName(
			String productionUntijointlySelectedPersionName) {
		this.productionUntijointlySelectedPersionName = productionUntijointlySelectedPersionName;
	}
	public String getProductionUntijointlySignedPersion() {
		return productionUntijointlySignedPersion;
	}
	public void setProductionUntijointlySignedPersion(
			String productionUntijointlySignedPersion) {
		this.productionUntijointlySignedPersion = productionUntijointlySignedPersion;
	}
	public String getDepartMentInsidejointlySign() {
		return departMentInsidejointlySign;
	}
	public void setDepartMentInsidejointlySign(String departMentInsidejointlySign) {
		this.departMentInsidejointlySign = departMentInsidejointlySign;
	}
	public String getDepartMentInsidejointlySignComment() {
		return departMentInsidejointlySignComment;
	}
	public void setDepartMentInsidejointlySignComment(
			String departMentInsidejointlySignComment) {
		this.departMentInsidejointlySignComment = departMentInsidejointlySignComment;
	}
	public String getDepartMentInsidejointlySignPersion() {
		return departMentInsidejointlySignPersion;
	}
	public void setDepartMentInsidejointlySignPersion(
			String departMentInsidejointlySignPersion) {
		this.departMentInsidejointlySignPersion = departMentInsidejointlySignPersion;
	}
	public String getDepartMentInsidejointlySignPersionName() {
		return departMentInsidejointlySignPersionName;
	}
	public void setDepartMentInsidejointlySignPersionName(
			String departMentInsidejointlySignPersionName) {
		this.departMentInsidejointlySignPersionName = departMentInsidejointlySignPersionName;
	}
	public String getDepartMentInsidejointlySignTime() {
		return departMentInsidejointlySignTime;
	}
	public void setDepartMentInsidejointlySignTime(
			String departMentInsidejointlySignTime) {
		this.departMentInsidejointlySignTime = departMentInsidejointlySignTime;
	}
	public String getDepartMentInsidejointlySelectedPersion() {
		return departMentInsidejointlySelectedPersion;
	}
	public void setDepartMentInsidejointlySelectedPersion(
			String departMentInsidejointlySelectedPersion) {
		this.departMentInsidejointlySelectedPersion = departMentInsidejointlySelectedPersion;
	}
	public String getDepartMentInsidejointlySelectedPersionName() {
		return departMentInsidejointlySelectedPersionName;
	}
	public void setDepartMentInsidejointlySelectedPersionName(
			String departMentInsidejointlySelectedPersionName) {
		this.departMentInsidejointlySelectedPersionName = departMentInsidejointlySelectedPersionName;
	}
	public String getDepartMentInsidejointlySignedPersion() {
		return departMentInsidejointlySignedPersion;
	}
	public void setDepartMentInsidejointlySignedPersion(
			String departMentInsidejointlySignedPersion) {
		this.departMentInsidejointlySignedPersion = departMentInsidejointlySignedPersion;
	}
}