package com.aptech.business.overhaul.overhaulPlan.dao;

import org.springframework.stereotype.Repository;

import com.aptech.business.overhaul.overhaulPlan.domain.OverhaulPlanEntity;
import com.aptech.framework.orm.AncestorDao;

/**
 * 
 * 检修计划应用数据类
 *
 * @author 
 * @created 2017-06-09 10:42:58
 * @lastModified 
 * @history
 *
 */
@Repository("overhaulPlanDao")
public class OverhaulPlanDaoImpl extends AncestorDao<OverhaulPlanEntity> implements OverhaulPlanDao{
	
	@Override
	public String getNameSpace() {
		// TODO Auto-generated method stub
		return "com.aptech.business.overhaulPlan";
	}
}
