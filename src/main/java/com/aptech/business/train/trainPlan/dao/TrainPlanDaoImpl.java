package com.aptech.business.train.trainPlan.dao;

import org.springframework.stereotype.Repository;

import com.aptech.business.train.trainPlan.domain.TrainPlanEntity;
import com.aptech.framework.orm.AncestorDao;

/**
 * 
 * 培训计划应用数据类
 *
 * @author 
 * @created 2018-03-19 10:04:50
 * @lastModified 
 * @history
 *
 */
@Repository("trainPlanDao")
public class TrainPlanDaoImpl extends AncestorDao<TrainPlanEntity> implements TrainPlanDao{
	
	@Override
	public String getNameSpace() {
		// TODO Auto-generated method stub
		return "com.aptech.business.trainPlan";
	}
}
