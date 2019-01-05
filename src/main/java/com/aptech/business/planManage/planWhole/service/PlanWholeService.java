package com.aptech.business.planManage.planWhole.service;

import com.aptech.business.planManage.planWhole.domain.PlanWholeEntity;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.web.base.ResultObj;

/**
 * 
 * 整体计划应用管理服务接口
 *
 * @author 
 * @created 2017-11-13 15:10:22
 * @lastModified 
 * @history
 *
 */
public interface PlanWholeService  extends IBaseEntityOperation<PlanWholeEntity> {
	
	public ResultObj add(PlanWholeEntity planWholeEntity);
}