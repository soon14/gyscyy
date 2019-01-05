package com.aptech.business.planManage.planDetail.service;

import com.aptech.business.planManage.planDetail.domain.PlanDetailEntity;
import com.aptech.business.planManage.planWhole.domain.PlanWholeEntity;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.web.base.ResultObj;

/**
 * 
 * 详细计划应用管理服务接口
 *
 * @author 
 * @created 2017-11-13 15:10:26
 * @lastModified 
 * @history
 *
 */
public interface PlanDetailService  extends IBaseEntityOperation<PlanDetailEntity> {
	
	public ResultObj add(PlanDetailEntity planDetailEntity);
	
	public   PlanWholeEntity updatePlanDetailEntity(Long planWholeId);
}