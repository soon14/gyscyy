package com.aptech.business.planManage.planDetail.dao;

import org.springframework.stereotype.Repository;

import com.aptech.business.planManage.planDetail.domain.PlanDetailEntity;
import com.aptech.framework.orm.AncestorDao;

/**
 * 
 * 详细计划应用数据类
 *
 * @author 
 * @created 2017-11-13 15:10:26
 * @lastModified 
 * @history
 *
 */
@Repository("planDetailDao")
public class PlanDetailDaoImpl extends AncestorDao<PlanDetailEntity> implements PlanDetailDao{
	
	@Override
	public String getNameSpace() {
		// TODO Auto-generated method stub
		return "com.aptech.business.planManage.planDetail";
	}
}
