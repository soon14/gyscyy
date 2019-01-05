package com.aptech.business.OAManagement.dispatchManagement.service;

import java.util.List;
import java.util.Map;

import com.aptech.business.OAManagement.dispatchManagement.domain.DispatchManagementEntity;
import com.aptech.business.OAManagement.dispatchManagement.domain.TreeEntity;
import com.aptech.common.system.user.domain.SysUserEntity;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.web.base.ResultObj;


public interface DispatchManagementService extends IBaseEntityOperation<DispatchManagementEntity> {
	
	/**
	 * 取得拟稿人List
	 * @return
	 */
	public List<SysUserEntity> getDrafterList();
	
	
	/**
	 * 发文文号相关信息
	 * @return
	 */
	public List<Map<String,String>> getDispatchNumberInfo();
	
	/**
	 * 取得接收人树
	 * @return
	 */
	public List<TreeEntity> getRecipientTree();
	
	/**
	 * 流程提交
	 * @param id 对象Id
	 * @param selectUser 选择审批用户对象
	 */
	public void submit(String id, List<SysUserEntity> selectUser);
	

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
	 * 更新领导审查信息
	 * @param entity
	 */
	public void updateComposingApprovalInfo(DispatchManagementEntity entity);
	
	
	/**
	 * 流程下一步
	 * @param taskId
	 * @param params
	 */
	public void approve (DispatchManagementEntity entity, Map<String, Object> params);
	
	
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
	
	
	/**
	 * 流程废弃
	 * @param processInstanceId
	 */
	public void dispatchDiscarded(String businessKey, String processInstanceId);
	
	/**
	 * 流程走会签时，选择领导审核的人
	 * @param taskId
	 * @return
	 */
	public List<SysUserEntity> getPerson4SignLeader(String taskId);
	
	/**
	 * 培训和检修类发文，流程结束时需要通知人登录名称的List
	 * @param dispatchType 发文类型
	 * @return
	 */
	public List<SysUserEntity> getPerson4TrainAndRepair(String dispatchType, String taskId);
	/**
	 * @Description:   查看时候的验证
	 * @author         zhangzq 
	 * @Date           2017年6月29日 下午1:17:42 
	 * @throws         Exception
	 */
	ResultObj showValidate(Long id);
}