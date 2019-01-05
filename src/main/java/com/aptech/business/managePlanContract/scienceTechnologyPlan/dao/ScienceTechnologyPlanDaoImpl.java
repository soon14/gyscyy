package com.aptech.business.managePlanContract.scienceTechnologyPlan.dao;

import org.springframework.stereotype.Repository;

import com.aptech.business.managePlanContract.scienceTechnologyPlan.domain.ScienceTechnologyPlanEntity;
import com.aptech.framework.orm.AncestorDao;

/**
 * 
 * 年度科技计划应用数据类
 *
 * @author 
 * @created 2018-04-02 19:54:46
 * @lastModified 
 * @history
 *
 */
@Repository("scienceTechnologyPlanDao")
public class ScienceTechnologyPlanDaoImpl extends AncestorDao<ScienceTechnologyPlanEntity> implements ScienceTechnologyPlanDao{
	
	@Override
	public String getNameSpace() {
		// TODO Auto-generated method stub
		return "com.aptech.business.scienceTechnologyPlan";
	}
}
