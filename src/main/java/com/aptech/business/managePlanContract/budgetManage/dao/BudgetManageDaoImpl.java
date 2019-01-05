package com.aptech.business.managePlanContract.budgetManage.dao;

import org.springframework.stereotype.Repository;

import com.aptech.business.managePlanContract.budgetManage.domain.BudgetManageEntity;
import com.aptech.framework.orm.AncestorDao;

/**
 * 
 * 预算管理应用数据类
 *
 * @author 
 * @created 2018-07-25 13:17:36
 * @lastModified 
 * @history
 *
 */
@Repository("budgetManageDao")
public class BudgetManageDaoImpl extends AncestorDao<BudgetManageEntity> implements BudgetManageDao{
	
	@Override
	public String getNameSpace() {
		// TODO Auto-generated method stub
		return "com.aptech.business.budgetManage";
	}
}
