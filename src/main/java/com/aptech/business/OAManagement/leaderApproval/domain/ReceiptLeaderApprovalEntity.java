package com.aptech.business.OAManagement.leaderApproval.domain;

import org.apache.ibatis.type.Alias;

import com.aptech.business.OAManagement.dispatchManagement.domain.ApprovalResultEnum;
import com.aptech.framework.orm.BaseEntity;


@Alias("ReceiptLeaderApproval")
public class ReceiptLeaderApprovalEntity extends BaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8577113574303043495L;
	// 收发文ID
	protected Long receiptId;
	// 领导审批结果,1同意，2驳回
	protected String approvalResult;
	// 领导审批结果,1同意，2驳回
	protected String approvalResultCN;
	// 领导审批意见
	protected String approvalComment;
	// 领导审批人id
	protected String approvalPersionId;
	// 领导审批人姓名
	protected String approvalPersionName;
	// 领导审批时间
	protected String approvalTime;
	
	public Long getReceiptId() {
		return receiptId;
	}

	public void setReceiptId(Long receiptId) {
		this.receiptId = receiptId;
	}

	public String getApprovalResult() {
		return approvalResult;
	}

	public void setApprovalResult(String approvalResult) {
		this.approvalResult = approvalResult;
	}
	
	public String getApprovalResultCN() {
		for (ApprovalResultEnum resultEnum : ApprovalResultEnum.values()) {
			if (resultEnum.getCode().equals(approvalResult)) {
				approvalResultCN = resultEnum.getName();
			}
		}
		return approvalResultCN;
	}

	public void setApprovalResultCN(String approvalResultCN) {
		this.approvalResultCN = approvalResultCN;
	}

	public String getApprovalComment() {
		return approvalComment;
	}

	public void setApprovalComment(String approvalComment) {
		this.approvalComment = approvalComment;
	}

	public String getApprovalPersionId() {
		return approvalPersionId;
	}

	public void setApprovalPersionId(String approvalPersionId) {
		this.approvalPersionId = approvalPersionId;
	}

	public String getApprovalPersionName() {
		return approvalPersionName;
	}

	public void setApprovalPersionName(String approvalPersionName) {
		this.approvalPersionName = approvalPersionName;
	}

	public String getApprovalTime() {
		return approvalTime;
	}

	public void setApprovalTime(String approvalTime) {
		this.approvalTime = approvalTime;
	}
	 
}