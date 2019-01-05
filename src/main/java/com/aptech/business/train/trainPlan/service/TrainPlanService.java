package com.aptech.business.train.trainPlan.service;

import com.aptech.business.train.trainPlan.domain.TrainPlanEntity;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.web.base.ResultObj;

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
public interface TrainPlanService  extends IBaseEntityOperation<TrainPlanEntity> {

	void saveAddPage(TrainPlanEntity t);

	void saveEditPage(TrainPlanEntity trainPlanEntity);
	
}