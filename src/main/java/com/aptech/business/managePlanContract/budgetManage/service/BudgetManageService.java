package com.aptech.business.managePlanContract.budgetManage.service;

import java.util.List;
import java.util.Map;

import com.aptech.business.managePlanContract.budgetManage.domain.BudgetManageEntity;
import com.aptech.framework.orm.IBaseEntityOperation;

/**
 * 
 * 预算管理应用管理服务接口
 *
 * @author 
 * @created 2018-07-25 13:17:36
 * @lastModified 
 * @history
 *
 */
public interface BudgetManageService  extends IBaseEntityOperation<BudgetManageEntity> {
	
	public List<BudgetManageEntity>  getDataList(String searchYear) ;
}