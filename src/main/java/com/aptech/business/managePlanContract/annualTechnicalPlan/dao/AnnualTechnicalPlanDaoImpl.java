package com.aptech.business.managePlanContract.annualTechnicalPlan.dao;

import org.springframework.stereotype.Repository;

import com.aptech.business.managePlanContract.annualTechnicalPlan.domain.AnnualTechnicalPlanEntity;
import com.aptech.framework.orm.AncestorDao;

/**
 * 
 * 年度技改计划应用数据类
 *
 * @author 
 * @created 2018-04-04 15:47:00
 * @lastModified 
 * @history
 *
 */
@Repository("annualTechnicalPlanDao")
public class AnnualTechnicalPlanDaoImpl extends AncestorDao<AnnualTechnicalPlanEntity> implements AnnualTechnicalPlanDao{
	
	@Override
	public String getNameSpace() {
		// TODO Auto-generated method stub
		return "com.aptech.business.annualTechnicalPlan";
	}
}
