package com.aptech.business.managePlanContract.monthlyProductionPlan.dao;

import org.springframework.stereotype.Repository;

import com.aptech.business.managePlanContract.monthlyProductionPlan.domain.MonthlyProductionPlanEntity;
import com.aptech.framework.orm.AncestorDao;

/**
 * 
 * 月度生产物资计划应用数据类
 *
 * @author 
 * @created 2018-04-24 11:40:00
 * @lastModified 
 * @history
 *
 */
@Repository("monthlyProductionPlanDao")
public class MonthlyProductionPlanDaoImpl extends AncestorDao<MonthlyProductionPlanEntity> implements MonthlyProductionPlanDao{
	
	@Override
	public String getNameSpace() {
		// TODO Auto-generated method stub
		return "com.aptech.business.monthlyProductionPlan";
	}
}
