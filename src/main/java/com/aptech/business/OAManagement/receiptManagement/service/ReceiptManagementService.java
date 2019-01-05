package com.aptech.business.OAManagement.receiptManagement.service;

import java.util.List;
import java.util.Map;

import com.aptech.business.OAManagement.dispatchManagement.domain.TreeEntity;
import com.aptech.business.OAManagement.receiptManagement.domain.ReceiptManagementEntity;
import com.aptech.common.system.user.domain.SysUserEntity;
import com.aptech.framework.orm.IBaseEntityOperation;


public interface ReceiptManagementService extends IBaseEntityOperation<ReceiptManagementEntity> {
	
	/**
	 * 收文文号相关信息
	 * @return
	 */
	public List<Map<String,String>> getReceiptNumberInfo();
	
	
	
	/**
	 * 流程提交
	 * @param id 对象Id
	 * @param selectUser 选择审批用户对象
	 */
	public void submit(String id, List<SysUserEntity> selectUser);
	
	/**
	 * 流程下一步
	 * @param entity
	 * @param params
	 */
	public void approve (ReceiptManagementEntity entity, Map<String, Object> params);
	
	/**
	 * 取得发布人树
	 * @return
	 */
	public List<TreeEntity> getPublisherTree();
	
	
	/**
	 * 流程废弃
	 * @param processInstanceId
	 */
	public void dispatchDiscarded(String businessKey, String processInstanceId);
	
	
	/**
	 * 接收人处理，取领导审核人
	 * @param taskId
	 * @return
	 */
	public List<SysUserEntity> getLeaderPerson4ReceivingHandle(String taskId);
}