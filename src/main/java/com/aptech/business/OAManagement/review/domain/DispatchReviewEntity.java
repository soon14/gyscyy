package com.aptech.business.OAManagement.review.domain;

import org.apache.ibatis.type.Alias;

import com.aptech.business.OAManagement.dispatchManagement.domain.ApprovalResultEnum;
import com.aptech.business.OAManagement.dispatchManagement.domain.ReviewTypeEnum;
import com.aptech.framework.orm.BaseEntity;


@Alias("DispatchReview")
public class DispatchReviewEntity extends BaseEntity {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = -8193995200820891588L;
	// 收发文ID
    protected Long dispatchId;
    // 审查结果,1同意，2驳回
    protected String reviewResult;
    // 审查结果,1同意，2驳回
    protected String reviewResultCN;
    // 审查意见
    protected String reviewComment;
    // 审查人id
    protected String reviewPersionId;
    // 审查人姓名
    protected String reviewPersionName;
    // 审查时间
    protected String reviewTime;
 	
 	/**
 	 * 审核类型 1 部门负责人审核/2会签/3领导审核
 	 */
 	private String reviewType;
 	private String reviewTypeCN;

 	
    public Long getDispatchId() {
        return dispatchId;
    }

	public void setDispatchId(Long dispatchId) {
        this.dispatchId = dispatchId;
    }

 	
    public String getReviewResult() {
        return reviewResult;
    }

	public void setReviewResult(String reviewResult) {
        this.reviewResult = reviewResult;
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

	public String getReviewType() {
		return reviewType;
	}

	public void setReviewType(String reviewType) {
		this.reviewType = reviewType;
	}

	public String getReviewTypeCN() {
		for (ReviewTypeEnum resultEnum : ReviewTypeEnum.values()) {
			if (resultEnum.getCode().equals(reviewType)) {
				reviewTypeCN = resultEnum.getName();
			}
		}
		return reviewTypeCN;
	}

	public void setReviewTypeCN(String reviewTypeCN) {
		this.reviewTypeCN = reviewTypeCN;
	}
	
	
}