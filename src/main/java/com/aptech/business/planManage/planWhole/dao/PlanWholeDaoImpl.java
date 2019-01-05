package com.aptech.business.planManage.planWhole.dao;

import org.springframework.stereotype.Repository;

import com.aptech.business.planManage.planWhole.domain.PlanWholeEntity;
import com.aptech.framework.orm.AncestorDao;

/**
 * 
 * 整体计划应用数据类
 *
 * @author 
 * @created 2017-11-13 15:10:22
 * @lastModified 
 * @history
 *
 */
@Repository("planWholeDao")
public class PlanWholeDaoImpl extends AncestorDao<PlanWholeEntity> implements PlanWholeDao{
	
	@Override
	public String getNameSpace() {
		// TODO Auto-generated method stub
		return "com.aptech.business..planManage.planWhole";
	}
}
