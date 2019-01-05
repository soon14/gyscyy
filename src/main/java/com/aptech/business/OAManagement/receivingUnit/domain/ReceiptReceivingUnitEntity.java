package com.aptech.business.OAManagement.receivingUnit.domain;

import org.apache.ibatis.type.Alias;

import com.aptech.business.OAManagement.dispatchManagement.domain.ApprovalResultEnum;
import com.aptech.framework.orm.BaseEntity;


@Alias("ReceiptReceivingUnit")
public class ReceiptReceivingUnitEntity extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1005324109836469745L;
	// 收发文ID
	private Long receiptId;
	// 审查结果,1同意，2驳回
	private String handleResult;
	// 审查结果,1同意，2驳回
	private String handleResultCN;
	// 审查意见
	private String handleComment;
	// 审查人id
	private String handlePersionId;
	// 审查人姓名
	private String handlePersionName;
	// 审查时间
	private String handleTime;
	public Long getReceiptId() {
		return receiptId;
	}
	public void setReceiptId(Long receiptId) {
		this.receiptId = receiptId;
	}
	public String getHandleResult() {
		return handleResult;
	}
	public void setHandleResult(String handleResult) {
		this.handleResult = handleResult;
	}
	public String getHandleResultCN() {
		for (ApprovalResultEnum resultEnum : ApprovalResultEnum.values()) {
			if (resultEnum.getCode().equals(handleResult)) {
				handleResultCN = resultEnum.getName();
			}
		}
		return handleResultCN;
	}
	public void setHandleResultCN(String handleResultCN) {
		this.handleResultCN = handleResultCN;
	}
	public String getHandleComment() {
		return handleComment;
	}
	public void setHandleComment(String handleComment) {
		this.handleComment = handleComment;
	}
	public String getHandlePersionId() {
		return handlePersionId;
	}
	public void setHandlePersionId(String handlePersionId) {
		this.handlePersionId = handlePersionId;
	}
	public String getHandlePersionName() {
		return handlePersionName;
	}
	public void setHandlePersionName(String handlePersionName) {
		this.handlePersionName = handlePersionName;
	}
	public String getHandleTime() {
		return handleTime;
	}
	public void setHandleTime(String handleTime) {
		this.handleTime = handleTime;
	}

	
	 
}