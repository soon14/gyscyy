package com.aptech.business.train.trainManagement.service;

import java.util.Map;

import com.aptech.business.train.trainManagement.domain.TrainManagementEntity;
import com.aptech.framework.orm.IBaseEntityOperation;

/**
 * 
 * 培训计划应用管理服务接口
 *
 * @author 
 * @created 2018-03-19 10:04:50
 * @lastModified 
 * @history
 *
 */
public interface TrainManagementService  extends IBaseEntityOperation<TrainManagementEntity> {

	public void saveAddPage(TrainManagementEntity t);

	public void saveEditPage(TrainManagementEntity trainManagementEntity);
	
	public void delete(Long id);
	
	/**
	 * 流程提交
	 * @param id 对象Id
	 * @param selectUser 选择审批用户Id
	 */
	public void submit(String id, String selectUseIds) ;
	
	/**
	 * 流程下一步
	 * @param entity
	 * @param params
	 */
	public void approve (TrainManagementEntity entity, Map<String, Object> params); 
	
}