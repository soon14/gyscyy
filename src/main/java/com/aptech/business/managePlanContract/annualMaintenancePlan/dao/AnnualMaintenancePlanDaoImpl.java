package com.aptech.business.managePlanContract.annualMaintenancePlan.dao;

import org.springframework.stereotype.Repository;

import com.aptech.business.managePlanContract.annualMaintenancePlan.domain.AnnualMaintenancePlanEntity;
import com.aptech.framework.orm.AncestorDao;

/**
 * 
 * 年度检修维护计划应用数据类
 *
 * @author 
 * @created 2018-04-18 15:31:10
 * @lastModified 
 * @history
 *
 */
@Repository("annualMaintenancePlanDao")
public class AnnualMaintenancePlanDaoImpl extends AncestorDao<AnnualMaintenancePlanEntity> implements AnnualMaintenancePlanDao{
	
	@Override
	public String getNameSpace() {
		// TODO Auto-generated method stub
		return "com.aptech.business.annualMaintenancePlan";
	}
}
