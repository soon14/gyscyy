package com.aptech.business.overhaul.overhaulProject.service;

import com.aptech.business.overhaul.overhaulProject.domain.OverhaulProjectEntity;
import com.aptech.framework.orm.IBaseEntityOperation;

/**
 * 
 * 检修项目应用管理服务接口
 *
 * @author 
 * @created 2017-06-12 18:48:28
 * @lastModified 
 * @history
 *
 */
public interface OverhaulProjectService  extends IBaseEntityOperation<OverhaulProjectEntity> {
	public void deleteEntityByPlanId(long overhaulPlanId);
	
}