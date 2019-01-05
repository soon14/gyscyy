package com.aptech.business.managePlanContract.annualTrainingPlan.dao;

import org.springframework.stereotype.Repository;

import com.aptech.business.managePlanContract.annualTrainingPlan.domain.AnnualTrainingPlanEntity;
import com.aptech.framework.orm.AncestorDao;

/**
 * 
 * 年度培训计划应用数据类
 *
 * @author 
 * @created 2018-04-13 15:24:06
 * @lastModified 
 * @history
 *
 */
@Repository("annualTrainingPlanDao")
public class AnnualTrainingPlanDaoImpl extends AncestorDao<AnnualTrainingPlanEntity> implements AnnualTrainingPlanDao{
	
	@Override
	public String getNameSpace() {
		// TODO Auto-generated method stub
		return "com.aptech.business.annualTrainingPlan";
	}
}
