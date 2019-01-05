package com.aptech.business.OAManagement.dispatchManagement.dao;
 
import com.aptech.business.OAManagement.dispatchManagement.domain.DispatchManagementEntity;
import com.aptech.framework.orm.IBaseEntityOperation;

public interface DispatchManagementDao extends IBaseEntityOperation<DispatchManagementEntity> {
	
	/**
	 * 更新审查信息
	 * @param entity
	 */
	public void updateReviewInfo(DispatchManagementEntity entity);
	
	/**
	 * 更新会签信息
	 * @param entity
	 */
	public void updateJointlySignInfo(DispatchManagementEntity entity);
	
	/**
	 * 更新领导审查信息
	 * @param entity
	 */
	public void updateLeaderApprovalInfo(DispatchManagementEntity entity);
	
	/**
	 * 更新综合管理处审查信息
	 * @param entity
	 */
	public void updateComposingApprovalInfo(DispatchManagementEntity entity);
	/**
	 * 更新反馈信息
	 * @param entity
	 */
	public void updateFeedBackInfo(DispatchManagementEntity entity);
	/**
	 * 发文发布
	 * @param entity
	 */
	public void releaseDispatch(DispatchManagementEntity entity);
	

}