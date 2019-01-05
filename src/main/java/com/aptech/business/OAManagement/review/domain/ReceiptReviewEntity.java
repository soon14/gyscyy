package com.aptech.business.OAManagement.review.domain;

import org.apache.ibatis.type.Alias;

import com.aptech.business.OAManagement.dispatchManagement.domain.ApprovalResultEnum;
import com.aptech.business.OAManagement.dispatchManagement.domain.ReviewTypeEnum;
import com.aptech.business.OAManagement.receiptManagement.domain.ReceiptReviewTypeEnum;
import com.aptech.framework.orm.BaseEntity;


@Alias("ReceiptReview")
public class ReceiptReviewEntity extends BaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1361987280613419048L;
	// 收发文ID
	private Long receiptId;
	// 审查结果,1同意，2驳回
	private String reviewResult;
	// 审查结果,1同意，2驳回
	private String reviewResultCN;
	// 审查意见
	private String reviewComment;
	// 审查人id
	private String reviewPersionId;
	// 审查人姓名
	private String reviewPersionName;
	// 审查时间
	private String reviewTime;
	
 	/**
 	 * 审核类型 1 部门负责人审核/2会签/3领导审核/4接收人处理
 	 */
 	private String reviewType;
 	private String reviewTypeCN;

 	/**
 	 * 组织id
 	 */
 	private String unitId;
 	
 	/**
 	 * 组织名称
 	 */
 	private String unitName;
	public Long getReceiptId() {
		return receiptId;
	}

	public void setReceiptId(Long receiptId) {
		this.receiptId = receiptId;
	}

 	
	public String getReviewResult() {
		return reviewResult;
	}

	public void setReviewResult(String reviewResult) {
		this.reviewResult = reviewResult;
	}
 	
	public String getReviewResultCN() {
		for (ApprovalResultEnum resultEnum : ApprovalResultEnum.values()) {
			if (resultEnum.getCode().equals(reviewResult)) {
				reviewResultCN = resultEnum.getName();
			}
		}
		return reviewResultCN;
	}

	public void setReviewResultCN(String reviewResultCN) {
		this.reviewResultCN = reviewResultCN;
	}

	public String getReviewComment() {
		return reviewComment;
	}

	public void setReviewComment(String reviewComment) {
		this.reviewComment = reviewComment;
	}

 	
	public String getReviewPersionId() {
		return reviewPersionId;
	}

	public void setReviewPersionId(String reviewPersionId) {
		this.reviewPersionId = reviewPersionId;
	}

 	
	public String getReviewPersionName() {
		return reviewPersionName;
	}

	public void setReviewPersionName(String reviewPersionName) {
		this.reviewPersionName = reviewPersionName;
	}

 	
	public String getReviewTime() {
		return reviewTime;
	}

	public void setReviewTime(String reviewTime) {
		this.reviewTime = reviewTime;
	}

	public String getReviewType() {
		return reviewType;
	}

	public void setReviewType(String reviewType) {
		this.reviewType = reviewType;
	}

	public String getReviewTypeCN() {
		for (ReceiptReviewTypeEnum resultEnum : ReceiptReviewTypeEnum.values()) {
			if (resultEnum.getCode().equals(reviewType)) {
				if (ReceiptReviewTypeEnum.OUT_DEPARTMENT.getCode().equals(reviewType)) {
					reviewTypeCN = resultEnum.getName() + "(" + this.unitName + ")";
				} else if (ReceiptReviewTypeEnum.PRODUCTION_UNIT_JOINLTY_SING.getCode().equals(reviewType)){
					reviewTypeCN = resultEnum.getName() + "(" + this.unitName + ")";
				} else if (ReceiptReviewTypeEnum.DEPARTMETNT_INSIDE_JOINLTY_SING.getCode().equals(reviewType)){
					reviewTypeCN = resultEnum.getName() + "(" + this.unitName + ")";
				} else {
					reviewTypeCN = resultEnum.getName();
				}
				break;
			}
		}
		return reviewTypeCN;
	}

	public void setReviewTypeCN(String reviewTypeCN) {
		this.reviewTypeCN = reviewTypeCN;
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
	 
}