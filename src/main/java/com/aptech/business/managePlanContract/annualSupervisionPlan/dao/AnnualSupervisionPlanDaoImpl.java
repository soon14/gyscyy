package com.aptech.business.managePlanContract.annualSupervisionPlan.dao;

import org.springframework.stereotype.Repository;

import com.aptech.business.managePlanContract.annualSupervisionPlan.domain.AnnualSupervisionPlanEntity;
import com.aptech.framework.orm.AncestorDao;

/**
 * 
 * 年度技术监督计划应用数据类
 *
 * @author 
 * @created 2018-04-12 15:36:28
 * @lastModified 
 * @history
 *
 */
@Repository("annualSupervisionPlanDao")
public class AnnualSupervisionPlanDaoImpl extends AncestorDao<AnnualSupervisionPlanEntity> implements AnnualSupervisionPlanDao{
	
	@Override
	public String getNameSpace() {
		// TODO Auto-generated method stub
		return "com.aptech.business.annualSupervisionPlan";
	}
}
