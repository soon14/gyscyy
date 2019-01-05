package com.aptech.business.run.workPlan.dao;

import org.springframework.stereotype.Repository;

import com.aptech.business.run.workPlan.domain.WorkPlanEntity;
import com.aptech.framework.orm.AncestorDao;

/**
 * 
 * 定期工作计划应用数据类
 *
 * @author 
 * @created 2017-05-27 11:40:09
 * @lastModified 
 * @history
 *
 */
@Repository("workPlanDao")
public class WorkPlanDaoImpl extends AncestorDao<WorkPlanEntity> implements WorkPlanDao{
	
	@Override
	public String getNameSpace() {
		// TODO Auto-generated method stub
		return "com.aptech.business.workPlan";
	}
}
