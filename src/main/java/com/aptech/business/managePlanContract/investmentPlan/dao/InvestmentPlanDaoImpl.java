package com.aptech.business.managePlanContract.investmentPlan.dao;

import org.springframework.stereotype.Repository;

import com.aptech.business.managePlanContract.investmentPlan.domain.InvestmentPlanEntity;
import com.aptech.framework.orm.AncestorDao;

/**
 * 
 * 投资计划应用数据类
 *
 * @author 
 * @created 2018-04-08 13:31:35
 * @lastModified 
 * @history
 *
 */
@Repository("investmentPlanDao")
public class InvestmentPlanDaoImpl extends AncestorDao<InvestmentPlanEntity> implements InvestmentPlanDao{
	
	@Override
	public String getNameSpace() {
		// TODO Auto-generated method stub
		return "com.aptech.business.investmentPlan";
	}
}
