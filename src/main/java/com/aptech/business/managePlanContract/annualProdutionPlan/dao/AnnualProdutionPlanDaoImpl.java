package com.aptech.business.managePlanContract.annualProdutionPlan.dao;

import org.springframework.stereotype.Repository;

import com.aptech.business.managePlanContract.annualProdutionPlan.domain.AnnualProdutionPlanEntity;
import com.aptech.framework.orm.AncestorDao;

/**
 * 
 * 年度生产物资计划应用数据类
 *
 * @author 
 * @created 2018-04-24 11:39:57
 * @lastModified 
 * @history
 *
 */
@Repository("annualProdutionPlanDao")
public class AnnualProdutionPlanDaoImpl extends AncestorDao<AnnualProdutionPlanEntity> implements AnnualProdutionPlanDao{
	
	@Override
	public String getNameSpace() {
		// TODO Auto-generated method stub
		return "com.aptech.business.annualProdutionPlan";
	}
}
