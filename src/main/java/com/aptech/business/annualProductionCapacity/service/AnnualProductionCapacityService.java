package com.aptech.business.annualProductionCapacity.service;

import com.aptech.business.annualProductionCapacity.domain.AnnualProductionCapacityEntity;
import com.aptech.framework.orm.IBaseEntityOperation;

/**
 * 
 * 年度生产量计划应用管理服务接口
 *
 * @author 
 * @created 2018-09-17 18:30:24
 * @lastModified 
 * @history
 *
 */
public interface AnnualProductionCapacityService  extends IBaseEntityOperation<AnnualProductionCapacityEntity> {
	public void deleteByType(AnnualProductionCapacityEntity entity);
}