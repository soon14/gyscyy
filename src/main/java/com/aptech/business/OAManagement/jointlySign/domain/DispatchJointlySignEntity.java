package com.aptech.business.OAManagement.jointlySign.domain;

import org.apache.ibatis.type.Alias;

import com.aptech.business.OAManagement.dispatchManagement.domain.ApprovalResultEnum;
import com.aptech.framework.orm.BaseEntity;


@Alias("DispatchJointlySign")
public class DispatchJointlySignEntity extends BaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7823347938647267198L;
	// 收发文ID
	protected Long dispatchId;
	// 会签结果,1同意，2驳回
	protected String signResult;
	// 会签结果,1同意，2驳回
	protected String signResultCN;
	// 会签意见
	protected String signComment;
	// 会签人id
	protected String signPersionId;
	// 会签人姓名
	protected String signPersionName;
	// 会签时间
	protected String signTime;
 	
 	

 	
	public Long getDispatchId() {
		return dispatchId;
	}

	public void setDispatchId(Long dispatchId) {
		this.dispatchId = dispatchId;
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