package com.aptech.business.managePlanContract.rollingMaintencePlan.dao;

import org.springframework.stereotype.Repository;

import com.aptech.business.managePlanContract.rollingMaintencePlan.domain.RollingMaintencePlanEntity;
import com.aptech.framework.orm.AncestorDao;

/**
 * 
 * 三年滚动检修计划应用数据类
 *
 * @author 
 * @created 2018-04-23 18:55:02
 * @lastModified 
 * @history
 *
 */
@Repository("rollingMaintencePlanDao")
public class RollingMaintencePlanDaoImpl extends AncestorDao<RollingMaintencePlanEntity> implements RollingMaintencePlanDao{
	
	@Override
	public String getNameSpace() {
		// TODO Auto-generated method stub
		return "com.aptech.business.rollingMaintencePlan";
	}
}
