package com.aptech.business.planManage.plan.dao;

import org.springframework.stereotype.Repository;

import com.aptech.business.planManage.plan.domain.PlanEntity;
import com.aptech.framework.orm.AncestorDao;

/**
 * 
 * 计划管理应用数据类
 *
 * @author 
 * @created 2017-11-13 15:10:16
 * @lastModified 
 * @history
 *
 */
@Repository("planDao")
public class PlanDaoImpl extends AncestorDao<PlanEntity> implements PlanDao{
	
	@Override
	public String getNameSpace() {
		// TODO Auto-generated method stub
		return "com.aptech.business.planManage.plan";
	}
}
