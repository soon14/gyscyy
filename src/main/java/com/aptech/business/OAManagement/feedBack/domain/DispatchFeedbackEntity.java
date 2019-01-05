package com.aptech.business.OAManagement.feedBack.domain;

import org.apache.ibatis.type.Alias;

import com.aptech.framework.orm.BaseEntity;


@Alias("DispatchFeedback")
public class DispatchFeedbackEntity extends BaseEntity {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = -3677228888704049808L;
	// 收发文ID
    protected Long dispatchId;
    // 反馈结果,1同意，2驳回
    protected String feedbackResult;
    // 反馈意见
    protected String feedbackComment;
    // 反馈人id
    protected String feedbackPersionId;
    // 反馈人姓名
    protected String feedbackPersionName;
    // 反馈时间
    protected String feedbackTime;
 	
	/**
	 * 反馈文件
	 */
	private String feedBackFile;
	
	private String feedBackFileName;

 	
    public Long getDispatchId() {
        return dispatchId;
    }

	public void setDispatchId(Long dispatchId) {
        this.dispatchId = dispatchId;
    }

 	
    public String getFeedbackResult() {
        return feedbackResult;
    }

	public void setFeedbackResult(String feedbackResult) {
        this.feedbackResult = feedbackResult;
    }

 	
    public String getFeedbackComment() {
        return feedbackComment;
    }

	public void setFeedbackComment(String feedbackComment) {
        this.feedbackComment = feedbackComment;
    }

 	
    public String getFeedbackPersionId() {
        return feedbackPersionId;
    }

	public void setFeedbackPersionId(String feedbackPersionId) {
        this.feedbackPersionId = feedbackPersionId;
    }

 	
    public String getFeedbackPersionName() {
        return feedbackPersionName;
    }

	public void setFeedbackPersionName(String feedbackPersionName) {
        this.feedbackPersionName = feedbackPersionName;
    }

 	
    public String getFeedbackTime() {
        return feedbackTime;
    }

	public void setFeedbackTime(String feedbackTime) {
        this.feedbackTime = feedbackTime;
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
}