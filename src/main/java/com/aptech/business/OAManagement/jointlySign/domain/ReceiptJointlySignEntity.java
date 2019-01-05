package com.aptech.business.OAManagement.jointlySign.domain;

import org.apache.ibatis.type.Alias;

import com.aptech.business.OAManagement.dispatchManagement.domain.ApprovalResultEnum;
import com.aptech.framework.orm.BaseEntity;


@Alias("ReceiptJointlySign")
public class ReceiptJointlySignEntity extends BaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2705833931090964542L;
	// 收发文ID
	private Long receiptId;
	// 会签结果,1同意，2驳回
	private String signResult;
	// 会签结果,1同意，2驳回
	private String signResultCN;
	// 会签意见
	private String signComment;
	// 会签人id
	private String signPersionId;
	// 会签人姓名
	private String signPersionName;
	// 会签时间
	private String signTime;
	//审核类型 1 生产部门审核 2 处室办理人审核 3 处室负责人审核
	private String type;
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getReceiptId() {
		return receiptId;
	}

	public void setReceiptId(Long receiptId) {
		this.receiptId = receiptId;
	}

	public String getSignResult() {
		return signResult;
	}

	public void setSignResult(String signResult) {
		this.signResult = signResult;
	}

	public String getSignResultCN() {
		for (ApprovalResultEnum resultEnum : ApprovalResultEnum.values()) {
			if (resultEnum.getCode().equals(signResult)) {
				signResultCN = resultEnum.getName();
			}
		}
		return signResultCN;
	}

	public void setSignResultCN(String signResultCN) {
		this.signResultCN = signResultCN;
	}

	public String getSignComment() {
		return signComment;
	}

	public void setSignComment(String signComment) {
		this.signComment = signComment;
	}

 	
	public String getSignPersionId() {
		return signPersionId;
	}

	public void setSignPersionId(String signPersionId) {
		this.signPersionId = signPersionId;
	}

 	
	public String getSignPersionName() {
		return signPersionName;
	}

	public void setSignPersionName(String signPersionName) {
		this.signPersionName = signPersionName;
	}

 	
	public String getSignTime() {
		return signTime;
	}

	public void setSignTime(String signTime) {
		this.signTime = signTime;
	}
	 
}